package com.payermax.apple.decrypt;

import com.alibaba.fastjson.JSON;
import com.payermax.apple.dto.request.PaymentData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
@Data
public class ApplePayDecrypt {
    private String privateKeyPem;
    private String certPem;

    public ApplePayDecrypt(String privateKeyPem, String certPem) {
        this.privateKeyPem = privateKeyPem;
        this.certPem = certPem;
    }

    public static KeyFactory keyFactory;
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        try {
            keyFactory = KeyFactory.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            log.error("get EC KeyFactory Instance error", e);
        }
    }

    public DecryptedPaymentData decrypt(PaymentData paymentData) throws Exception {
        byte[] sharedSecret = sharedSecret(paymentData.getHeader().getEphemeralPublicKey());
        byte[] merchantId = merchantId();
        byte[] symmetricKey = symmetricKey(sharedSecret, merchantId);
        String decrypted = decryptCipherText(symmetricKey, paymentData.getData());
        DecryptedPaymentData decryptedPaymentData = JSON.parseObject(decrypted, DecryptedPaymentData.class);
        return decryptedPaymentData;
    }

    /**
     * Generating the shared secret with the merchant private key and the ephemeral public key(part of the payment token data)
     * using Elliptic Curve Diffie-Hellman (id-ecDH 1.3.132.1.12).
     */
    private byte[] sharedSecret(String ephemeralPublicKeyStr) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        byte[] ephemeralPublicKeyBytes = Base64.decodeBase64(ephemeralPublicKeyStr);

        // merchant private key
        String merchantPrivateKeyStr = this.privateKeyPem;
        merchantPrivateKeyStr = merchantPrivateKeyStr.replace("-----BEGIN PRIVATE KEY-----", "");
        merchantPrivateKeyStr = merchantPrivateKeyStr.replace("-----END PRIVATE KEY-----", "");
        merchantPrivateKeyStr = merchantPrivateKeyStr.replaceAll("\\s+", "");
        byte[] merchantPrivateKeyBytes = Base64.decodeBase64(merchantPrivateKeyStr);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(merchantPrivateKeyBytes);
        PrivateKey merchantPrivateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // Ephemeral public key
        PublicKey ephemeralPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(ephemeralPublicKeyBytes));

        // Key agreement
        KeyAgreement agreement = KeyAgreement.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
        agreement.init(merchantPrivateKey);
        agreement.doPhase(ephemeralPublicKey, true);
        byte[] sharedSecret = agreement.generateSecret();
        return sharedSecret;
    }

    private static final String MERCHANT_ID_FIELD_OID = "1.2.840.113635.100.6.32";
    private byte[] merchantId() throws IOException, CertificateException {
        String certificate = this.certPem;
        X509Certificate cert = convertStringToX509Cert(certificate);
        byte[] merchantIdentifierTlv = cert.getExtensionValue(MERCHANT_ID_FIELD_OID);
        byte[] merchantIdentifier = new byte[64];
        System.arraycopy(merchantIdentifierTlv, 4, merchantIdentifier, 0, 64);
        return Hex.decode(merchantIdentifier);
    }
    private X509Certificate convertStringToX509Cert(String certificate) throws IOException, CertificateException {
        try (InputStream targetStream = new ByteArrayInputStream(certificate.getBytes())){
            return (X509Certificate) CertificateFactory
                    .getInstance("X509")
                    .generateCertificate(targetStream);
        }
    }

    private static final byte[] COUNTER = { 0x00, 0x00, 0x00, 0x01 };
    private static final byte[] ALG_IDENTIFIER_BYTES = "id-aes256-GCM".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] APPLE_OEM = "Apple".getBytes(StandardCharsets.US_ASCII);
    private byte[] symmetricKey(byte[] sharedSecret, byte[] merchantId) throws IOException, NoSuchProviderException, NoSuchAlgorithmException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(COUNTER);
        baos.write(sharedSecret);
        baos.write(ALG_IDENTIFIER_BYTES.length);
        baos.write(ALG_IDENTIFIER_BYTES);
        baos.write(APPLE_OEM);
        baos.write(merchantId);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA256", BouncyCastleProvider.PROVIDER_NAME);
        return messageDigest.digest(baos.toByteArray());
    }

    private String decryptCipherText(byte[] symmetricKey, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] dataBytes = Base64.decodeBase64(data);

        // Decrypt the payment data
        String symmetricKeyInfo = "AES/GCM/NoPadding";
        Cipher cipher = Cipher.getInstance(symmetricKeyInfo, BouncyCastleProvider.PROVIDER_NAME);

        SecretKeySpec key = new SecretKeySpec(symmetricKey, cipher.getAlgorithm());
        IvParameterSpec ivspec = new IvParameterSpec(new byte[16]);
        cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
        byte[] decryptedPaymentData = cipher.doFinal(dataBytes);

        // JSON payload
        return new String(decryptedPaymentData, StandardCharsets.UTF_8);
    }

}

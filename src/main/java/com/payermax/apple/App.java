package com.payermax.apple;

import com.alibaba.fastjson.JSON;
import com.payermax.apple.decrypt.ApplePayDecrypt;
import com.payermax.apple.decrypt.DecryptedPaymentData;
import com.payermax.apple.dto.request.PaymentData;
import com.payermax.apple.verify.ApplePaySignatureVerifier;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        //apple 返回的加密数据
        PaymentData paymentData = JSON.parseObject("{\n" +
                "            \"data\": \"Z6hMLc2gvIbLvP6OgcM6+00WUV2Y9LYKSqfFVX9+cYzPEPrWw9sowhNDlDqUCigvUrRdBw2JFcg0RwOCETuin79CrapKceIj5M3op6Fzl52lgYaynm1skiK3XK6W0ET2c0bQQY62XEXRrvut3f1vhyEpkCtJ9Jv4NCuc8NX7hLYCTg2eSCYpbnlDiSue6w/EjaWtmHqZ727HyYX0SaBf5WMxhn51JNweZMhCaSOZVZ+a3U8miVdDnCEjZzyaa8/gcqTM+rT9kmuKCgkwvIvJ3v5yysX3ihq3VR5XEa/4qGA+DnG90E+NrwI+VJR645g0hNOsKMMqwD2K26/PFC0qeeiV6TWKIkuo4IdDtztSEWl5dSr8FfbnlGvCDM/9f+WNFXQ8JD771p8KSPrk9Q==\",\n" +
                "            \"signature\": \"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\",\n" +
                "            \"header\": {\n" +
                "                \"publicKeyHash\": \"Djn0kMdz3USSykBMe8VbMJWD+pvNm7SsaSs22/T+a7Q=\",\n" +
                "                \"ephemeralPublicKey\": \"MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/cWdX2goveiTEzM1x7Pskjk0xDlEkdr/MRvaFnqq7hvdyLNqOqQkfe4I0Z1AVPtgZnHmJXufhuZ+B8DzV4jyVA==\",\n" +
                "                \"transactionId\": \"15fe7685fe7ec6e7e22e5b6358c2d18308e8d57dea24720f799a1b0150dee978\"\n" +
                "            },\n" +
                "            \"version\": \"EC_v1\"\n" +
                "        }", PaymentData.class);

        //主要ApplePayDecrypt先要创建，里边static代码块初始化BC 否则验签报错
        ApplePayDecrypt applePayDecrypt = buildApplePayDecrypt();

        //验证签名
        ApplePaySignatureVerifier.validate(paymentData.getData(), paymentData.getHeader(), paymentData.getSignature(), 1000L * 60 * 60 * 24 * 3000);

        //解密数据
        DecryptedPaymentData decryptedPaymentData = applePayDecrypt.decrypt(paymentData);
        System.out.println(JSON.toJSONString(decryptedPaymentData));

    }

    private static ApplePayDecrypt buildApplePayDecrypt() {
        //解密私钥
        String privateKeyPem = "-----BEGIN PRIVATE KEY-----\n" +
                "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgEaLjsbSS2zuPUwZc\n" +
                "JLTAjb2bJqxsDxXcLI9NwDOUkGOhRANCAASOewwT/tJTwS7nZam7gttyJECZpmcO\n" +
                "LHjVXXqIq7tSas/FXzUA33V4/HtOF5udUfh1e17O1OwQeISFXtkbZQQt\n" +
                "-----END PRIVATE KEY-----";
        //PPC 给apple的加密证书
        String certPem = "-----BEGIN CERTIFICATE-----\n" +
                "MIIElzCCBD6gAwIBAgIIFxU7Giev/9UwCgYIKoZIzj0EAwIwgYAxNDAyBgNVBAMM\n" +
                "K0FwcGxlIFdvcmxkd2lkZSBEZXZlbG9wZXIgUmVsYXRpb25zIENBIC0gRzIxJjAk\n" +
                "BgNVBAsMHUFwcGxlIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MRMwEQYDVQQKDApB\n" +
                "cHBsZSBJbmMuMQswCQYDVQQGEwJVUzAeFw0yMjExMDQxMDQxMTNaFw0yNDEyMDMx\n" +
                "MDQxMTJaMIHJMTMwMQYKCZImiZPyLGQBAQwjbWVyY2hhbnQuY29tLnVzaGFyZWl0\n" +
                "LmFwcGxlcGF5LnRlc3QxSTBHBgNVBAMMQEFwcGxlIFBheSBQYXltZW50IFByb2Nl\n" +
                "c3Npbmc6bWVyY2hhbnQuY29tLnVzaGFyZWl0LmFwcGxlcGF5LnRlc3QxEzARBgNV\n" +
                "BAsMCjVGNVU1VEZCMjYxJTAjBgNVBAoMHFNIQVJFaXQgVGVjaG5vbG9naWVzIENv\n" +
                "LiBMdGQxCzAJBgNVBAYTAlVTMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEjnsM\n" +
                "E/7SU8Eu52Wpu4LbciRAmaZnDix41V16iKu7UmrPxV81AN91ePx7ThebnVH4dXte\n" +
                "ztTsEHiEhV7ZG2UELaOCAlUwggJRMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAU\n" +
                "hLaEzDqGYnIWWZToGqO9SN863wswRwYIKwYBBQUHAQEEOzA5MDcGCCsGAQUFBzAB\n" +
                "hitodHRwOi8vb2NzcC5hcHBsZS5jb20vb2NzcDA0LWFwcGxld3dkcmNhMjAxMIIB\n" +
                "HQYDVR0gBIIBFDCCARAwggEMBgkqhkiG92NkBQEwgf4wgcMGCCsGAQUFBwICMIG2\n" +
                "DIGzUmVsaWFuY2Ugb24gdGhpcyBjZXJ0aWZpY2F0ZSBieSBhbnkgcGFydHkgYXNz\n" +
                "dW1lcyBhY2NlcHRhbmNlIG9mIHRoZSB0aGVuIGFwcGxpY2FibGUgc3RhbmRhcmQg\n" +
                "dGVybXMgYW5kIGNvbmRpdGlvbnMgb2YgdXNlLCBjZXJ0aWZpY2F0ZSBwb2xpY3kg\n" +
                "YW5kIGNlcnRpZmljYXRpb24gcHJhY3RpY2Ugc3RhdGVtZW50cy4wNgYIKwYBBQUH\n" +
                "AgEWKmh0dHA6Ly93d3cuYXBwbGUuY29tL2NlcnRpZmljYXRlYXV0aG9yaXR5LzA2\n" +
                "BgNVHR8ELzAtMCugKaAnhiVodHRwOi8vY3JsLmFwcGxlLmNvbS9hcHBsZXd3ZHJj\n" +
                "YTIuY3JsMB0GA1UdDgQWBBScSNgfkpx6LX7Lq5tFrG/WgraVsDAOBgNVHQ8BAf8E\n" +
                "BAMCAygwTwYJKoZIhvdjZAYgBEIMQEQ2QkY5NzFDNERBNjYxN0MwRDg3MDdBNUFB\n" +
                "MTMwOTRDNjI5ODNEMUNBMkI3QjkzQjY1MDZDQUVGMjQzODEzNzcwCgYIKoZIzj0E\n" +
                "AwIDRwAwRAIgEjwvsX4AnfWkMCt8r5E4tgvI0p43l7a96HvMf5kQxRQCICsnf4W1\n" +
                "FoQZi5sP7UK3/+FsqUdZQiDg1KP33uMslhiX\n" +
                "-----END CERTIFICATE-----";
        return new ApplePayDecrypt(privateKeyPem, certPem);
    }

}

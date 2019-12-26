// 说了碰到的问题, 自然要玩玩签名与验签
package com.example.demo;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.security.Signature;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class RSASignature {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA256WithRSA";

    public static String sign(byte[] data, String privateKey) throws Exception {
        // 解密由 base64 编码的私钥
        final Base64.Encoder encoder = Base64.getEncoder();
        final Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(privateKey);

        // 构造 PKCS8EncodedKeySpec 对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encoder.encodeToString(signature.sign());
    }

    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

        // 解码由 base64 编码的公钥
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] keyBytes = publicKey.getBytes("UTF-8");

        // 构造 X509EncodedKeySpec 对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoder.decode(keyBytes));

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decoder.decode(sign));
    }

    public static void main(String[] args) throws Exception {
        String s = "hello world";
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCG7LKGJNYdurPDRjw9NPMP+qYFdj9HbLr1EVF6YvEhB8gK/OQYye6+N7T4CLPQdpcXhHrazqjEvIHONOcjxKKYBa3t5eBZx3CU+Mjp7XsOQONrPk4VzmGwenbrEoY1lrmI0ocb+TVWqo+XhDX0JwgdEvGwLJCyyCo8ZxwyQikyrQIDAQAB";
        // PKCS8格式私钥(可由PKCS1格式转换)
        String priKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIbssoYk1h26s8NGPD008w/6pgV2P0dsuvURUXpi8SEHyAr85BjJ7r43tPgIs9B2lxeEetrOqMS8gc405yPEopgFre3l4FnHcJT4yOntew5A42s+ThXOYbB6dusShjWWuYjShxv5NVaqj5eENfQnCB0S8bAskLLIKjxnHDJCKTKtAgMBAAECgYAlFRDLxXwDl7ioRrlvqQnUrnLKtYEWxE9t42CdXmwfQ+A7BN474eP365+VO+8PsEg+cm7BjGLeD2Etp2SPdA+rilgdyM4PhhxRdVI5JrF4/BrxzFZi4FGFSHC9DJvNgN5JgFln2Jxg/80NoUWv6jzQKt/WpFvkfqbyeleAM5anAQJBALzGHUCBkB/MTiqQPcg/hb7tYCKhDd6Fp/znhhfbLn+/BFcZtu35w1LnHV18Odrr1MzBlwTHpPyUK2/gUvYC630CQQC2+VLDyamzwNJohPz5ehLq2ijLZlHOBwvuNcav1w6kMwzxotT0bqll5gbEfyeCVgI8+lo/OpK7yiAu+gCukyrxAkAEF9OYi3ahid+EAr6kMeRngGQePBm94m4DCM4ahFEcyfqBHPifblPy3zn+xevHOGfTzLrGisGkDF5ovZdu6B9NAkBKhOJbUM9noPlQjhEFfd0kfvMsW6CneyE1w/sGdFzHLO9xnfKDFmqxUGyjLHIn1+WTdha5N565Eonm9/S9JLzRAkActLG5LwY/wvqyfPnrbxcXNjI/JdhnmAXoc86T/bsQMCuEeAkwLUwZLVciLornRnCnl38HL8DqVLHq5ss3Wl4M";
        String sign = "cPz4BuUiKXDDBXjTx5VcMFgDFdCKVfn50Idv7pYhmiivrmx94zk0Fpk6IbKjReiqaNfRhEqGCIVpdFNiKLVKfA==";
        String m = "hello world";
        String after_sign = sign(s.getBytes(), priKey);
        System.out.println(after_sign);
        boolean ok = verify(m.getBytes(), pubKey, sign);
        System.out.println(ok);
    }
}

/*  输出结果:
cPz4BuUiKXDDBXjTx5VcMFgDFdCKVfn50Idv7pYhmiivrmx94zk0Fpk6IbKjReiqaNfRhEqGCIVpdFNiKLVKfA==
true
*/

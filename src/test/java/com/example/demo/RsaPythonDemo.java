// 说了碰到的问题, 自然要玩玩签名与验签
package com.example.demo;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.security.Signature;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class RsaPythonDemo {

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
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDcpr7m19SU0Fa7Dfg7fQ0ueUSRWWDA577VsoSceQtd2whDYlI0I5Q4jZAxfPTZCECaft3eFWyBPn41lRP1GP4kWI/FLdYr3TVMx3kVzeF52IzF34EheuNmRlPCGvQY1RXcsAkFaVwEDUiads4dwxTW6hLVrbecqEnfOKKnCAFbFQIDAQAB";
        // PKCS8格式私钥(可由PKCS1格式转换)
        String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANymvubX1JTQVrsN+Dt9DS55RJFZYMDnvtWyhJx5C13bCENiUjQjlDiNkDF89NkIQJp+3d4VbIE+fjWVE/UY/iRYj8Ut1ivdNUzHeRXN4XnYjMXfgSF642ZGU8Ia9BjVFdywCQVpXAQNSJp2zh3DFNbqEtWtt5yoSd84oqcIAVsVAgMBAAECgYA1iuTiIVd9cPzK0T3+OX8cWuLvnk+jH9koA/S3/4tpj//2UgJ+km+iNX/a8mZ7z8vVcZmnDW3SAvcfS3iXTiRE/BPL662NkGytcD+uQTWUNDconJuG/+xdPzDHpOvK9U98msZuD3pTkHwiUW9yJhbEKWGqSvN9hEzEkqUjDAGx4QJBAPpl7V4o6xXZoFovGa0OtMa0E4wzeEnqugqCxfvO2PUcif0aQq/bCPE5hMderXS6gLtQ7/RiyI6uaQbABhHS1nkCQQDhlnQYWYqqw8+iLwul56ApR1qtgxQ7p4GIrpIj0qebKmftbkW2U42FLllybeJI1TB+7RvJLMUqMwXHWygIwzJ9AkEA39/v6WcGQOPx5ZAFEeB7KsloTMrb+qKeMbWYlDntasFYcm5fP2HDzgFSQPradOonHNMRp1wG2uL/TiSziI4MaQJAVU9geBTkq8+BJgREk2kU2Idzc0quzCB8jAP4oDN5zuQqiU+6bUkLq+HrUarceE6nGFjyuRGFPoAtH0cJzMaEwQJADguND6xgOzypE60aizHikdfsnMCJjd7M+opwEeHsMdo6yvLXDlFkBAB4RSH2GvtLqkbhvlfgq5Q9AhZNo+CN4g==";
        String sign = "Xx5mj1RBfwkmA3of3U9Bxt5ttanY4R9Rzk/fe6IyqtT7ZN1On44tWGMFP5NSCNAnEA5jUJMqr9vnjpUH+rXD0tII49IlHiTavHqhZU1MAws+O82FViI56i9j8qCqTodkiX9Qi38u1XQlV1ZyIdelShyL1JRdtGU1V2XL23cUanE=";
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

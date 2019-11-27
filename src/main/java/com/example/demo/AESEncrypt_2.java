package com.example.demo;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncrypt_2 {

    /**
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private static String sKey = "1234567891234567";
//    private static String ivParameter = "1234567890123456";

    public static void main(String[] args) {
        String raw = "{'asset_max_count':'5','license_create_datetime':'20190827','copyright':'紫关云技术有限公司 2019'}";

        try {
            String encryptRaw = AESEncrypt_2.encrypt(raw);
            System.out.println("加密串:" + encryptRaw);

            String decryptRaw = decrypt("VkrDMLs1EE3MXCbxP1uUIA==");

            System.out.println("解秘串:" + decryptRaw);

            String str = decrypt("VkrDMLs1EE3MXCbxP1uUIA==","1234567891234567","1234567890123456","ECB","PKCS5Padding");
            System.out.println("解秘串2:" + str);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public static String encrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        // return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
        return new String(Base64.getEncoder().encode(encrypted), "UTF-8");
    }

    // 解密
    public static String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String text,String sKey,String ivs,String encryptMode, String Complement ) throws Exception {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            if("ECB".equals(encryptMode)) {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            }else {
                IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            }
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] tmp1 = Base64.getDecoder().decode(text);
            byte[] tmp2 = cipher.doFinal(tmp1);
            String decodeSecrt = new String(tmp2, "utf-8");
            return decodeSecrt;
        } catch (Exception ex) {
            return "";
        }
    }

}
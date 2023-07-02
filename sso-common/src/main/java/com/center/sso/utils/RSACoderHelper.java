package com.center.sso.utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * 非对称加密算法RSA工具类
 */
public class RSACoderHelper {
    //非对称密钥算法
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 512;
    //公钥
    private static final String PUBLIC_KEY = "6916bc11-a2a1-1f39-4376-bd08fd94c106";

    //私钥
    private static final String PRIVATE_KEY = "9619c899-0330-3167-5513-66fe94ad1ce3";

    /**
     * 初始化密钥对
     *
     * @return Map 甲方密钥的Map
     */
    public static Map<String, Object> initKey() throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //甲方私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }


    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @param key       密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {

        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key       密钥
     * @return byte[] 加密数据
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

//    /**
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception {
//
//        String str = "111";
//
////        //初始化密钥
////        //生成密钥对
////        Map<String, Object> keyMap = RSACoderHelper.initKey();
////        //公钥
////        byte[] publicKey = RSACoderHelper.getPublicKey(keyMap);
////        //私钥
////        byte[] privateKey = RSACoderHelper.getPrivateKey(keyMap);
////        System.out.println("公钥：" + Base64.encodeBase64String(publicKey));
////        System.out.println("私钥：" + Base64.encodeBase64String(privateKey));
////
////        //公钥加密
////        byte[] bytes = RSACoderHelper.encryptByPublicKey(str.getBytes(), publicKey);
////        String s = Base64.encodeBase64String(bytes);
////        System.out.println("公钥加密后数据：" + s);
////
////        //私钥解密
////        byte[] bytes1 = Base64.decodeBase64(s);
////        byte[] bytes2 = RSACoderHelper.decryptByPrivateKey(bytes1, privateKey);
////        String s1 = new String(bytes2);
////
////        System.out.println("私钥解密后数据：" + s1);
//
//        //公钥
//        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIlMTdt/m/+w0W3JEzJYnF2NyEhoGQEAsVxKQrstXCveyOqgwSyvnoIc+Z0rfmAcSTUfFEZLJqQmLHVmuvqHMG8CAwEAAQ==";
//        //私钥
//        String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAiUxN23+b/7DRbckTMlicXY3ISGgZAQCxXEpCuy1cK97I6qDBLK+eghz5nSt+YBxJNR8URksmpCYsdWa6+ocwbwIDAQABAkBi8pHd0b6cbFLSeyoRi5jNN4QS4qq6dURdDcu/17XoiuRUupSLmNG95yRY7jRfN1NuR1yKRIRoPHjSDtiu1jWpAiEA8ewMEf6U6d/VEtfb6J6svs6t6wIhkP+cBjEff2SWEXsCIQCRSafyMxP7ld6K+A0vr6X6MBhaSy/Jd1/Aqw6rVcXonQIhALRj8swoLRoHUXZvhwb56o2Mx5qJSEY6kzj6wCXZ9xypAiB83RSdrxBJdHAidzS9+vNmpdcIIv4a46FDcL/WuIyycQIhAN8FWGeKRoche5roZm1e+Gekvz8P1tzKGs65ibALwcPV";
//        //公钥加密
//        byte[] bytes = RSACoderHelper.encryptByPublicKey(str.getBytes(),  Base64.decodeBase64(publicKey));
//        String s = Base64.encodeBase64String(bytes);
//        System.out.println("公钥加密后数据：" + s);
//
//        //私钥解密
//        byte[] bytes1 = Base64.decodeBase64(s);
//        byte[] bytes2 = RSACoderHelper.decryptByPrivateKey(bytes1, Base64.decodeBase64(privateKey));
//        String s1 = new String(bytes2);
//        System.out.println("解密后的数据：" + s1);
//    }


}

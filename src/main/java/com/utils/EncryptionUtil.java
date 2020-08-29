package com.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author WonderFour
 * @date 2020/8/10 10:18
 */
public class EncryptionUtil {
    /*
    md5加密
     */
//    public static String encryption(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        MessageDigest md5;
//        md5 = MessageDigest.getInstance("MD5");
//        BASE64Encoder base64en = new BASE64Encoder();
//        //加密后的字符串
//        return base64en.encode(md5.digest(password.getBytes(StandardCharsets.UTF_8)));
//    }

    public static String encryption(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
//确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
//加密后的字符串
        return Base64.encodeBase64String(md5.digest(str.getBytes()));
    }

    /*
    wj uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}

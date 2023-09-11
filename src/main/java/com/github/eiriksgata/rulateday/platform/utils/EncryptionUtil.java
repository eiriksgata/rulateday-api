package com.github.eiriksgata.rulateday.platform.utils;


import com.github.eiriksgata.rulateday.platform.exception.CommonBaseException;
import com.github.eiriksgata.rulateday.platform.exception.CommonBaseExceptionEnum;

import java.security.MessageDigest;

public class EncryptionUtil {

    /**
     * 对字符串进行md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return byteToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonBaseException(CommonBaseExceptionEnum.ACCOUNTS_AUTHENTICATION_ERROR);
        }
    }

    /**
     * 对字符串进行sha256加密
     *
     * @param str
     * @return
     */
    public static String sha256(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            return byteToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 对字符串进行sha1加密
     *
     * @param str
     * @return
     */
    public static String sha1(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(str.getBytes());
            return byteToHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 字节数组转16进制字符串
     *
     * @param data
     * @return
     */
    public static String byteToHex(byte[] data) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}

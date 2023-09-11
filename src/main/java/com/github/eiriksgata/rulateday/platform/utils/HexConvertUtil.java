package com.github.eiriksgata.rulateday.platform.utils;

public class HexConvertUtil {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * 十六进制字符串转字节数组
     *
     * @param hexString 如：FE00120F0E
     * @return
     */
    public static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        if (length % 2 == 1) {
            hexString = "0" + hexString;
            length++;
        }
        byte[] buffer = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            buffer[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return buffer;
    }


    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}

package com.milktea.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtil {
    /**
     * MD5加密
     *
     * @param input 输入字符串
     * @return 加密后的字符串
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成订单号
     *
     * @return 订单号
     */
    public static String generateOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(new Date());
        Random random = new Random();
        int randomNum = random.nextInt(900) + 100; // 生成3位随机数
        return "ORDER" + dateStr + randomNum;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 生成随机字符串
     *
     * @param length 长度
     * @return 随机字符串
     */
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
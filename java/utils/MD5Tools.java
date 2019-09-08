package com.xuecheng.manage_cms.service;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

/**
 * MD5密码加盐加密 Base64 编码解码
 * Create By @author:Yim On 2019-6-24 16:08
 **/
public class MD5Tools {

    /**
     * @description: 使用uuid生成随机盐
     * @author: yimugod
     * @params: []
     * @return: java.lang.String
     */
    public static String saltRandom() {
        UUID uuid = UUID.randomUUID();
        String[] split = uuid.toString().split("-");
        String uu = "";
        for (int i = 0; i < split.length; i++) {
            uu += split[i];
        }
        return uu;
    }

    /**
     * @description: 使用md进行加密
     * @author: yimugod
     * @params: [md]
     * @return: java.lang.String
     */
    public static String MD5Method(String md) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(md.getBytes("UTF-8"));
            for (int i = 0; i < bytes.length; i++) {
                byte aByte = bytes[i];
            }
            return toHex(bytes);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static String MD5Dencode() {
        return new String();
    }

    /**
     * @description: byte[] 数组进行十六进制转换,
     *  位运算符---- << >> 左移运算符和右移运算符,表示按照二进制的方式进行左右移动的位置(符合电脑计算方式,效率最高) >>>无符号位右移
     *  $ | ^ 分别表示按位与 按位或 和按位异与  (也是用二进制的方式进行判断) 1 & 0 = 0(一0为0) 1 | 0 =  1(一1为1) 0 ^ 0 =0 (相同为0 不同为1)
     *  二进制逢二进一 二进制是以0b开头
     *  八进制逢八进一 三位二进制一组 000 001 010 011 100 101 110 111(0~7)(二进制与八进制之间的关系) 以0开头的表示八进制
     *  十六进制逢十六进一 四位二进制一组0000 0001 0010 0011 0100 0101 0110 0111 1000 1001 1010 1011 1100 1101 1110 1111(0~F)(二进制与十六进制之间的关系) 以0x开头表示十六进制
     *  一个字节八位 byte表示字节 一个字节所占的大小在-128~127之间用二进制表示为 0000 0000 ~ 0111 1111(正数部分表示范围) 1000 0000 ~ 1111 1111(负数部分表示范围)
     *  (计算机的原码 反码 和补码 表示的都是整数概念-- 正整数时结果相同 负整数时反码对原码进行按位取反,补码在反码的基础上进行+1)
     *  -2  原码 0000 0010 反码 1111 1101 补码 1111 1110
     *  -128 原码 1000 0000 反码 0111 1111 补码 1000 0000
     *  m & n (进行二进制计算 以右侧为依据得到的结果<=n)
     * @author: yimugod
     * @params: [bytes]
     * @return: java.lang.String
     */
    private static String toHex(byte[] bytes) {
        final char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);//stringBuilder下for循环中使用两次,所以指定长度*2
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append(chars[(bytes[i] >> 4) & 0x0f]);//bytes[i]表示一个字节大小的整数,二进制右移四位表示的数值范围为(0~F)之间,并和16进制的15进行按位与 得到较小值
            stringBuilder.append(chars[bytes[i] & 0x0f]);//通过运算得到一个<=15的下标值
        }
        return stringBuilder.toString();
    }

    /**
     * @description: 使用Base64进行编码
     * @author: yimugod
     * @params: [data]
     * @return: java.lang.String
     */
    public static String encode(String data) {
        final Base64.Encoder encoder = Base64.getEncoder();
        final String encodeData = encoder.encodeToString(data.getBytes());
        return encodeData;
    }

    /**
     * @description: 使用Base64进行解码
     * @author: yimugod
     * @params: [data]
     * @return: java.lang.String
     */
    public static String dencode(String data) {
        final Base64.Decoder decoder = Base64.getDecoder();
        final byte[] decode = decoder.decode(data);
        return new String(decode);
    }

    /**
     * @description: 用于使用中使用==的方式没有比较没有结果.故使用equals进行比较进行测试
     * @author: yimugod
     * @params: [args]
     * @return: void
     */
    public static void main(String[] args) {
        String saltRandom = saltRandom();
        String md5Method = MD5Method(saltRandom + "123");
        String method = MD5Method(saltRandom + "123");
        System.out.println(md5Method);
        System.out.println(method);
        if (method == md5Method || md5Method.equals(method)) {
            System.out.println("MD5 没有问题!");
        }
    }
}

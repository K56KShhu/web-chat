package com.zkyyo.www.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 该类采用基于PBKDF22的加密算法, 封装了字符串的加密和验证功能
 * 原作者为havoc AT defuse.ca
 */
public class Pbkdf2Util {
    /**
     * 加密算法
     */
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
    /**
     * 盐字节数
     */
    private static final int SALT_BYTE_SIZE = 24;
    /**
     * 生成Hash字节数
     */
    private static final int HASH_BYTE_SIZE = 24;
    /**
     * 循环次数, 可用于控制时间
     */
    private static final int PBKDF2_ITERATIONS = 10000;

    /**
     * 生成字符串中记录循环次数的下标
     */
    private static final int ITERATION_INDEX = 0;
    /**
     * 生成字符串中记录盐的下标
     */
    private static final int SALT_INDEX = 1;
    /**
     * 生成字符串中Hash的下标
     */
    private static final int PBKDF2_INDEX = 2;

    /**
     * 获取加密后的Hash字符串
     *
     * @param password 待加密的字符串
     * @return 加密后的字符串
     */
    public static String createHash(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return createHash(password.toCharArray());
    }

    /**
     * 获取加密后的Hash字符串
     *
     * @param password 待加密的字节数组
     * @return 加密后的字符串
     */
    private static String createHash(char[] password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        //创建随机盐
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        //Hash
        byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        //构造 iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    /**
     * 验证输入的字符加密后是否与正确的Hash字符串相同
     *
     * @param password    待检查的字符串
     * @param correctHash 正确的Hash字符串
     * @return true相同, false不相同
     */
    public static boolean validatePassword(String password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        return validatePassword(password.toCharArray(), correctHash);
    }

    /**
     * 验证输入的字符加密后是否与正确的Hash字符串相同
     *
     * @param password    待检查的字节数组
     * @param correctHash 正确的Hash字符串
     * @return true相同, false不相同
     */
    private static boolean validatePassword(char[] password, String correctHash)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        //解析构造的字符串
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[ITERATION_INDEX]);
        byte[] salt = fromHex(params[SALT_INDEX]);
        byte[] hash = fromHex(params[PBKDF2_INDEX]);
        //使用同样的加密参数
        byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
        //比较
        return slowEquals(hash, testHash);
    }

    /**
     * 校验两个直接数组是否完全相同, 同时固定检验时间, 防止利用利用时间破解
     *
     * @param a 第一个字节数组
     * @param b 第二个字节数组
     * @return true相同, false不相同
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i]; //a[i]异或b[i], 不同时为1
        }
        return diff == 0;
    }

    /**
     * 通过盐, 循环次数, Hash长度计算出Hash
     *
     * @param password   待Hash的字符串
     * @param salt       盐
     * @param iterations 循环次数
     * @param bytes      Hash长度
     * @return 加密后的字符串
     */
    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    /**
     * 将一个十六进制的字符串转换为字节数组
     *
     * @param hex 十六进制字符串
     * @return 转换后的字节数组
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * 将字节数组转换为一个十六进制字符串
     *
     * @param array 字符串
     * @return 十六进制字符串
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}

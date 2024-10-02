package org.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;

public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    static Random rnd = new Random();

    public static String generateToken(int length) {
        if (length < 1) throw new IllegalArgumentException("length must be a positive number");
        Random random = new SecureRandom();
        StringBuilder token = new StringBuilder(length);
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < length; i++) {
            token.append(chars.charAt(random.nextInt(chars.length())));
        }
        return token.toString();
    }

    public static String generateNumericOtp(int length) {
        if (length < 1) throw new IllegalArgumentException("length must be a positive number");
        Random random = new SecureRandom();
        StringBuilder otp = new StringBuilder(length);
        String firstDigitPool = "123456789";
        String digitsPool = "0123456789";
        otp.append(firstDigitPool.charAt(random.nextInt(firstDigitPool.length())));
        for (int i = 1; i < length; i++) {
            otp.append(digitsPool.charAt(random.nextInt(digitsPool.length())));
        }
        return otp.toString();
    }

    public static String generateRandomText(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";

        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public static String generateRandomNumber(int len) {
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    public static String generateRandomAlphaNum(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

}

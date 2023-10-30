package com.user.userserver.util;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;


public class PBKDF2Util {

    /**
     * PBKDF2-HMAC-SHA512 加密算法
     */

    // 迭代计数为 120000
    private static final Integer DEFAULT_ITERATIONS = 120000;
    // 密文长度
    private static final Integer HASH_LENGTH = 512;

    /**
     * 获取密文
     *
     * @param password 密码明文
     * @param salt     加盐
     * @return String
     */
    private static String getEncodedHash(String password, String salt) {
        try {
            // Instance：PBKDF2WithHmacSHA1、PBKDF2WithHmacSHA256、PBKDF2WithHmacSHA224、PBKDF2WithHmacSHA512
            // 、PBKDF2WithHmacSHA384
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt),
                    DEFAULT_ITERATIONS,
                    HASH_LENGTH);
            return Base64.getEncoder().encodeToString(keyFactory.generateSecret(keySpec).getEncoded());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("无法检索 pbkdf2_sha256 算法：" + e);
        } catch (InvalidKeySpecException e) {
            System.out.println("无法生成密钥：" + e);
        }
        return null;
    }

    /**
     * 明文加盐
     *
     * @return String
     */
    private static String getSalt() {
        int length = 32; // 盐的长度
        Random rand = new Random();
        char[] rs = new char[length];
        for (int i = 0; i < length; i++) {
            int t = rand.nextInt(3);
            if (t == 0) {
                // 大写字母
                rs[i] = (char) (rand.nextInt(26) + 65);
            } else if (t == 1) {
                // 小写字母
                rs[i] = (char) (rand.nextInt(26) + 97);
            } else {
                // 数字
                rs[i] = (char) (rand.nextInt(10) + 48);
            }
        }
        return new String(rs);
    }

    /**
     * @param password 密码明文
     * @return String
     */
    public static String encode(String password) {
        return encode(password, getSalt());
    }


    /**
     * @param password 密码明文
     * @param salt     加盐
     * @return String
     */
    public static String encode(String password, String salt) {
        if (password == null) {
            throw new NullPointerException("password is null");
        }
        // 返回 salt&密文
        return String.format("%s$%s", salt, getEncodedHash(password, salt));
    }

    /**
     * 校验密码是否合法
     *
     * @param password       密码明文
     * @param hashedPassword 密码密文
     * @return boolean
     */
    public static boolean verification(String password, String hashedPassword) {
        if (password == null) {
            return false;
        }
        String[] parts = hashedPassword.split("\\$");
        if (parts.length != 2) {
            // 格式错误
            return false;
        }
        String salt = parts[0];
        String hash = encode(password, salt);
        return hash.equals(hashedPassword);
    }

    public static void main(String[] args) {
        System.out.println(PBKDF2Util.encode("123456"));
        String en = "nqiRCdB81937etc4LYf0266hJ3l8Tb51$Bm0sAmpXbuE50dpJAJFERQcA2Tr0oGZ/JciTOCBcgMVf1H22Nve9HpiO" +
                "/eip7PzpMI6cOOlg1WpsbVbt60hYoQ==";
        System.out.println(PBKDF2Util.verification("123456", en));
//        System.out.println(PBKDF2Util.getSalt());
    }
}

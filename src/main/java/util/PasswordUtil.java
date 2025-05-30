package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String hashPassword(String password) {
            String hashString = "";
            try {
                java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
                byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
                for (int i = 0; i < hash.length; i++) {
                    hashString += Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1, 3);
                }
            } catch (java.security.NoSuchAlgorithmException e) {
                System.out.println(e);
            }
            return hashString;
        }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String inputHash = hashPassword(plainPassword);
        return inputHash.equals(hashedPassword); //true se corrispondono
    }
}
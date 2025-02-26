package com.air_app.security;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.*;

public class PasswordEncrypt {
    private static final String KEY_FILE = "secret.key"; // Store encryption key

    // Generate and save SecretKey (Only once!)
    public static SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey = keyGenerator.generateKey();

            saveSecretKey(secretKey); // Save key to file
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Save SecretKey to a file
    private static void saveSecretKey(SecretKey secretKey) {
        try (FileOutputStream fos = new FileOutputStream(KEY_FILE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(secretKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load SecretKey from file
    public static SecretKey loadSecretKey() {
        File keyFile = new File(KEY_FILE);
        if (!keyFile.exists()) {
            System.out.println("⚠ Secret key file not found! Generating a new one...");
            return generateSecretKey(); // Generate and save a new key
        }

        try (FileInputStream fis = new FileInputStream(KEY_FILE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (SecretKey) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Encrypt Password
    public static String encrypt(String password) {
        try {
            SecretKey secretKey = loadSecretKey();
            if (secretKey == null) {
                throw new RuntimeException("❌ SecretKey is null! Cannot encrypt.");
            }

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Decrypt Password
    public static String decrypt(String encryptedPassword) {
        try {
            SecretKey secretKey = loadSecretKey();
            if (secretKey == null) {
                throw new RuntimeException("❌ SecretKey is null! Cannot decrypt.");
            }

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));

            return new String(decryptedBytes);
        } catch (Exception e) {
            System.out.println("⚠ Error decrypting password: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

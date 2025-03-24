package com.example.demo.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptorUtils {

    public static void main(String[] args) {
        String secretKey = "xxxxxx"; // 確保這與環境變數一致
        String plainText = "rootcsie"; // 你要加密的內容

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretKey);
        encryptor.setAlgorithm("PBEWITHMD5ANDDES"); // 確保與應用程式設定的演算法一致
        String encryptedText = encryptor.encrypt(plainText);

        System.out.println("加密後的密文: ENC(" + encryptedText + ")");
    }
}
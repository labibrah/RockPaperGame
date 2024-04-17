package com.gradle.example;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HMAC {
    SecretKeySpec secretKeySpec;
    Mac mac;
    String data;

    public HMAC(String algorithm, String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        this.data = data;
        secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);

    }

    public String getHMACkey() {
        return bytesToHex(mac.doFinal(data.getBytes()));
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
}

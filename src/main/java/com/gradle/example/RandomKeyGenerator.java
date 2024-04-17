package com.gradle.example;


import java.security.SecureRandom;

public class RandomKeyGenerator {

    public RandomKeyGenerator() {

    }

    public String getGeneratedString() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[32];
        random.nextBytes(bytes);
        //converting random bytes to hex
        return HMAC.bytesToHex(bytes);

    }
}

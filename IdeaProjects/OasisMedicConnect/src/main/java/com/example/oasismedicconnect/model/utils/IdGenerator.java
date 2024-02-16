package com.example.oasismedicconnect.model.utils;

import java.util.Random;

public class IdGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int ID_LENGTH = 6;

    public static String generateRandomId() {
        StringBuilder idBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            idBuilder.append(randomChar);
        }

        return idBuilder.toString();
    }
}

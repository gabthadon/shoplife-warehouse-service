package com.softnet.shoplife.utils;

import java.time.Year;
import java.util.Random;

public class IDGenerator {

    public static String generateID(String IdName) {
        int year = Year.now().getValue();
        int DIVIDER_LENGTH = 4;
        int DIVIDER_LENGTH1 = 3;

        String randomDigits1 = generateRandomDigits(DIVIDER_LENGTH);
        String randomDigits2 = generateRandomDigits(DIVIDER_LENGTH1);

        return String.format("%s%s%s", IdName, randomDigits1, randomDigits2);
    }

    private static String generateRandomDigits(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 1; i <= length; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }

}

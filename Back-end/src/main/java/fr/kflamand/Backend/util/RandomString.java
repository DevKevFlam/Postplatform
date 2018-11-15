package fr.kflamand.Backend.util;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public final class RandomString {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

    private static final String DIGITS = "0123456789";

    private static final String candidateChars = UPPER + LOWER + DIGITS;
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String generateRandomString(int length) {

        StringBuilder token = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            token.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
        }

        return token.toString();
    }
}
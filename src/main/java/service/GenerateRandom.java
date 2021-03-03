package service;

import java.util.Random;

public class GenerateRandom {
    private static final String UPPERCASE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_ALPHABET = UPPERCASE_ALPHABET.toLowerCase();
    private static final String NUMERIC = "0123456789";
    private static final String ALPHANUMERIC = UPPERCASE_ALPHABET + LOWERCASE_ALPHABET + NUMERIC;

    public String generateString(int size) {
        String[] range = ALPHANUMERIC.split("");

        Random random = new Random();
        StringBuffer result = new StringBuffer();

        random.ints(0, range.length)
                .limit(size)
                .forEach((i) -> result.append(range[i]));

        return result.toString();
    }
}

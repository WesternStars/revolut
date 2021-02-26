package service;

import exception.MaxSeoLengthException;

import java.util.Random;

public class ShortUrlGeneratorService {

    private static final String DOMAIN = "short.com";
    public static final int MAX_LENGTH = 20;
    private static final String REGEX_DOMAIN = "\\w+\\.\\w+";
    private static final int SIZE_RANDOM_URL_BLOCK = 4;

    public static String generateSeoUrl(String url, String seo) {
        if (seo.length() > MAX_LENGTH) {
            throw new MaxSeoLengthException("Max length " + MAX_LENGTH);
        }

        String shortUrl = url.replaceFirst(REGEX_DOMAIN, DOMAIN);
        int lastIndexDomain = shortUrl.lastIndexOf(DOMAIN) + DOMAIN.length();

        return shortUrl.substring(0, lastIndexDomain) + "/" + seo;
    }

    public static String generateShortUrl(String url) {
        int leftLimit = 48;
        int rightLimit = 122;
        Random random = new Random();
        String seoRandom = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> i <= 57 || (i > 64 && i < 91) || i > 96)
                .limit(SIZE_RANDOM_URL_BLOCK)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return ShortUrlGeneratorService.generateSeoUrl(url, seoRandom);
    }
}

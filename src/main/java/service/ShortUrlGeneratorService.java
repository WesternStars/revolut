package service;

import java.net.URI;
import java.net.URISyntaxException;

public class ShortUrlGeneratorService {

    public static final int MAX_LENGTH = 20;
    private static final int SIZE_RANDOM_URL_BLOCK = 4;
    private static final String HOST = "short.com";
    private static final String REGEX = "\\w+://\\w+\\.\\w+/\\w+";

    private final GenerateRandom generateRandom;

    public ShortUrlGeneratorService() {
        this.generateRandom = new GenerateRandom();
    }

    public String generateSeoUrl(String address, String seo) throws URISyntaxException {
        if (validateLengthSeo(seo)) {
            throw new IllegalArgumentException("Length seo parameter must be from 1 to " + MAX_LENGTH);
        }

        if (validateAddressStructure(address)) {
            throw new IllegalArgumentException("Example: http://looooong.com/somepath");
        }

        URI uri = URI.create(address);
        URI shortUri = new URI(uri.getScheme(), HOST, "/" + seo, null);
        return shortUri.toString();
    }

    public String generateShortUrl(String url) throws URISyntaxException {
        String seoRandom = generateRandom.generateString(SIZE_RANDOM_URL_BLOCK);
        return generateSeoUrl(url, seoRandom);
    }

    private boolean validateLengthSeo(String seo) {
        return !(seo.length() > 0 && seo.length() <= MAX_LENGTH);
    }

    private boolean validateAddressStructure(String address) {
        return !address.matches(REGEX);
    }
}

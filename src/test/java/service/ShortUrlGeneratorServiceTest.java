package service;

import exception.MaxSeoLengthException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ShortUrlGeneratorServiceTest {

    private static final String TEST_URL = "http://looooong.com/somepath";

    private ShortUrlGeneratorService shortUrlGeneratorService;

    @BeforeEach
    void init() {
        shortUrlGeneratorService = new ShortUrlGeneratorService();
    }

    @ParameterizedTest
    @CsvSource({"MY-NEW-WS, http://short.com/MY-NEW-WS", "POTATO, http://short.com/POTATO"})
    void generateSeoUrl_correctSeo_True(String seo, String expected) throws URISyntaxException {
        String actual = shortUrlGeneratorService.generateSeoUrl(TEST_URL, seo);
        assertEquals(expected, actual);
    }

    @Test
    void generateSeoUrl_invalidSeoLength_MaxSeoLengthExceptionThrown() {
        String seo = Stream.generate(() -> "1").limit(ShortUrlGeneratorService.MAX_LENGTH + 1).toString();
        assertThrows(MaxSeoLengthException.class, () -> shortUrlGeneratorService.generateSeoUrl(TEST_URL, seo));
    }

    @ParameterizedTest
    @CsvSource({"http://looooong.com/", "http://looooong./test", "http://.com/test", "://looooong.com/test"})
    void generateSeoUrl_invalidAddress_IllegalArgumentExceptionThrown(String uri) {
        assertThrows(IllegalArgumentException.class, () -> shortUrlGeneratorService.generateSeoUrl(uri, "test"));
    }

    @Test
    void generateShortUrl_autoGenerate_True() throws URISyntaxException {
        String result = shortUrlGeneratorService.generateShortUrl(TEST_URL);
        assertTrue(result.matches("\\w+://\\w+\\.\\w+/[A-Za-z0-9]{4}"));
    }

    @Test
    void generateShortUrl_autoGenerate2UriNotEquals_True() throws URISyntaxException {
        String result = shortUrlGeneratorService.generateShortUrl(TEST_URL);
        String result2 = shortUrlGeneratorService.generateShortUrl(TEST_URL);
        assertNotEquals(result, result2);
    }
}
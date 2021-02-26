import exception.MaxSeoLengthException;
import org.junit.Assert;
import org.junit.Test;
import service.ShortUrlGeneratorService;

import java.util.stream.Stream;

/**
 *
 */
public class ShortUrlGeneratorServiceTest {

    private static final String TEST_URL = "http://looooong.com/somepath";

    @Test
    public void generateSeoUrl_correctSeo_True() {
        String seo = "MY-NEW-WS";
        String expected = "http://short.com/MY-NEW-WS";
        String actual = ShortUrlGeneratorService.generateSeoUrl(TEST_URL, seo);
        Assert.assertEquals(expected, actual);

        String seo2 = "POTATO";
        String expected2 = "http://short.com/POTATO";
        String actual2 = ShortUrlGeneratorService.generateSeoUrl(TEST_URL, seo2);
        Assert.assertEquals(expected2, actual2);
    }

    @Test(expected = MaxSeoLengthException.class)
    public void generateSeoUrl_invalidSeoLength_MaxSeoLengthExceptionThrown() {
        String seo = Stream.generate(() -> "1").limit(ShortUrlGeneratorService.MAX_LENGTH + 1).toString();
        ShortUrlGeneratorService.generateSeoUrl(TEST_URL, seo);
    }

    @Test
    public void generateShortUrl_autoGenerate_True() {
        String result = ShortUrlGeneratorService.generateShortUrl(TEST_URL);
        Assert.assertEquals(result.matches("\\w+://\\w+\\.\\w+/[A-Za-z0-9]{4}"), true);
    }
}
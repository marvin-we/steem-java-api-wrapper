package eu.bittrade.libs.steemj.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

/**
 * Test some specific methods of the {@link SteemitUtils} class.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemitUtilsTest {
    /**
     * Test if all expected links are extracted from a given test string by
     * using the {@link SteemitUtils#extractLinksFromContent(String)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testExtractLinksFromContent() {
        List<String> extractedUrls = SteemitUtils.extractLinksFromContent(
                "Welcome to https://steemit.com/ which is also reachable by http://www.steemit.com/ .");

        assertThat(extractedUrls, contains(equalTo("https://steemit.com/"), equalTo("http://www.steemit.com/")));
    }

    /**
     * Test if all expected links are extracted from a given test string by
     * using the {@link SteemitUtils#extractUsersFromContent(String)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testExtractUsernamesFromContent() {
        List<String> extractedUsernames = SteemitUtils.extractUsersFromContent(
                "This post by @dez1337 and has been liked by @steemj so it contains two usernames in total.");

        assertThat(extractedUsernames, contains(equalTo("dez1337"), equalTo("steemj")));
    }
}

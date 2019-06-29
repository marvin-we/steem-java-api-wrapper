/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

/**
 * Test some specific methods of the {@link CondenserUtils} class.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CondenserUtilsTest {
    private static final String DUMMY_TEXT_LINKS = "Welcome to https://steemit.com/ which is also reachable by http://www.steemit.com/ .";
    private static final String DUMMY_TEXT_USER = "This post by @dez1337 and has been liked by @steemj so it contains two usernames in total.";
    private static final String EXPECTED_JSON = "{\"tags\":[\"test\",\"dontvote\"],\"users\":[\"dez1337\",\"steemj\"],\"links\":"
            + "[\"https://steemit.com/\",\"http://www.steemit.com/\"],\"app\":\"steemj/0.4.1\",\"format\":\"markdown\"}";

    /**
     * Test if all expected links are extracted from a given test string by
     * using the {@link CondenserUtils#extractLinksFromContent(String)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testExtractLinksFromContent() {
        List<String> extractedUrls = CondenserUtils.extractLinksFromContent(DUMMY_TEXT_LINKS);

        assertThat(extractedUrls, contains(equalTo("https://steemit.com/"), equalTo("http://www.steemit.com/")));
    }

    /**
     * Test if all expected links are extracted from a given test string by
     * using the {@link CondenserUtils#extractUsersFromContent(String)} method.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testExtractUsernamesFromContent() {
        List<String> extractedUsernames = CondenserUtils.extractUsersFromContent(DUMMY_TEXT_USER);

        assertThat(extractedUsernames, contains(equalTo("dez1337"), equalTo("steemj")));
    }

    /**
     * Test if the
     * {@link CondenserUtils#generateSteemitMetadata(String, String[], String, String)}
     * method.
     */
    @Test
    public void testGenerateSteemitMetadata() {
        assertThat(CondenserUtils.generateSteemitMetadata(DUMMY_TEXT_LINKS + DUMMY_TEXT_USER,
                new String[] { "test", "dontvote" }, "steemj/0.4.1", "markdown", null), equalTo(EXPECTED_JSON));
    }

    /**
     * Test if the {@link CondenserUtils#createPermlinkString(String)} method is
     * working correctly.
     */
    @Test
    public void testCreatePermlinkString() {
        // Check trimming, lowercasing, space replacement
        assertEquals("test-title-1", CondenserUtils.createPermlinkString(" TEST TITLE 1 "));

        // Only numbers, letters and hyphens allowed
        assertEquals("test-title-2", CondenserUtils.createPermlinkString("TEST TITLE 2!"));

        // Contiguous hyphens should be replaced with a single hyphen
        assertEquals("test-title-3", CondenserUtils.createPermlinkString("TEST TITLE  3"));
    }
}

package eu.bittrade.libs.steemj.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

/**
 * Test the brain key dictionary import.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class BrainkeyDictionaryManagerTest {
    private static final int NUMBER_OF_WORDS = 49744;

    /**
     * Verify that the brain key dictionary has the correct amount of words.
     */
    @Test
    public void testBrainkeyDictionaryManager() {
        assertThat(BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary().length, equalTo(NUMBER_OF_WORDS));
    }
}

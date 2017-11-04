package eu.bittrade.libs.steemj.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test some specific methods of the {@link SteemJUtils} class.
 * 
 * @author <a href=\"http://steemit.com/@dez1337\">dez1337</a>
 */
public class SteemJUtilsTest {
    /**
     * Test if the {@link SteemJUtils#verifyJsonString(String)} method is
     * working correctly.
     */
    @Test
    public void testExtractLinksFromContent() {
        assertTrue(SteemJUtils.verifyJsonString("{\"menu\": {\"id\": \"file\", \"value\": \"File\", \"popup\": "
                + "{ \"menuitem\": [ {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"}, "
                + "{\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},"
                + "{\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}]}}}"));
        assertFalse(SteemJUtils.verifyJsonString("{\"menu\": {\"id\": \"file\", \"value\": \"File\", \"popup\": "
                + "{ \"menuitem\": [ {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"}, "
                + "{\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},"
                + "{\"value\": [\"Close\",] \"onclick\": \"CloseDoc()\"}]}}"));
    }

    /**
     * Test if the {@link SteemJUtils#createPermlinkString(String)} method is
     * working correctly.
     */
    @Test
    public void testCreatePermlinkString() {
        // Check trimming, lowercasing, space replacement
        assertEquals("test-title-1", SteemJUtils.createPermlinkString(" TEST TITLE 1 "));

        // Only numbers, letters and hyphens allowed
        assertEquals("test-title-2", SteemJUtils.createPermlinkString("TEST TITLE 2!"));

        // Contiguous hyphens should be replaced with a single hyphen
        assertEquals("test-title-3", SteemJUtils.createPermlinkString("TEST TITLE  3"));
    }
}

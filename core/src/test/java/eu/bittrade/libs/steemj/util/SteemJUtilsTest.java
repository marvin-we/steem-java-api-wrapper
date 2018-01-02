package eu.bittrade.libs.steemj.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.InvalidParameterException;

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
    public void testVerifyJsonString() {
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
     * Test if the {@link SteemJUtils#setIfNotNull(Object, String, Object)}
     * method is working correctly.
     */
    @Test
    public void testSetIfNotNull() {
        Object objectToSet = new Object();
        Object nullObject = null;
        Object defaultValue = new Object();
        String exampleMessage = "TestMessage";

        assertThat(SteemJUtils.setIfNotNull(objectToSet, exampleMessage), equalTo(objectToSet));

        try {
            SteemJUtils.setIfNotNull(nullObject, exampleMessage);
            fail();
        } catch (InvalidParameterException e) {
            assertThat(e.getMessage(), equalTo(exampleMessage));
        }

        assertThat(SteemJUtils.setIfNotNull(objectToSet, defaultValue), equalTo(objectToSet));
        assertThat(SteemJUtils.setIfNotNull(nullObject, defaultValue), equalTo(defaultValue));

        try {
            SteemJUtils.setIfNotNull(nullObject, nullObject);
            fail();
        } catch (InvalidParameterException e) {
            assertThat(e.getMessage(), equalTo("Both, the objectToSet and the default value are null."));
        }
    }
}

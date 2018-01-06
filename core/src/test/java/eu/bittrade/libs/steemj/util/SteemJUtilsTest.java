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

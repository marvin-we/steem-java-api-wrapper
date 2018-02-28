package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.security.InvalidParameterException;

import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;

/**
 * Test the Permlink object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class PermlinkTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "28737465656d6a2d76302d322d342d6861732d6265656e2d72656c65617365642d7570646174652d39";

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.base.models.Permlink#toByteArray()
     * toByteArray()} method.
     * 
     * @throws Exception
     *             In case of a problem.
     */
    @Test
    public void testPermlinkToByteArray() throws Exception {
        Permlink customPermlink = new Permlink("steemj-v0-2-4-has-been-released-update-9");

        assertThat("Expect that the accountName object has the given byte representation.",
                CryptoUtils.HEX.encode(customPermlink.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.Permlink#setLink(String link)
     * setLink(String link)} method by providing an permlink which is too long.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPermlinkValidationTooLong() {
        new Permlink("steemj-v0-2-4-has-been-released-update-9-steemj-v0-2-4-has-been-released-update-9-steemj"
                + "-v0-2-4-has-been-released-update-9-steemj-v0-2-4-has-been-released-update-9-steemj-v0-2-4-h"
                + "as-been-released-update-9-steemj-v0-2-4-has-been-released-update-9-steemj-v0-2-4-has-been-r"
                + "eleased-update-9-steemj-v0-2-4-has-been-released-update-9-steemj-v0-2-4-has-been-released-u"
                + "pdate-9");
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.Permlink#setLink(String link)
     * setLink(String link)} method by providing an permlink with an invalid
     * character.
     */
    @Test(expected = InvalidParameterException.class)
    public void testPermlinkValidationWrongChar() {
        new Permlink("steemj-v0+2-4-has-been-released-update-9");
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.Permlink#setLink(String link)
     * setLink(String link)} method by providing a valid permlink.
     */
    @Test
    public void testPermlinkValidation() {
        new Permlink("steemj-v0-2-4-has-been-released-update-9");
    }
}

package eu.bittrade.libs.steemj.base.models.operations;

import java.io.UnsupportedEncodingException;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseUnitTest;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * Test the transformation of a Steem "prove authority operation".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ProveAuthorityOperationTest extends BaseUnitTest {

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();

    }

    @Test
    public void testVoteOperationToByteArray() throws UnsupportedEncodingException, SteemInvalidTransactionException {
        // TODO
    }

    @Test
    public void testVoteOperationTransactionHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException {
        // TODO
    }
}

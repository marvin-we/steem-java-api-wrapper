package eu.bittrade.libs.steem.api.wrapper;

import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BaseTest {
    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    protected static final short REF_BLOCK_NUM = (short) 34294;
    protected static final long REF_BLOCK_PREFIX = 3707022213L;
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";

    protected static Transaction transaction;

    protected BaseTest() {
        try {
            transaction = new Transaction();
            transaction.setExpirationDate(EXPIRATION_DATE);
            transaction.setRefBlockNum(REF_BLOCK_NUM);
            transaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
            // TODO: Add extensions when supported.
            // transaction.setExtensions(extensions);
        } catch (ParseException e) {
            LOGGER.error("Could not parse the given expiration date. - Test execution stopped.", e);
        }
    }
}

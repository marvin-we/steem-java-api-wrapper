package eu.bittrade.libs.steemj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.base.models.Transaction;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTest {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    protected static final short REF_BLOCK_NUM = (short) 34294;
    protected static final long REF_BLOCK_PREFIX = 3707022213L;
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";

    protected static Transaction transaction;

    protected static void setupBasicTestEnvironment() {
        transaction = new Transaction();
        transaction.setExpirationDate(new TimePointSec(EXPIRATION_DATE));
        transaction.setRefBlockNum(REF_BLOCK_NUM);
        transaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
    }
}

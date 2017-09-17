package eu.bittrade.libs.steemj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class defines static properties used in all tests.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTest {
    protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    protected static final short REF_BLOCK_NUM = (short) 34294;
    protected static final long REF_BLOCK_PREFIX = 3707022213L;
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";
}

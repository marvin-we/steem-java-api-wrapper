package eu.bittrade.libs.steemj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joou.UInteger;
import org.joou.UShort;

/**
 * This class defines static properties used in all tests.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTest {
    protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

    protected static final UShort REF_BLOCK_NUM = UShort.valueOf((short) 34294);
    protected static final UInteger REF_BLOCK_PREFIX = UInteger.valueOf(3707022213L);
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";
}

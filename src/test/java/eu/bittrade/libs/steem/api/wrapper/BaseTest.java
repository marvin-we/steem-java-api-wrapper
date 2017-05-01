package eu.bittrade.libs.steem.api.wrapper;

import org.junit.BeforeClass;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTest {
    protected static final SteemApiWrapperConfig CONFIG = SteemApiWrapperConfig.getInstance();

    protected static final String WIF = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final short REF_BLOCK_NUM = (short) 34294;
    protected static final long REF_BLOCK_PREFIX = 3707022213L;
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";

    @BeforeClass
    public static void setUp() {
        CONFIG.setPrivateKey(PrivateKeyType.POSTING, WIF);
    }
}

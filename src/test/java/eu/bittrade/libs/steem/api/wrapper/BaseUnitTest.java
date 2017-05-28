package eu.bittrade.libs.steem.api.wrapper;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemJConfig;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseUnitTest extends BaseTest {
    protected static final SteemJConfig CONFIG = SteemJConfig.getInstance();

    protected static final String PRIVATE_POSTING_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final String PRIVATE_ACTIVE_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";

    protected static void setupUnitTestEnvironment() {
        setupBasicTestEnvironment();

        CONFIG.setPrivateKey(PrivateKeyType.POSTING, PRIVATE_POSTING_KEY);
        CONFIG.setPrivateKey(PrivateKeyType.ACTIVE, PRIVATE_ACTIVE_KEY);
    }
}

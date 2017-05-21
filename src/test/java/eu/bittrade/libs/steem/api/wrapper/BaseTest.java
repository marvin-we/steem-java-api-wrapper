package eu.bittrade.libs.steem.api.wrapper;

import org.junit.BeforeClass;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemJConfig;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTest {
    protected static final SteemJConfig CONFIG = SteemJConfig.getInstance();

    protected static final String PRIVATE_POSTING_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final String PRIVATE_ACTIVE_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final short REF_BLOCK_NUM = (short) 34294;
    protected static final long REF_BLOCK_PREFIX = 3707022213L;
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";

    protected static Transaction transaction;
    
    @BeforeClass
    public static void setUp() throws Exception {
        CONFIG.setPrivateKey(PrivateKeyType.POSTING, PRIVATE_POSTING_KEY);
        CONFIG.setPrivateKey(PrivateKeyType.ACTIVE, PRIVATE_ACTIVE_KEY);
        
        transaction = new Transaction();
        transaction.setExpirationDate(EXPIRATION_DATE);
        transaction.setRefBlockNum(REF_BLOCK_NUM);
        transaction.setRefBlockPrefix(REF_BLOCK_PREFIX);
        // TODO: Add extensions when supported.
        // transaction.setExtensions(extensions);
    }
}

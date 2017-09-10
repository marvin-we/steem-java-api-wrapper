package eu.bittrade.libs.steemj;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseUnitTest extends BaseTest {
    protected static final SteemJConfig CONFIG = SteemJConfig.getInstance();

    protected static final String PRIVATE_POSTING_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final String PRIVATE_ACTIVE_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";

    protected static void setupUnitTestEnvironment() {
        setupBasicTestEnvironment();

        List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();

        privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING, PRIVATE_POSTING_KEY));
        privateKeys.add(new ImmutablePair<>(PrivateKeyType.ACTIVE, PRIVATE_ACTIVE_KEY));

        CONFIG.getPrivateKeyStorage().addAccount(new AccountName("dez1337"), privateKeys);
        CONFIG.getPrivateKeyStorage().addAccount(new AccountName("foobara"), privateKeys);
        CONFIG.getPrivateKeyStorage().addAccount(new AccountName("foobarc"), privateKeys);
        CONFIG.getPrivateKeyStorage().addAccount(new AccountName("foo"), privateKeys);
        CONFIG.getPrivateKeyStorage().addAccount(new AccountName("steemj"), privateKeys);
        CONFIG.getPrivateKeyStorage().addAccount(new AccountName("xeroc"), privateKeys);
    }
}

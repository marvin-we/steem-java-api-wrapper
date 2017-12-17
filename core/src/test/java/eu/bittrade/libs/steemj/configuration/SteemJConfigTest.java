package eu.bittrade.libs.steemj.configuration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJConfigTest {
    private static final String STEEMJ_API_USERNAME = "customnodename";
    private static final String STEEMJ_API_PASSWORD = "test1234";
    private static final String STEEMJ_KEY_ACCOUNTNAME = "dez1337";
    private static final String STEEMJ_KEY_POSTING = "5JpbHHrEkoLsxNcddo5YaTgtmgDegTcjk8i7BDPiTbMefrPnjWK";
    private static final String STEEMJ_KEY_ACTIVE = "5J6a9B9H1rBC9XsxHUrv9Eu98cG4MaZPuaMk6LBfMSDGyk5SoiP";
    private static final String STEEMJ_KEY_OWNER = "5JhxZZ6oGwFm2egPWyy21DWvroSoUur33sEHBamobDdSmhPN9U4";
    private static final String STEEMJ_KEY_MEMO = "5Hw3qRsC3f9yLtVazZpA8LyCUozBJq5aQv9tNNnz8fcg8BqoAWw";

    /**
     * Setup the required system properties.
     */
    @BeforeClass
    public static void setUp() {
        System.setProperty("steemj.api.username", STEEMJ_API_USERNAME);
        System.setProperty("steemj.api.password", STEEMJ_API_PASSWORD);
        System.setProperty("steemj.default.account", STEEMJ_KEY_ACCOUNTNAME);
        System.setProperty("steemj.default.account.posting.key", STEEMJ_KEY_POSTING);
        System.setProperty("steemj.default.account.active.key", STEEMJ_KEY_ACTIVE);
        System.setProperty("steemj.default.account.owner.key", STEEMJ_KEY_OWNER);
        System.setProperty("steemj.default.account.memo.key", STEEMJ_KEY_MEMO);

        // As there may have been other tests earlier we need to create a new
        // SteemJConfig instance so the parameter above will take effect.
        SteemJConfig.getNewInstance();
    }

    /**
     * Test if the system properties have been parsed correctly by SteemJ.
     */
    @Test
    public void testSettingsThroughSystemProperties() {
        assertThat(SteemJConfig.getInstance().getApiUsername(), equalTo(new AccountName(STEEMJ_API_USERNAME)));
        assertThat(String.valueOf(SteemJConfig.getInstance().getApiPassword()), equalTo(STEEMJ_API_PASSWORD));

        AccountName accountName = SteemJConfig.getInstance().getPrivateKeyStorage().getAccounts().get(0);
        assertThat(accountName, equalTo(new AccountName(STEEMJ_KEY_ACCOUNTNAME)));
        assertThat(
                SteemJConfig.getInstance().getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.POSTING, accountName)
                        .decompress().getPrivateKeyEncoded(128).toBase58(),
                equalTo(STEEMJ_KEY_POSTING));
        assertThat(SteemJConfig.getInstance().getPrivateKeyStorage()
                .getKeyForAccount(PrivateKeyType.ACTIVE, accountName).decompress().getPrivateKeyEncoded(128).toBase58(),
                equalTo(STEEMJ_KEY_ACTIVE));
        assertThat(SteemJConfig.getInstance().getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.OWNER, accountName)
                .decompress().getPrivateKeyEncoded(128).toBase58(), equalTo(STEEMJ_KEY_OWNER));
        assertThat(SteemJConfig.getInstance().getPrivateKeyStorage().getKeyForAccount(PrivateKeyType.MEMO, accountName)
                .decompress().getPrivateKeyEncoded(128).toBase58(), equalTo(STEEMJ_KEY_MEMO));
    }

    /**
     * Test if the version and the application name have been set correctly
     * during the build process.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testVersionAndName() {
        assertThat(SteemJConfig.getSteemJVersion(), notNullValue());
        assertThat(SteemJConfig.getSteemJAppName(), notNullValue());
    }
}

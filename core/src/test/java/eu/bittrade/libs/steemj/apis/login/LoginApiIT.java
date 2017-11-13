package eu.bittrade.libs.steemj.apis.login;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.apis.login.models.SteemVersionInfo;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.apis.login.LoginApi LoginApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LoginApiIT extends BaseIT {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     */
    @BeforeClass
    public static void init() throws SteemCommunicationException {
        setupIntegrationTestEnvironment();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.login.LoginApi#login(CommunicationHandler, eu.bittrade.libs.steemj.base.models.AccountName, String)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testLogin() throws SteemCommunicationException, SteemResponseException {
        assertTrue("Expect login to always return success: true",
                LoginApi.login(COMMUNICATION_HANDLER, new AccountName("gilligan"), "s.s.minnow"));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.login.LoginApi#getApiByName(CommunicationHandler, String)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetApiByName() throws SteemCommunicationException, SteemResponseException {
        final Integer bogus = LoginApi.getApiByName(COMMUNICATION_HANDLER, "bogus_api");
        final Integer database = LoginApi.getApiByName(COMMUNICATION_HANDLER, "database_api");
        final Integer login = LoginApi.getApiByName(COMMUNICATION_HANDLER, "login_api");
        final Integer market_history = LoginApi.getApiByName(COMMUNICATION_HANDLER, "market_history_api");
        final Integer follow = LoginApi.getApiByName(COMMUNICATION_HANDLER, "follow_api");

        assertNull("Expect that bogus api does not exist", bogus);
        assertNotNull("Expect that database api does exist", database);
        assertNotNull("Expect that login api does exist", login);
        assertNotNull("Expect that market_history api does exist", market_history);
        assertNotNull("Expect that follow api does exist", follow);
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.login.LoginApi#getVersion(CommunicationHandler)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetVersion() throws SteemCommunicationException, SteemResponseException {
        final SteemVersionInfo version = LoginApi.getVersion(COMMUNICATION_HANDLER);

        assertNotEquals("Expect non-empty blockchain version", "", version.getBlockchainVersion());
        assertNotEquals("Expect non-empty fc revision", "", version.getFcRevision());
        assertNotEquals("Expect non-empty steem revision", "", version.getSteemRevision());
    }
}

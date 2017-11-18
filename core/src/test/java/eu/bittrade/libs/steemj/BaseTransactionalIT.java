package eu.bittrade.libs.steemj;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.communication.HttpClientRequestInitializer;
import eu.bittrade.libs.steemj.enums.AddressPrefixType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class defines which tests should at least be performed for an operation
 * and prepares a transaction object so that it does not need to be created in
 * each sub test case.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTransactionalIT extends BaseIT {
    // Test settings can be set as -D Parameters in the argline of the failsave
    // plugin.
    protected static final String MODE_FIELD_NAME = "steemj.test.mode";
    protected static final String ENDPOINT_FIELD_NAME = "steemj.test.endpoint";
    protected static final String HTTP_MODE_IDENTIFIER = "http";
    protected static final String WEBSOCKET_MODE_IDENTIFIER = "websocket";
    protected static final String TESTNET_ENDPOINT_IDENTIFIER = "testnet";
    protected static final String STEEMNET_ENDPOINT_IDENTIFIER = "steem";
    // TestNet related constants:
    protected static final AccountName STEEMJ_ACCOUNT_NAME = new AccountName("steemj");
    protected static final AccountName DEZ_ACCOUNT_NAME = new AccountName("dez1337");
    protected static final String STEEMJ_PASSWORD = "P8N6sHEJu438dj4dwY9jx9c8deeKpPA6XWCr9aTC5SQ7MiCjMUm";
    protected static final String DEZ_PASSWORD = "P63u9CtiWtWMRU8k4m63Ahek7qRwPJpvwqAjNiQw7ZmDN1AQEen";
    // Allow to configure the mode and the endpoint as -D parameters during test
    // execution.
    protected static String TEST_MODE = System.getProperty(MODE_FIELD_NAME, null);
    protected static String TEST_ENDPOINT = System.getProperty(ENDPOINT_FIELD_NAME, null);

    protected static SignedTransaction signedTransaction;

    /**
     * Setup the test environment for transaction related tests.
     */
    protected static void setupIntegrationTestEnvironmentForTransactionalTests(String mode, String endpoint) {
        setupIntegrationTestEnvironment();

        if (TEST_ENDPOINT == null) {
            TEST_ENDPOINT = endpoint;
        }
        if (TEST_MODE == null) {
            TEST_MODE = mode;
        }

        // The expiration date used for tests is way to old in general -
        // Therefore the validation needs to be disabled.
        config.setValidationLevel(ValidationType.SKIP_VALIDATION);

        try {
            if (TEST_ENDPOINT.equals(TESTNET_ENDPOINT_IDENTIFIER)) {
                // Configure SteemJ to work against the TestNet.
                config.setChainId("79276aea5d4877d9a25892eaa01b0adf019d3e5cb12a97478df3298ccdd01673");
                config.setAddressPrefix(AddressPrefixType.STX);

                try {
                    if (TEST_MODE.equals(HTTP_MODE_IDENTIFIER)) {
                        configureTestNetHttpEndpoint();
                    } else if (TEST_MODE.equals(WEBSOCKET_MODE_IDENTIFIER)) {
                        configureTestNetWebsocketEndpoint();
                    } else {
                        LOGGER.error("Unknown Test Mode {}. - Test execution stopped.", TEST_MODE);
                    }
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Unable to start test due to a wrong endpoint URI.");
                }

                try {
                    createTestNetAccount(STEEMJ_ACCOUNT_NAME.getName(), STEEMJ_PASSWORD);
                    createTestNetAccount(DEZ_ACCOUNT_NAME.getName(), DEZ_PASSWORD);
                    // Fille the private key storage.
                    List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();

                    privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING, SteemJ
                            .getPrivateKeyFromPassword(STEEMJ_ACCOUNT_NAME, PrivateKeyType.POSTING, STEEMJ_PASSWORD)
                            .getRight()));
                    privateKeys.add(new ImmutablePair<>(PrivateKeyType.ACTIVE, SteemJ
                            .getPrivateKeyFromPassword(STEEMJ_ACCOUNT_NAME, PrivateKeyType.ACTIVE, STEEMJ_PASSWORD)
                            .getRight()));
                    privateKeys.add(new ImmutablePair<>(PrivateKeyType.OWNER,
                            SteemJ.getPrivateKeyFromPassword(STEEMJ_ACCOUNT_NAME, PrivateKeyType.OWNER, STEEMJ_PASSWORD)
                                    .getRight()));

                    config.getPrivateKeyStorage().addAccount(STEEMJ_ACCOUNT_NAME, privateKeys);

                    privateKeys = new ArrayList<>();

                    privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING,
                            SteemJ.getPrivateKeyFromPassword(DEZ_ACCOUNT_NAME, PrivateKeyType.POSTING, DEZ_PASSWORD)
                                    .getRight()));
                    privateKeys.add(new ImmutablePair<>(PrivateKeyType.ACTIVE,
                            SteemJ.getPrivateKeyFromPassword(DEZ_ACCOUNT_NAME, PrivateKeyType.ACTIVE, DEZ_PASSWORD)
                                    .getRight()));
                    privateKeys.add(new ImmutablePair<>(PrivateKeyType.OWNER,
                            SteemJ.getPrivateKeyFromPassword(DEZ_ACCOUNT_NAME, PrivateKeyType.OWNER, DEZ_PASSWORD)
                                    .getRight()));

                    config.getPrivateKeyStorage().addAccount(DEZ_ACCOUNT_NAME, privateKeys);
                } catch (IOException | GeneralSecurityException e) {
                    throw new RuntimeException("Could not create TestNet accounts. - Test execution stopped.", e);
                }
            } else if (TEST_ENDPOINT.equals(STEEMNET_ENDPOINT_IDENTIFIER)) {
                /*
                 * If running against the real Steem Blockchain an existing
                 * account with real private keys is required. This mode can
                 * currently only be used by @dez1337 as most of the integration
                 * tests are written for his account.
                 */
                try {
                    if (TEST_MODE.equals(HTTP_MODE_IDENTIFIER)) {
                        // Do nothing as this is the default.
                    } else if (TEST_MODE.equals(WEBSOCKET_MODE_IDENTIFIER)) {
                        configureSteemWebSocketEndpoint();
                    } else {
                        LOGGER.error("Unknown Test Mode {}. - Test execution stopped.", TEST_MODE);
                    }
                } catch (URISyntaxException e) {
                    throw new RuntimeException("Unable to start test due to a wrong endpoint URI.");
                }
            } else {
                LOGGER.error("Unknown Test Endpoint {}. - Test execution stopped.", TEST_ENDPOINT);
            }

            // Create a new instance to respect the settings made above.
            steemJ = new SteemJ();
        } catch (SteemCommunicationException | SteemResponseException e) {
            throw new RuntimeException("Could not create a SteemJ instance. - Test execution stopped.", e);
        }
    }

    /**
     * Create a new TestNet account as described in the TestNet main page
     * (https://testnet.steem.vc).
     * 
     * @param username
     *            The account to create.
     * @param password
     *            The password to set for the <code>username</code>.
     * @throws IOException
     *             In case something went wrong.
     * @throws GeneralSecurityException
     *             In case something went wrong.
     */
    private static void createTestNetAccount(String username, String password)
            throws IOException, GeneralSecurityException {
        NetHttpTransport.Builder builder = new NetHttpTransport.Builder();
        // Disable SSL verification:
        builder.doNotValidateCertificate();
        HttpRequest httpRequest = builder.build().createRequestFactory(new HttpClientRequestInitializer())
                .buildPostRequest(new GenericUrl("https://testnet.steem.vc/create"), ByteArrayContent.fromString(
                        "application/x-www-form-urlencoded", "username=" + username + "&password=" + password));
        try {
            httpRequest.execute();
        } catch (HttpResponseException e) {
            if (e.getStatusCode() != 409) {
                LOGGER.info("Account already existed.");
            }
        }
    }
}

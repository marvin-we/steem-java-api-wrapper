package eu.bittrade.libs.steemj;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.AddressPrefixType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * @author Anthony Martin
 */
public abstract class BaseIntegrationTest extends BaseTest {
    protected static final SteemJConfig CONFIG = SteemJConfig.getInstance();

    protected static SteemJ steemJ;

    /**
     * Prepare a the environment for standard integration tests.
     */
    protected static void setupIntegrationTestEnvironment() {
        try {
            CONFIG.setResponseTimeout(0);
            //CONFIG.setChainId("79276aea5d4877d9a25892eaa01b0adf019d3e5cb12a97478df3298ccdd01673");
            //CONFIG.setAddressPrefix(AddressPrefixType.STX);

            try {
                configureWebSocketEndpoint();
                //configureTestNetEndpoint();
            } catch (URISyntaxException e) {
                throw new RuntimeException("Unable to start test due to a wrong endpoint URI.");
            }

            steemJ = new SteemJ();
        } catch (SteemCommunicationException | SteemResponseException e) {
            LOGGER.error("Could not create a SteemJ instance. - Test execution stopped.", e);
        }
    }

    /**
     * Call this method in case the tests should be fired against a WebSocket
     * endpoint instead of using the default HTTPS endpoint.
     * 
     * @throws URISyntaxException
     *             If the URL is wrong.
     */
    public static void configureWebSocketEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("wss://steemd.steemit.com"), true);

        endpoints.add(webSocketEndpoint);
        CONFIG.setEndpointURIs(endpoints);
    }

    /**
     * Call this method in case the tests should be fired against the TestNet
     * endpoint instead of using the default HTTPS endpoint.
     * 
     * @throws URISyntaxException
     *             If the URL is wrong.
     */
    public static void configureTestNetEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("https://testnet.steem.vc"), true);

        endpoints.add(webSocketEndpoint);
        CONFIG.setEndpointURIs(endpoints);
    }
}

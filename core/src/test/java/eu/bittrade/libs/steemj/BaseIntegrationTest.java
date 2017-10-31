package eu.bittrade.libs.steemj;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

/**
 * @author Anthony Martin
 */
public abstract class BaseIntegrationTest extends BaseTest {
    protected static final SteemJConfig CONFIG = SteemJConfig.getInstance();

    protected static SteemJ steemJ;

    /**
     * Prepare a the environment for standard integration tests.
     * 
     * @throws URISyntaxException
     */
    protected static void setupIntegrationTestEnvironment() {
        try {
            CONFIG.setResponseTimeout(0);

            steemJ = new SteemJ();
        } catch (SteemCommunicationException | SteemResponseError e) {
            LOGGER.error("Could not create a SteemJ instance. - Test execution stopped.", e);
        }
    }

    /**
     * Call this method in case the tests should be fired against a WebSocket
     * endpoint instead of using the default HTTPS endpoint.
     * 
     * @throws Exception
     *             If the URL is wrong.
     */
    public void configureApiEndpoint() throws Exception {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("wss://steemd.steemit.com"), true);

        endpoints.add(webSocketEndpoint);
        CONFIG.setEndpointURIs(endpoints);
    }
}

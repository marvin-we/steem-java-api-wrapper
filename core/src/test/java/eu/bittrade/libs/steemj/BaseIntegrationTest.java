package eu.bittrade.libs.steemj;

import java.net.URI;
import java.net.URISyntaxException;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

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
            // Change the default settings if needed.
            CONFIG.setWebSocketEndpointURI(new URI("wss://seed.bitcoiner.me"));
            // Create a new apiWrapper with your config object.
            CONFIG.setResponseTimeout(0);
            CONFIG.setSslVerificationDisabled(true);

            steemJ = new SteemJ();
        } catch (SteemCommunicationException | URISyntaxException e) {
            LOGGER.error("Could not create a SteemJ instance. - Test execution stopped.", e);
        }
    }
}

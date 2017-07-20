package eu.bittrade.libs.steemj;

import java.net.URI;
import java.net.URISyntaxException;

import eu.bittrade.libs.steemj.SteemApiWrapper;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * @author Anthony Martin
 */
public abstract class BaseIntegrationTest extends BaseTest {
    protected static final SteemJConfig CONFIG = SteemJConfig.getInstance();

    protected static SteemApiWrapper steemApiWrapper;

    protected static void setupIntegrationTestEnvironment() {
        setupBasicTestEnvironment();

        try {
            // Change the default settings if needed.
            CONFIG.setWebsocketEndpointURI(new URI("wss://this.piston.rocks"));
            // Create a new apiWrapper with your config object.
            CONFIG.setTimeout(0);
            CONFIG.setSslVerificationDisabled(true);

            steemApiWrapper = new SteemApiWrapper();
        } catch (SteemCommunicationException | URISyntaxException e) {
            LOGGER.error("Could not create a SteemJ instance. - Test execution stopped.", e);
        }
    }
}

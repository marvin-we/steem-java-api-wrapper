package eu.bittrade.libs.steem.api.wrapper;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;

/**
 * @author Anthony Martin
 */
public abstract class BaseIntegrationTest extends BaseTest {
    private static final Logger LOGGER = LogManager.getLogger(BaseIntegrationTest.class);
    protected static final SteemApiWrapperConfig CONFIG = SteemApiWrapperConfig.getInstance();

    protected static SteemApiWrapper steemApiWrapper;
    
    @BeforeClass
    public static void setUpSteemApi() throws Exception  {
        setUp();
        // Change the default settings if needed.
        CONFIG.setWebsocketEndpointURI(new URI("wss://this.piston.rocks"));
        // Create a new apiWrapper with your config object.
        CONFIG.setTimeout(5000);
        CONFIG.setSslVerificationDisabled(true);
        steemApiWrapper = new SteemApiWrapper();
    }

    protected void debug(final String message) {
        LOGGER.info(message);
    }
}

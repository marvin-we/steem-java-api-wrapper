package eu.bittrade.libs.steem.api.wrapper;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;

/**
 * @author Anthony Martin
 */
public abstract class BaseTest {
    private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
    protected static final SteemApiWrapperConfig CONFIG = new SteemApiWrapperConfig();

    protected SteemApiWrapper steemApiWrapper;

    @Before
    public void setUp() throws Exception {
        // Change the default settings if needed.
        CONFIG.setWebsocketEndpointURI(new URI("wss://this.piston.rocks"));
        // Create a new apiWrapper with your config object.
        steemApiWrapper = new SteemApiWrapper(CONFIG);
    }

    protected void debug(final String message) {
        LOGGER.info(message);
    }
}

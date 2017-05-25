package eu.bittrade.libs.steem.api.wrapper;

import java.net.URI;

import org.junit.BeforeClass;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemJConfig;

/**
 * @author Anthony Martin
 */
public abstract class BaseIntegrationTest extends BaseTest {
    protected static final SteemJConfig CONFIG = SteemJConfig.getInstance();

    protected static SteemApiWrapper steemApiWrapper;

    @BeforeClass
    public static void setUpSteemApi() throws Exception {
        // Change the default settings if needed.
        CONFIG.setWebsocketEndpointURI(new URI("wss://this.piston.rocks"));
        // Create a new apiWrapper with your config object.
        CONFIG.setTimeout(5000);
        CONFIG.setSslVerificationDisabled(true);

        steemApiWrapper = new SteemApiWrapper();
    }
}

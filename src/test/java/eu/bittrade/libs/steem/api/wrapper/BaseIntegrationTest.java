package eu.bittrade.libs.steem.api.wrapper;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;

/**
 * @author Anthony Martin
 */
public abstract class BaseIntegrationTest {
    private static final Logger LOGGER = LogManager.getLogger(BaseIntegrationTest.class);
    protected static final SteemApiWrapperConfig CONFIG = SteemApiWrapperConfig.getInstance();

    protected static SteemApiWrapper steemApiWrapper;

    protected static final String WIF = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final int REF_BLOCK_NUM = 34294;
    protected static final long REF_BLOCK_PREFIX = 3707022213L;
    protected static final String EXPIRATION_DATE = "2016-04-06T08:29:27UTC";

    @BeforeClass
    public static void setUp() throws Exception {
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

package eu.bittrade.libs.steem.api.wrapper;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import java.net.URI;

public abstract class BaseTest {

  private static final Logger LOGGER = LogManager.getLogger(BaseTest.class);

  protected static final SteemApiWrapperConfig CONFIG = new SteemApiWrapperConfig();

  protected SteemApiWrapper steemApiWrapper;

  @Before
  public void setUp ( ) throws Exception {
    // Change the default settings if needed.
    CONFIG.setWebsocketEndpointURI(new URI("wss://steemit.com/wspa"));
    // Create a new apiWrapper with your config object.
    steemApiWrapper = new SteemApiWrapper(CONFIG);
  }


  protected void debug ( final String message ) { LOGGER.info(message); }

}

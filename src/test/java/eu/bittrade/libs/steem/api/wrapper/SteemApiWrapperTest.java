package eu.bittrade.libs.steem.api.wrapper;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemResponseError;
import eu.bittrade.libs.steem.api.wrapper.models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exparity.hamcrest.date.DateMatchers;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Month;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

public class SteemApiWrapperTest extends BaseTest {

  private static final Logger LOGGER = LogManager.getLogger(SteemApiWrapperTest.class);

  private static final String ACCOUNT = "dez1337";

  private static final String PERMLINK = "steem-api-wrapper-for-java-update1";

  @Test
  public void testAccountCount ( ) throws Exception {

    final int accountCount = steemApiWrapper.getAccountCount();

    assertThat("expect the number of accounts greater than 122908", accountCount, greaterThan(122908));

  }

  @Ignore("not fully implemented")
  @Test
  public void testAccountHistory ( ) throws Exception {

    final Map<Integer, AccountActivity> accountHistory = steemApiWrapper.getAccountHistory(ACCOUNT, 0, 10);

    // TODO write assertions

  }

  @Test
  public void testInvalidAccountVotes ( ) throws Exception {
    // Force an error response:
    try {
      steemApiWrapper.getAccountVotes("thisAcountDoesNotExistYet");
    } catch ( final SteemResponseError steemResponseError ) {
      // success
    } catch ( final Exception e ) {
      LOGGER.error(e);
      fail(e.toString());
    }

  }

  @Test
  public void testWitnessCount ( ) throws Exception {

    final int witnessCount = steemApiWrapper.getWitnessCount();

    assertThat("expect the number of witnesses greater than 13071", witnessCount, greaterThan(13071));

  }

  @Test
  public void testMinerQueue ( ) throws Exception {

    final String[] minerQueue = steemApiWrapper.getMinerQueue();

    assertThat("expect the number of miners greater than 0", minerQueue.length, greaterThan(0));

  }

  @Test
  public void testConfig ( ) throws Exception {

    final Config config = steemApiWrapper.getConfig();
    final boolean isTestNet = config.getIsTestNet();
    final String steemitNullAccount = config.getSteemitNullAccount();
    final String initMinerName = config.getSteemitInitMinerName();

    assertEquals("expect main net", false, isTestNet);
    assertEquals("expect the null account to be null", "null", steemitNullAccount);
    assertEquals("expect the init miner name to be initminer", "initminer", initMinerName);

  }

  @Ignore("not fully implemented")
  @Test
  public void testNodeInfo ( ) throws Exception {

    final NodeInfo nodeInfo = steemApiWrapper.getNodeInfo();

    // TODO write assertions

  }

  @Test
  public void testVersion ( ) throws Exception {

    final Version version = steemApiWrapper.getVersion();

    assertNotEquals("expect non-empty blockchain version", "", version.getBlockchainVersion());
    assertNotEquals("expect non-empty fc revision", "", version.getFcRevision());
    assertNotEquals("expect non-empty steem revision", "", version.getSteemRevision());

  }

  @Test
  public void testLogin ( ) throws Exception {

    final boolean success = steemApiWrapper.login("gilligan", "s.s.minnow");

    assertTrue("expect login to always return success: true", success);

  }

  @Test
  public void getApiByName ( ) throws Exception {

    final String bogus = steemApiWrapper.getApiByName("bogus_api");
    final String database = steemApiWrapper.getApiByName("database_api");
    final String login = steemApiWrapper.getApiByName("login_api");
    final String market_history = steemApiWrapper.getApiByName("market_history_api");
    final String follow = steemApiWrapper.getApiByName("follow_api");
    final String tags = steemApiWrapper.getApiByName("tags_api");
    final String network_node = steemApiWrapper.getApiByName("network_node_api");
    final String network_broadcast = steemApiWrapper.getApiByName("network_broadcast_api");
    final String chain_stats = steemApiWrapper.getApiByName("chain_stats_api");

    assertNull("expect that bogus api does not exist", bogus);
    assertNotNull("expect that database api does exist", database);
    assertNotNull("expect that login api does exist", login);
    assertNotNull("expect that market_history api does exist", market_history);
    assertNotNull("expect that follow api does exist", follow);
//    assertNotNull("expect that tags api does exist", tags);
//    assertNotNull("expect that network_node api does exist", network_node);
//    assertNotNull("expect that network_broadcast api does exist", network_broadcast);
//    assertNotNull("expect that chain_stats api does exist", chain_stats);

  }

  @Test
  public void testTrendingTags ( ) throws Exception {

    final List<TrendingTag> trendingTags = steemApiWrapper.getTrendingTags(null, 10);

    assertNotNull("expect trending tags", trendingTags);
    assertThat("expect trending tags size > 0", trendingTags.size(), greaterThan(0));

  }

  @Test
  public void testHardforkVersion ( ) throws Exception {

    final String hardforkVersion = steemApiWrapper.getHardforkVersion();

    assertNotNull("expect hardfork version", hardforkVersion);

  }

  @Test
  public void testWitnessSchedule ( ) throws Exception {

    final WitnessSchedule witnessSchedule = steemApiWrapper.getWitnessSchedule();

    assertNotNull("expect hardfork version", witnessSchedule);

  }

  @Test
  public void testLookupAccount ( ) throws Exception {

    final List<String> accounts = steemApiWrapper.lookupAccounts(ACCOUNT, 10);

    assertNotNull("expect accounts", accounts);
    assertThat("expect at least one account", accounts.size(), greaterThan(0));

  }

  @Test
  public void testLookupWitnessAccount ( ) throws Exception {

    final List<String> accounts = steemApiWrapper.lookupWitnessAccounts("gtg", 10);

    assertNotNull("expect accounts", accounts);
    assertThat("expect at least one account", accounts.size(), greaterThan(0));

  }

  @Test
  public void testGetDynamicGlobalProperties ( ) throws Exception {

    final GlobalProperties properties = steemApiWrapper.getDynamicGlobalProperties();

    assertNotNull("expect properties", properties);
    assertThat("expect head block number", properties.getHeadBlockNumber(), greaterThan(6000000L));

  }

  @Test
  public void testGetChainProperties ( ) throws Exception {

    final ChainProperties properties = steemApiWrapper.getChainProperties();

    assertNotNull("expect properties", properties);
    assertThat("expect head block number", properties.getSdbInterestRate(), greaterThan(0));

  }

  @Test
  public void testCurrentMedianHistoryPrice ( ) throws Exception {

    final String base = steemApiWrapper.getCurrentMedianHistoryPrice().getBase();
    final double base_value = Double.parseDouble(base.substring(0, base.indexOf(' ')));
    final String symbol = base.substring(base.indexOf(' ') + 1, base.length());

    assertThat("expect current median price greater than zero", base_value, greaterThan(0.00));
    assertEquals("expect current median price symbol", "SBD", symbol);

  }

  @Test
  public void testGetContent ( ) throws Exception {

    final Discussion discussion = steemApiWrapper.getContent(ACCOUNT, PERMLINK);

    assertNotNull("expect discussion", discussion);
    assertEquals("expect correct author", ACCOUNT, discussion.getAuthor());

  }

  @Test
  public void testGetContentReplies ( ) throws Exception {

    final List<Discussion> replies = steemApiWrapper.getContentReplies(ACCOUNT, PERMLINK);

    assertNotNull("expect replies", replies);
    assertThat("expect replies greater than zero", replies.size(), greaterThan(0));

  }

  @Test
  public void testActiveVotes ( ) throws Exception {

    // Get the votes done by the specified account:
    final List<Vote> votes = steemApiWrapper.getAccountVotes(ACCOUNT);
    final List<ActiveVote> activeVotesForArticle = steemApiWrapper.getActiveVotes(ACCOUNT, PERMLINK);

    assertNotNull("expect votes", votes);
    assertThat("expect account has votes", votes.size(), greaterThan(0));
    assertThat("expect last vote after 2016-03-01", votes.get(votes.size() - 1).getTime(), DateMatchers.after(2016, Month.MARCH, 1));

    boolean foundSelfVote = false;

    for ( final ActiveVote vote : activeVotesForArticle ) {
      if ( ACCOUNT.equals(vote.getVoter()) ) {
        foundSelfVote = true;
        break;
      }
    }

    assertTrue("expect self vote for article of account", foundSelfVote);

  }

  @Test
  public void testGetDiscussionByActive ( ) throws Exception {

    final List<Discussion> discussions = steemApiWrapper.getDiscussionsByActive("steemit", 1);

    assertNotNull("expect discussions", discussions);
    assertThat("expect discussions greater than zero", discussions.size(), greaterThan(0));

  }

  @Ignore("not fully implemented")
  @Test
  public void testBroadcastTransactionSynchronous ( ) throws Exception {

    final boolean success = steemApiWrapper.broadcastTransactionSynchronous("TODO");

    assertTrue("expect broadcast success: true", success);

  }

}

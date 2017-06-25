package eu.bittrade.libs.steemj;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.exparity.hamcrest.date.DateMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.base.models.AccountActivity;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.ActiveVote;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.Config;
import eu.bittrade.libs.steemj.base.models.Discussion;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.GlobalProperties;
import eu.bittrade.libs.steemj.base.models.HardforkSchedule;
import eu.bittrade.libs.steemj.base.models.LiquidityQueueEntry;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.base.models.SignedBlockWithInfo;
import eu.bittrade.libs.steemj.base.models.TrendingTag;
import eu.bittrade.libs.steemj.base.models.Version;
import eu.bittrade.libs.steemj.base.models.Vote;
import eu.bittrade.libs.steemj.base.models.Witness;
import eu.bittrade.libs.steemj.base.models.WitnessSchedule;
import eu.bittrade.libs.steemj.base.models.operations.AccountCreateOperation;
import eu.bittrade.libs.steemj.base.models.operations.AccountCreateWithDelegationOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;
import eu.bittrade.libs.steemj.plugins.follow.enums.FollowType;
import eu.bittrade.libs.steemj.plugins.follow.model.AccountReputation;
import eu.bittrade.libs.steemj.plugins.follow.model.BlogEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.CommentBlogEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.CommentFeedEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.FeedEntry;
import eu.bittrade.libs.steemj.plugins.follow.model.FollowApiObject;
import eu.bittrade.libs.steemj.plugins.follow.model.FollowCountApiObject;
import eu.bittrade.libs.steemj.plugins.follow.model.PostsPerAuthorPair;

/**
 * @author Anthony Martin
 */
public class SteemApiWrapperIT extends BaseIntegrationTest {
    private static final Logger LOGGER = LogManager.getLogger(SteemApiWrapperIT.class);
    private static final String ACCOUNT = "dez1337";
    private static final String ACCOUNT_TWO = "randowhale";
    private static final String WITNESS_ACCOUNT = "riverhead";
    private static final String PERMLINK = "steem-api-wrapper-for-java-update1";

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetNextScheduledHarfork() throws Exception {
        final HardforkSchedule hardforkSchedule = steemApiWrapper.getNextScheduledHarfork();

        assertTrue(hardforkSchedule.getHardforkVersion().matches("[0-9\\.]+"));
        assertTrue(hardforkSchedule.getLiveTime().matches("[0-9\\-:T]+"));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlock() throws Exception {
        final SignedBlockWithInfo signedBlockWithInfo = steemApiWrapper.getBlock(12347123L);

        assertThat(signedBlockWithInfo.getTimestamp(), equalTo(0L));
        assertThat(signedBlockWithInfo.getWitness(), equalTo("abit"));

        final SignedBlockWithInfo signedBlockWithInfoWithExtension = steemApiWrapper.getBlock(12615532L);

        assertThat(signedBlockWithInfoWithExtension.getTimestamp(), equalTo(0L));
        assertThat(signedBlockWithInfoWithExtension.getWitness(), equalTo("dragosroua"));
        assertThat(signedBlockWithInfoWithExtension.getExtensions().get(0).getHardforkVersionVote().getHfVersion(), equalTo("0.19.0"));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testAccountCount() throws Exception {
        final int accountCount = steemApiWrapper.getAccountCount();

        assertThat("expect the number of accounts greater than 122908", accountCount, greaterThan(122908));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testAccountHistory() throws Exception {
        final Map<Integer, AccountActivity> accountHistorySetOne = steemApiWrapper.getAccountHistory(ACCOUNT, 10, 10);
        assertEquals("expect response to contain 10 results", 11, accountHistorySetOne.size());

        Operation firstOperation = accountHistorySetOne.get(0).getOperations();
        assertTrue("the first operation for each account is the 'account_create_operation'",
                firstOperation instanceof AccountCreateOperation);

        final Map<Integer, AccountActivity> accountHistorySetTwo = steemApiWrapper.getAccountHistory(ACCOUNT_TWO, 1000,
                1000);
        assertEquals("expect response to contain 1001 results", 1001, accountHistorySetTwo.size());

        assertThat(accountHistorySetTwo.get(0).getOperations(), instanceOf(AccountCreateWithDelegationOperation.class));
        assertThat(((AccountCreateWithDelegationOperation) accountHistorySetTwo.get(0).getOperations()).getCreator()
                .getAccountName(), equalTo(new AccountName("anonsteem").getAccountName()));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testActiveVotes() throws Exception {
        // Get the votes done by the specified account:
        final List<Vote> votes = steemApiWrapper.getAccountVotes(ACCOUNT);
        final List<ActiveVote> activeVotesForArticle = steemApiWrapper.getActiveVotes(ACCOUNT, PERMLINK);

        assertNotNull("expect votes", votes);
        assertThat("expect account has votes", votes.size(), greaterThan(0));
        assertThat("expect last vote after 2016-03-01", votes.get(votes.size() - 1).getTime(),
                DateMatchers.after(2016, Month.MARCH, 1));

        boolean foundSelfVote = false;

        for (final ActiveVote vote : activeVotesForArticle) {
            if (ACCOUNT.equals(vote.getVoter())) {
                foundSelfVote = true;
                break;
            }
        }

        assertTrue("expect self vote for article of account", foundSelfVote);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetConfig() throws Exception {
        final Config config = steemApiWrapper.getConfig();
        final boolean isTestNet = config.getIsTestNet();
        final String steemitNullAccount = config.getSteemitNullAccount();
        final String initMinerName = config.getSteemitInitMinerName();

        assertEquals("expect main net", false, isTestNet);
        assertEquals("expect the null account to be null", "null", steemitNullAccount);
        assertEquals("expect the init miner name to be initminer", "initminer", initMinerName);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testCurrentMedianHistoryPrice() throws Exception {
        final Asset base = steemApiWrapper.getCurrentMedianHistoryPrice().getBase();
        final Asset quote = steemApiWrapper.getCurrentMedianHistoryPrice().getQuote();

        assertThat("expect current median price greater than zero", base.getAmount(), greaterThan(0.00));
        assertEquals("expect current median price symbol", AssetSymbolType.SBD, base.getSymbol());
        assertThat("expect current median price greater than zero", quote.getAmount(), greaterThan(0.00));
        assertEquals("expect current median price symbol", AssetSymbolType.STEEM, quote.getSymbol());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccounts() throws Exception {
        final List<AccountName> accountNames = new ArrayList<>();
        accountNames.add(new AccountName("dez1337"));
        accountNames.add(new AccountName("inertia"));

        List<ExtendedAccount> accounts = steemApiWrapper.getAccounts(accountNames);

        assertThat("Expect that two results are returned.", accounts, hasSize(2));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetActiveWitnesses() throws Exception {
        final String[] activeWitnesses = steemApiWrapper.getActiveWitnesses();

        // The active witness changes from time to time, so we just check if
        // something is returned.
        assertThat(activeWitnesses.length, greaterThan(0));
        assertThat(activeWitnesses[0], not(isEmptyOrNullString()));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetApiByName() throws Exception {
        final String bogus = steemApiWrapper.getApiByName("bogus_api");
        final String database = steemApiWrapper.getApiByName("database_api");
        final String login = steemApiWrapper.getApiByName("login_api");
        final String market_history = steemApiWrapper.getApiByName("market_history_api");
        final String follow = steemApiWrapper.getApiByName("follow_api");

        assertNull("expect that bogus api does not exist", bogus);
        assertNotNull("expect that database api does exist", database);
        assertNotNull("expect that login api does exist", login);
        assertNotNull("expect that market_history api does exist", market_history);
        assertNotNull("expect that follow api does exist", follow);
    }

    @Category(IntegrationTest.class)
    @Test
    public void testGetApiByNameForSecuredApi() throws Exception {
        final String database = steemApiWrapper.getApiByName("database_api");
        final String networkBroadcast = steemApiWrapper.getApiByName("network_broadcast_api");
        final String login = steemApiWrapper.getApiByName("login_api");

        assertNotNull("expect that network_node api does exist", database);
        assertNotNull("expect that network_broadcast api does exist", networkBroadcast);
        assertNotNull("expect that chain_stats api does exist", login);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetChainProperties() throws Exception {
        final ChainProperties properties = steemApiWrapper.getChainProperties();

        assertNotNull("expect properties", properties);
        assertThat("expect sbd interest rate", properties.getSdbInterestRate(), greaterThanOrEqualTo(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetContent() throws Exception {
        final Discussion discussion = steemApiWrapper.getContent(ACCOUNT, PERMLINK);

        assertNotNull("expect discussion", discussion);
        assertEquals("expect correct author", ACCOUNT, discussion.getAuthor().getAccountName());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetContentReplies() throws Exception {
        final List<Discussion> replies = steemApiWrapper.getContentReplies(ACCOUNT, PERMLINK);

        assertNotNull("expect replies", replies);
        assertThat("expect replies greater than zero", replies.size(), greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetDiscussionBy() throws Exception {

        final DiscussionSortType[] sortTypes = new DiscussionSortType[] { DiscussionSortType.SORT_BY_TRENDING,
                DiscussionSortType.SORT_BY_CREATED, DiscussionSortType.SORT_BY_ACTIVE,
                DiscussionSortType.SORT_BY_CASHOUT, DiscussionSortType.SORT_BY_VOTES,
                DiscussionSortType.SORT_BY_CHILDREN, DiscussionSortType.SORT_BY_HOT, DiscussionSortType.SORT_BY_BLOG,
                DiscussionSortType.SORT_BY_PROMOTED, DiscussionSortType.SORT_BY_PAYOUT,
                DiscussionSortType.SORT_BY_FEED };

        for (final DiscussionSortType type : sortTypes) {
            final List<Discussion> discussions = steemApiWrapper.getDiscussionsBy("steemit", 1, type);
            assertNotNull("expect discussions", discussions);
            assertThat("expect discussions in " + type + " greater than zero", discussions.size(),
                    greaterThanOrEqualTo(0));
        }
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetDiscussionsByAuthorBeforeDate() throws Exception {
        final List<Discussion> repliesByLastUpdate = steemApiWrapper.getDiscussionsByAuthorBeforeDate(ACCOUNT, PERMLINK,
                "2017-02-10T22:00:06", 8);

        assertEquals("expect that 8 results are returned", repliesByLastUpdate.size(), 8);
        assertEquals("expect " + ACCOUNT + " to be the first returned author",
                repliesByLastUpdate.get(0).getAuthor().getAccountName(), ACCOUNT);
        assertEquals("expect " + PERMLINK + " to be the first returned permlink", PERMLINK,
                repliesByLastUpdate.get(0).getPermlink());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetDynamicGlobalProperties() throws Exception {
        final GlobalProperties properties = steemApiWrapper.getDynamicGlobalProperties();

        assertNotNull("expect properties", properties);
        assertThat("expect head block number", properties.getHeadBlockNumber(), greaterThan(6000000));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetLiquidityQueue() throws Exception {
        final List<LiquidityQueueEntry> repliesByLastUpdate = steemApiWrapper.getLiquidityQueue(WITNESS_ACCOUNT, 5);

        assertEquals("expect that 5 results are returned", repliesByLastUpdate.size(), 5);
        assertEquals("expect " + WITNESS_ACCOUNT + " to be the first returned account", WITNESS_ACCOUNT,
                repliesByLastUpdate.get(0).getAccount());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetRepliesByLastUpdate() throws Exception {
        final List<Discussion> repliesByLastUpdate = steemApiWrapper.getRepliesByLastUpdate(ACCOUNT, PERMLINK, 9);

        assertEquals("expect that 9 results are returned", repliesByLastUpdate.size(), 9);
        assertEquals("expect " + ACCOUNT + " to be the first returned author", ACCOUNT,
                repliesByLastUpdate.get(0).getAuthor().getAccountName());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetWitnessByAccount() throws Exception {
        final Witness activeWitnessesByVote = steemApiWrapper.getWitnessByAccount(WITNESS_ACCOUNT);

        assertEquals("expect " + WITNESS_ACCOUNT + " to be the owner of the returned witness account", WITNESS_ACCOUNT,
                activeWitnessesByVote.getOwner());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetWitnessesByVote() throws Exception {
        final List<Witness> activeWitnessesByVote = steemApiWrapper.getWitnessByVote(WITNESS_ACCOUNT, 10);

        assertEquals("expect that 10 results are returned", activeWitnessesByVote.size(), 10);
        assertEquals("expect " + WITNESS_ACCOUNT + " to be the first returned witness", WITNESS_ACCOUNT,
                activeWitnessesByVote.get(0).getOwner());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testHardforkVersion() throws Exception {
        final String hardforkVersion = steemApiWrapper.getHardforkVersion();

        assertNotNull("Expect hardfork version to be present.", hardforkVersion);
        assertTrue(hardforkVersion.matches("[0-9]+[\\.]{1}[0-9]{2}[\\.]{1}[0-9]+"));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testInvalidAccountVotes() throws Exception {
        // Force an error response:
        try {
            steemApiWrapper.getAccountVotes("thisAcountDoesNotExistYet");
        } catch (final SteemResponseError steemResponseError) {
            // success
        } catch (final Exception e) {
            LOGGER.error(e);
            fail(e.toString());
        }
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testLogin() throws Exception {
        final boolean success = steemApiWrapper.login("gilligan", "s.s.minnow");

        assertTrue("expect login to always return success: true", success);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testLookupAccount() throws Exception {
        final List<String> accounts = steemApiWrapper.lookupAccounts(ACCOUNT, 10);

        assertNotNull("expect accounts", accounts);
        assertThat("expect at least one account", accounts.size(), greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testLookupWitnessAccount() throws Exception {
        final List<String> accounts = steemApiWrapper.lookupWitnessAccounts("gtg", 10);

        assertNotNull("expect accounts", accounts);
        assertThat("expect at least one account", accounts.size(), greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testMinerQueue() throws Exception {
        final String[] minerQueue = steemApiWrapper.getMinerQueue();

        assertThat("expect the number of miners greater than 0", minerQueue.length, greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testTrendingTags() throws Exception {
        final List<TrendingTag> trendingTags = steemApiWrapper.getTrendingTags(null, 10);

        assertNotNull("expect trending tags", trendingTags);
        assertThat("expect trending tags size > 0", trendingTags.size(), greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetRewardFund() throws Exception {
        final RewardFund rewardFund = steemApiWrapper.getRewardFund(RewardFundType.POST);

        // Verify that all fields a used:
        assertThat(rewardFund.getContentConstant(), notNullValue());
        assertThat(rewardFund.getId(), notNullValue());
        assertThat(rewardFund.getLastUpdate(), notNullValue());
        assertThat(rewardFund.getName(), notNullValue());
        assertThat(rewardFund.getPercentContentRewards(), notNullValue());
        assertThat(rewardFund.getPercentCurationRewards(), notNullValue());
        assertThat(rewardFund.getRecentClaims(), notNullValue());
        assertThat(rewardFund.getRewardBalance(), notNullValue());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testVersion() throws Exception {
        final Version version = steemApiWrapper.getVersion();

        assertNotEquals("expect non-empty blockchain version", "", version.getBlockchainVersion());
        assertNotEquals("expect non-empty fc revision", "", version.getFcRevision());
        assertNotEquals("expect non-empty steem revision", "", version.getSteemRevision());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testWitnessCount() throws Exception {
        final int witnessCount = steemApiWrapper.getWitnessCount();

        assertThat("expect the number of witnesses greater than 13071", witnessCount, greaterThan(13071));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testWitnessSchedule() throws Exception {
        final WitnessSchedule witnessSchedule = steemApiWrapper.getWitnessSchedule();

        assertNotNull("expect hardfork version", witnessSchedule);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetFollowers() throws Exception {
        final List<FollowApiObject> followers = steemApiWrapper.getFollowers(new AccountName("dez1337"),
                new AccountName("dez1337"), FollowType.BLOG, (short) 100);

        assertThat(followers.size(), equalTo(100));
        assertThat(followers.get(0).getFollower(), equalTo(new AccountName("dhwoodland")));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetFollowing() throws Exception {
        final List<FollowApiObject> following = steemApiWrapper.getFollowing(new AccountName("dez1337"),
                new AccountName("dez1337"), FollowType.BLOG, (short) 10);

        assertThat(following.size(), equalTo(10));
        assertThat(following.get(0).getFollowing(), equalTo(new AccountName("furion")));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetFollowCount() throws Exception {
        final FollowCountApiObject followCount = steemApiWrapper.getFollowCount(new AccountName("dez1337"));

        assertThat(followCount.getFollowerCount(), greaterThan(10));
        assertThat(followCount.getFollowingCount(), greaterThan(10));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetFeedEntries() throws Exception {
        final List<FeedEntry> feedEntries = steemApiWrapper.getFeedEntries(new AccountName("dez1337"), 0, (short) 100);

        assertThat(feedEntries.size(), equalTo(100));
        assertTrue(feedEntries.get(0).getPermlink().matches("[a-z0-9\\-]+"));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetFeed() throws Exception {
        final List<CommentFeedEntry> feed = steemApiWrapper.getFeed(new AccountName("dez1337"), 0, (short) 100);

        assertThat(feed.size(), equalTo(100));
        assertTrue(feed.get(0).getComment().getAuthor().getAccountName().matches("[a-z]+"));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlogEntries() throws Exception {
        final List<BlogEntry> blogEntries = steemApiWrapper.getBlogEntries(new AccountName("dez1337"), 0, (short) 10);

        assertThat(blogEntries.size(), equalTo(10));
        assertThat(blogEntries.get(0).getBlog(), equalTo(new AccountName("dez1337")));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlog() throws Exception {
        final List<CommentBlogEntry> blog = steemApiWrapper.getBlog(new AccountName("dez1337"), 0, (short) 10);

        assertThat(blog.size(), equalTo(10));
        assertThat(blog.get(0).getBlog(), equalTo(new AccountName("dez1337")));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccountReputation() throws Exception {
        final List<AccountReputation> accountReputations = steemApiWrapper
                .getAccountReputations(new AccountName("dez1337"), 10);

        assertThat(accountReputations.size(), equalTo(10));
        assertThat(accountReputations.get(0).getReputation(), greaterThan(14251747809260L));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetRebloggedBy() throws Exception {
        final List<AccountName> accountNames = steemApiWrapper.getRebloggedBy(new AccountName("dez1337"),
                "steemj-v0-2-6-has-been-released-update-11");

        assertThat(accountNames.size(), greaterThan(2));
        assertThat(accountNames.get(1), equalTo(new AccountName("jesuscirino")));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testBlogAuthors() throws Exception {
        final List<PostsPerAuthorPair> blogAuthors = steemApiWrapper.getBlogAuthors(new AccountName("dez1337"));

        assertThat(blogAuthors.size(), greaterThan(2));
        assertThat(blogAuthors.get(1).getAccount(), equalTo(new AccountName("good-karma")));
    }
}

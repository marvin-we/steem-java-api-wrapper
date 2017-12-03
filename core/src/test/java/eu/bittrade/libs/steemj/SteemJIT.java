package eu.bittrade.libs.steemj;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.apis.database.models.state.Discussion;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AccountVote;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.Config;
import eu.bittrade.libs.steemj.base.models.DiscussionQuery;
import eu.bittrade.libs.steemj.base.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.ExtendedLimitOrder;
import eu.bittrade.libs.steemj.base.models.FeedHistory;
import eu.bittrade.libs.steemj.base.models.LiquidityBalance;
import eu.bittrade.libs.steemj.base.models.OrderBook;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.RewardFund;
import eu.bittrade.libs.steemj.base.models.ScheduledHardfork;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.base.models.VoteState;
import eu.bittrade.libs.steemj.base.models.Witness;
import eu.bittrade.libs.steemj.base.models.WitnessSchedule;
import eu.bittrade.libs.steemj.base.models.operations.AccountCreateOperation;
import eu.bittrade.libs.steemj.base.models.operations.AccountCreateWithDelegationOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.TransferOperation;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.util.KeyGenerator;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * @author Anthony Martin
 */
public class SteemJIT extends BaseIT {
    private static final Logger LOGGER = LogManager.getLogger(SteemJIT.class);
    private static final AccountName ACCOUNT = new AccountName("dez1337");
    private static final AccountName ACCOUNT_TWO = new AccountName("randowhale");
    private static final AccountName WITNESS_ACCOUNT = new AccountName("riverhead");
    private static final Permlink PERMLINK = new Permlink("steem-api-wrapper-for-java-update1");

    /**
     * Prepare the test environment.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    /**
     * Test the {@link SteemJ#getNextScheduledHarfork()} method.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetNextScheduledHarfork() throws Exception {
        final ScheduledHardfork hardforkSchedule = steemJ.getNextScheduledHarfork();

        assertTrue(hardforkSchedule.getHardforkVersion().toString().matches("[0-9\\.]+"));
        assertThat(hardforkSchedule.getLiveTime().getDateTimeAsTimestamp(), greaterThan(1497970799L));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccountCount() throws Exception {
        final int accountCount = steemJ.getAccountCount();

        assertThat("expect the number of accounts greater than 122908", accountCount, greaterThan(122908));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccountHistory() throws Exception {
        final Map<Integer, AppliedOperation> accountHistorySetOne = steemJ.getAccountHistory(ACCOUNT, 10, 10);
        assertEquals("expect response to contain 10 results", 11, accountHistorySetOne.size());

        Operation firstOperation = accountHistorySetOne.get(0).getOp();
        assertTrue("the first operation for each account is the 'account_create_operation'",
                firstOperation instanceof AccountCreateOperation);

        final Map<Integer, AppliedOperation> accountHistorySetTwo = steemJ.getAccountHistory(ACCOUNT_TWO, 1000, 1000);
        assertEquals("expect response to contain 1001 results", 1001, accountHistorySetTwo.size());

        assertThat(accountHistorySetTwo.get(0).getOp(), instanceOf(AccountCreateWithDelegationOperation.class));
        assertThat(((AccountCreateWithDelegationOperation) accountHistorySetTwo.get(0).getOp()).getCreator().getName(),
                equalTo(new AccountName("anonsteem").getName()));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetActiveVotes() throws Exception {
        // Get the votes done by the specified account:
        final List<AccountVote> votes = steemJ.getAccountVotes(ACCOUNT);
        final List<VoteState> activeVotesForArticle = steemJ.getActiveVotes(ACCOUNT, PERMLINK);

        assertNotNull("expect votes", votes);
        assertThat("expect account has votes", votes.size(), greaterThan(0));
        assertThat("expect last vote after 2016-03-01", votes.get(votes.size() - 1).getTime().getDateTimeAsTimestamp(),
                greaterThan(1456790400L));

        boolean foundSelfVote = false;

        for (final VoteState vote : activeVotesForArticle) {
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
        final Config config = steemJ.getConfig();
        final boolean isTestNet = config.getIsTestNet();
        final String steemitNullAccount = config.getSteemitNullAccount();
        final String initMinerName = config.getSteemitInitMinerName();

        assertEquals("expect main net", false, isTestNet);
        assertEquals("expect the null account to be null", "null", steemitNullAccount);
        assertEquals("expect the init miner name to be initminer", "initminer", initMinerName);
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetCurrentMedianHistoryPrice() throws Exception {
        final Asset base = steemJ.getCurrentMedianHistoryPrice().getBase();
        final Asset quote = steemJ.getCurrentMedianHistoryPrice().getQuote();

        assertThat("expect current median price greater than zero", base.getAmount(), greaterThan(0L));
        assertEquals("expect current median price symbol", AssetSymbolType.SBD, base.getSymbol());
        assertThat("expect current median price greater than zero", quote.getAmount(), greaterThan(0L));
        assertEquals("expect current median price symbol", AssetSymbolType.STEEM, quote.getSymbol());
    }

    /**
     * Test the {@link eu.bittrade.libs.steemj.SteemJ#getAccounts(List)
     * getAccounts(List)} method.
     * 
     * @throws Exception
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccounts() throws Exception {
        final List<AccountName> accountNames = new ArrayList<>();

        accountNames.add(new AccountName("dez1337"));
        accountNames.add(new AccountName("inertia"));
        accountNames.add(new AccountName("baabeetaa"));

        List<ExtendedAccount> accounts = steemJ.getAccounts(accountNames);

        assertThat("Expect that two results are returned.", accounts, hasSize(3));
        assertThat(accounts.get(2).getName(), equalTo(new AccountName("baabeetaa")));
        assertThat(accounts.get(0).getMemoKey().getAddressFromPublicKey(),
                equalTo("STM5qu8gRh39y5AvY3kciA5P4CkRZEfSYbSo5xQKoZsZdDVsyn6fm"));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetChainProperties() throws Exception {
        final ChainProperties properties = steemJ.getChainProperties();

        assertNotNull("expect properties", properties);
        assertThat("expect sbd interest rate", properties.getSdbInterestRate(), greaterThanOrEqualTo(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetContent() throws Exception {
        final Discussion discussion = steemJ.getContent(ACCOUNT, PERMLINK);

        assertNotNull("expect discussion", discussion);
        assertEquals("expect correct author", ACCOUNT, discussion.getAuthor());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetContentReplies() throws Exception {
        final List<Discussion> replies = steemJ.getContentReplies(ACCOUNT, PERMLINK);

        assertNotNull("expect replies", replies);
        assertThat("expect replies greater than zero", replies.size(), greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetDiscussionBy() throws Exception {
        final DiscussionQuery discussionQuery = new DiscussionQuery();
        discussionQuery.setLimit(1);
        discussionQuery.setTag("steemit");
        discussionQuery.setStartAuthor(new AccountName("steemitblog"));

        for (final DiscussionSortType type : DiscussionSortType.values()) {
            // Some methods require other parameters, so skip them here and
            // check them afterwards.
            if (type.equals(DiscussionSortType.GET_DISCUSSIONS_BY_FEED)
                    || type.equals(DiscussionSortType.GET_DISCUSSIONS_BY_BLOG)) {
                continue;
            }
            final List<Discussion> discussions = steemJ.getDiscussionsBy(discussionQuery, type);
            assertNotNull("expect discussions", discussions);
            assertThat("expect discussions in " + type + " greater than zero", discussions.size(),
                    greaterThanOrEqualTo(0));
        }

        // TODO: Clean this up..
        discussionQuery.setStartAuthor(null);

        final List<Discussion> discussions = steemJ.getDiscussionsBy(discussionQuery,
                DiscussionSortType.GET_DISCUSSIONS_BY_FEED);
        assertNotNull("expect discussions", discussions);
        assertThat("expect discussions in " + DiscussionSortType.GET_DISCUSSIONS_BY_FEED + " greater than zero",
                discussions.size(), greaterThanOrEqualTo(0));

        final List<Discussion> discussions2 = steemJ.getDiscussionsBy(discussionQuery,
                DiscussionSortType.GET_DISCUSSIONS_BY_BLOG);
        assertNotNull("expect discussions", discussions2);
        assertThat("expect discussions in " + DiscussionSortType.GET_DISCUSSIONS_BY_FEED + " greater than zero",
                discussions2.size(), greaterThanOrEqualTo(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetDiscussionsByAuthorBeforeDate() throws Exception {
        final List<Discussion> repliesByLastUpdate = steemJ.getDiscussionsByAuthorBeforeDate(ACCOUNT, PERMLINK,
                "2017-02-10T22:00:06", 8);

        assertEquals("expect that 8 results are returned", repliesByLastUpdate.size(), 8);
        assertEquals("expect " + ACCOUNT + " to be the first returned author", repliesByLastUpdate.get(0).getAuthor(),
                ACCOUNT);
        assertEquals("expect " + PERMLINK + " to be the first returned permlink", PERMLINK,
                repliesByLastUpdate.get(0).getPermlink());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetDynamicGlobalProperties() throws Exception {
        final DynamicGlobalProperty properties = steemJ.getDynamicGlobalProperties();

        assertNotNull("expect properties", properties);
        assertThat("expect head block number", properties.getHeadBlockNumber(), greaterThan(6000000L));
        assertTrue(properties.getHeadBlockId().toString().matches("[0-9a-f]{40}"));
        assertThat(properties.getHeadBlockId().getHashValue().longValue(), greaterThan(123L));
        assertThat(properties.getHeadBlockId().getNumberFromHash(), greaterThan(123));
        assertThat(properties.getTotalPow(), greaterThan(new BigInteger("123")));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetLiquidityQueue() throws Exception {
        final List<LiquidityBalance> repliesByLastUpdate = steemJ.getLiquidityQueue(WITNESS_ACCOUNT, 5);

        assertEquals("expect that 5 results are returned", repliesByLastUpdate.size(), 5);
        assertEquals("expect " + WITNESS_ACCOUNT + " to be the first returned account", WITNESS_ACCOUNT,
                repliesByLastUpdate.get(0).getAccount());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetRepliesByLastUpdate() throws Exception {
        final List<Discussion> repliesByLastUpdate = steemJ.getRepliesByLastUpdate(ACCOUNT, PERMLINK, 9);

        assertEquals("expect that 9 results are returned", repliesByLastUpdate.size(), 9);
        assertEquals("expect " + ACCOUNT + " to be the first returned author", ACCOUNT,
                repliesByLastUpdate.get(0).getAuthor());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetWitnessByAccount() throws Exception {
        final Witness activeWitnessesByVote = steemJ.getWitnessByAccount(WITNESS_ACCOUNT);

        assertEquals("expect " + WITNESS_ACCOUNT + " to be the owner of the returned witness account", WITNESS_ACCOUNT,
                activeWitnessesByVote.getOwner());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetWitnessesByVote() throws Exception {
        final List<Witness> activeWitnessesByVote = steemJ.getWitnessByVote(WITNESS_ACCOUNT, 10);

        assertEquals("expect that 10 results are returned", activeWitnessesByVote.size(), 10);
        assertEquals("expect " + WITNESS_ACCOUNT + " to be the first returned witness", WITNESS_ACCOUNT,
                activeWitnessesByVote.get(0).getOwner());
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetHardforkVersion() throws Exception {
        final String hardforkVersion = steemJ.getHardforkVersion();

        assertNotNull("Expect hardfork version to be present.", hardforkVersion);
        assertTrue(hardforkVersion.matches("[0-9]+[\\.]{1}[0-9]{2}[\\.]{1}[0-9]+"));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetInvalidAccountVotes() throws Exception {
        // Force an error response:
        try {
            steemJ.getAccountVotes(new AccountName("thisacountdoes"));
        } catch (final SteemResponseException steemResponseError) {
            // success
        } catch (final Exception e) {
            LOGGER.error(e);
            fail(e.toString());
        }
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetLookupAccount() throws Exception {
        final List<String> accounts = steemJ.lookupAccounts(ACCOUNT.getName(), 10);

        assertNotNull("expect accounts", accounts);
        assertThat("expect at least one account", accounts.size(), greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetLookupWitnessAccount() throws Exception {
        final List<String> accounts = steemJ.lookupWitnessAccounts("gtg", 10);

        assertNotNull("expect accounts", accounts);
        assertThat("expect at least one account", accounts.size(), greaterThan(0));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetRewardFund() throws Exception {
        final RewardFund rewardFund = steemJ.getRewardFund(RewardFundType.POST);

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
    public void testGetWitnessCount() throws Exception {
        final int witnessCount = steemJ.getWitnessCount();

        assertThat("expect the number of witnesses greater than 13071", witnessCount, greaterThan(13071));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetWitnessSchedule() throws Exception {
        final WitnessSchedule witnessSchedule = steemJ.getWitnessSchedule();

        assertNotNull("expect hardfork version", witnessSchedule);
        assertThat(witnessSchedule.getTop19Weight(), equalTo((short) 1));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetOrderBookUsingDatabaseApi() throws Exception {
        final OrderBook orderBook = steemJ.getOrderBookUsingDatabaseApi(1);

        assertThat(orderBook.getAsks().size(), equalTo(1));
        assertThat(orderBook.getBids().get(0).getSbd(), greaterThan(1L));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetFeedHistory() throws Exception {
        final FeedHistory feedHistory = steemJ.getFeedHistory();

        assertThat(feedHistory.getCurrentPrice().getBase().getAmount(), greaterThan(100L));
        assertThat(feedHistory.getPriceHistory().size(), greaterThan(1));
    }

    @Category({ IntegrationTest.class })
    @Test
    public void testGetOpenOrders() throws Exception {
        // TODO: If dez1337 removes this operation, the test will fail. Use the
        // SteemJ account for this.
        final List<ExtendedLimitOrder> openOrders = steemJ.getOpenOrders(ACCOUNT);

        assertThat(openOrders.size(), greaterThanOrEqualTo(1));
        assertThat(openOrders.get(0).getCreated().getDateTime(), equalTo("2017-07-20T19:30:27"));
        assertThat(openOrders.get(0).getExpiration().getDateTime(), equalTo("1969-12-31T23:59:59"));
        assertThat(openOrders.get(0).getDeferredFee(), equalTo(0L));
        assertThat(openOrders.get(0).getForSale(), equalTo(1L));
        assertThat(openOrders.get(0).getId(), equalTo(675734));
        assertThat(openOrders.get(0).getOrderId(), equalTo(1500579025L));
        assertThat(openOrders.get(0).getSeller(), equalTo(ACCOUNT));
        assertThat(openOrders.get(0).getSellPrice().getBase(), equalTo(new Asset(1, AssetSymbolType.SBD)));
    }

    @Category({ IntegrationTest.class })
    @Ignore
    @Test
    public void testGetConversionRequests() throws Exception {
        // TODO: Implement
        steemJ.getConversionRequests(new AccountName("dez1337"));
    }

    /**
     * The the <code>false</code> case of the
     * {@link SteemJ#verifyAuthority(SignedTransaction)} method by testing a
     * transaction signed with the wrong key.
     * 
     * The positive case of this operation is tested by the operation tests.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Category({ IntegrationTest.class })
    @Test(expected = SteemResponseException.class)
    public void testVerifyAuthority() throws Exception {
        List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();

        privateKeys
                .add(new ImmutablePair<>(PrivateKeyType.ACTIVE, "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3"));

        config.getPrivateKeyStorage().addAccount(new AccountName("dez1337"), privateKeys);

        Asset steemAmount = new Asset(1L, AssetSymbolType.STEEM);

        AccountName from = new AccountName("dez1337");
        AccountName to = new AccountName("steemj");
        String memo = "Test SteemJ";

        ArrayList<Operation> operations = new ArrayList<>();
        operations.add(new TransferOperation(from, to, steemAmount, memo));

        SignedTransaction signedTransaction = new SignedTransaction(REF_BLOCK_NUM, REF_BLOCK_PREFIX,
                new TimePointSec(EXPIRATION_DATE), operations, null);

        assertFalse(steemJ.verifyAuthority(signedTransaction));
    }

    // #########################################################################
    // ## UTILITY METHODS ######################################################
    // #########################################################################

    /**
     * Test if the
     * {@link SteemJ#getPrivateKeyFromPassword(AccountName, PrivateKeyType, String)}
     * method is working as expected.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testGetPrivateKeyFromPassword() throws Exception {
        // Create a new random key using the KeyGenerator and use this password
        // to generate the additional keys.
        String masterPassword = SteemJUtils.privateKeyToWIF(new KeyGenerator(
                "COLORER BICORN KASBEKE FAERIE LOCHIA GOMUTI SOVKHOZ Y GERMAL AUNTIE PERFUMY TIME FEATURE GANGAN CELEMIN MATZO",
                0).getPrivateKey());

        assertThat(
                SteemJ.getPrivateKeyFromPassword(new AccountName("dez1337"), PrivateKeyType.POSTING, masterPassword)
                        .getLeft().getAddressFromPublicKey(),
                equalTo("STM7UCTzg9orXeWKnHpMr9viwzMBRy1pnxC2nMHNDEkZnBbiSKJDD"));
        assertThat(SteemJ.getPrivateKeyFromPassword(new AccountName("dez1337"), PrivateKeyType.POSTING, masterPassword)
                .getRight(), equalTo("5KHroQR6SU3oquhirVKvRpDUYGeuXEksZfqkaqU5KEFSypFHXvU"));
    }
}

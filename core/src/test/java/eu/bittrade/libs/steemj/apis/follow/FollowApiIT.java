package eu.bittrade.libs.steemj.apis.follow;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.apis.follow.model.AccountReputation;
import eu.bittrade.libs.steemj.apis.follow.model.BlogEntry;
import eu.bittrade.libs.steemj.apis.follow.model.CommentBlogEntry;
import eu.bittrade.libs.steemj.apis.follow.model.CommentFeedEntry;
import eu.bittrade.libs.steemj.apis.follow.model.FeedEntry;
import eu.bittrade.libs.steemj.apis.follow.model.FollowApiObject;
import eu.bittrade.libs.steemj.apis.follow.model.FollowCountApiObject;
import eu.bittrade.libs.steemj.apis.follow.model.PostsPerAuthorPair;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.apis.market.history.MarketHistoryApi
 * MarketHistoryApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowApiIT extends BaseIT {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     */
    @BeforeClass
    public static void init() throws SteemCommunicationException {
        setupIntegrationTestEnvironment();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getFollowers(CommunicationHandler, AccountName, AccountName, FollowType, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetFollowers() throws SteemCommunicationException, SteemResponseException {
        final List<FollowApiObject> followers = FollowApi.getFollowers(COMMUNICATION_HANDLER,
                new AccountName("dez1337"), new AccountName("dez1337"), FollowType.BLOG, (short) 100);

        assertThat(followers.size(), equalTo(100));
        assertThat(followers.get(0).getFollower(), equalTo(new AccountName("dhwoodland")));
        assertThat(followers.get(0).getFollowing(), equalTo(new AccountName("dez1337")));
        assertThat(followers.get(0).getWhat(), contains(FollowType.BLOG));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getFollowing(CommunicationHandler, AccountName, AccountName, FollowType, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetFollowing() throws SteemCommunicationException, SteemResponseException {
        final List<FollowApiObject> following = FollowApi.getFollowing(COMMUNICATION_HANDLER,
                new AccountName("dez1337"), new AccountName("dez1337"), FollowType.BLOG, (short) 10);

        assertThat(following.size(), equalTo(10));
        assertTrue(following.get(0).getFollower().getName().matches("[a-z0-9\\.-]{3,16}"));
        assertTrue(following.get(0).getFollowing().getName().matches("[a-z0-9\\.-]{3,16}"));
        assertThat(following.get(0).getWhat(), contains(FollowType.BLOG));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getFollowCount(CommunicationHandler, AccountName)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetFollowCount() throws SteemCommunicationException, SteemResponseException {
        final FollowCountApiObject followCount = FollowApi.getFollowCount(COMMUNICATION_HANDLER,
                new AccountName("dez1337"));

        assertThat(followCount.getFollowerCount(), greaterThan(10));
        assertThat(followCount.getFollowingCount(), greaterThan(10));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getFeedEntries(CommunicationHandler, AccountName, int, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetFeedEntries() throws SteemCommunicationException, SteemResponseException {
        final List<FeedEntry> feedEntries = FollowApi.getFeedEntries(COMMUNICATION_HANDLER, new AccountName("dez1337"),
                0, (short) 100);
        assertThat(feedEntries.size(), equalTo(100));
        assertTrue(feedEntries.get(0).getPermlink().getLink().matches("[a-z0-9\\-]+"));
        assertFalse(feedEntries.get(0).getAuthor().isEmpty());
        assertThat(feedEntries.get(0).getEntryId(), greaterThanOrEqualTo(0));
        assertNotNull(feedEntries.get(0).getReblogBy());
        assertThat(feedEntries.get(0).getReblogOn().getDateTimeAsTimestamp(), greaterThanOrEqualTo(0L));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getFeed(CommunicationHandler, AccountName, int, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetFeed() throws SteemCommunicationException, SteemResponseException {
        final List<CommentFeedEntry> feed = FollowApi.getFeed(COMMUNICATION_HANDLER, new AccountName("dez1337"), 0,
                (short) 5);
        assertThat(feed.size(), equalTo(5));

        assertFalse(feed.get(0).getComment().getAuthor().isEmpty());
        assertTrue(feed.get(0).getComment().getPermlink().getLink().matches("[a-z0-9\\-]+"));
        assertFalse(feed.get(0).getComment().getTitle().isEmpty());

        assertThat(feed.get(0).getEntryId(), greaterThanOrEqualTo(0));
        assertNotNull(feed.get(0).getReblogBy());
        assertThat(feed.get(0).getReblogOn().getDateTimeAsTimestamp(), greaterThanOrEqualTo(0L));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getBlogEntries(CommunicationHandler, AccountName, int, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlogEntries() throws SteemCommunicationException, SteemResponseException {
        final List<BlogEntry> blogEntries = FollowApi.getBlogEntries(COMMUNICATION_HANDLER, new AccountName("dez1337"),
                0, (short) 5);

        assertThat(blogEntries.size(), equalTo(5));
        assertTrue(blogEntries.get(0).getPermlink().getLink().matches("[a-z0-9\\-]+"));
        assertFalse(blogEntries.get(0).getAuthor().isEmpty());
        assertThat(blogEntries.get(0).getEntryId(), greaterThanOrEqualTo(0));
        assertFalse(blogEntries.get(0).getBlog().isEmpty());
        assertThat(blogEntries.get(0).getReblogOn().getDateTimeAsTimestamp(), greaterThanOrEqualTo(0L));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getBlog(CommunicationHandler, AccountName, int, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlog() throws SteemCommunicationException, SteemResponseException {
        final List<CommentBlogEntry> blog = FollowApi.getBlog(COMMUNICATION_HANDLER, new AccountName("dez1337"), 0,
                (short) 5);

        assertThat(blog.size(), equalTo(5));

        assertFalse(blog.get(0).getComment().getAuthor().isEmpty());
        assertTrue(blog.get(0).getComment().getPermlink().getLink().matches("[a-z0-9\\-]+"));
        assertFalse(blog.get(0).getComment().getTitle().isEmpty());

        assertThat(blog.get(0).getEntryId(), greaterThan(0));
        assertThat(blog.get(0).getReblogOn().getDateTimeAsTimestamp(), greaterThanOrEqualTo(0L));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getAccountReputations(CommunicationHandler, AccountName, int)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccountReputation() throws SteemCommunicationException, SteemResponseException {
        final List<AccountReputation> accountReputations = FollowApi.getAccountReputations(COMMUNICATION_HANDLER,
                new AccountName("dez1337"), 10);

        assertThat(accountReputations.size(), equalTo(10));
        assertThat(accountReputations.get(0).getReputation(), greaterThan(14251747809260L));
        assertThat(accountReputations.get(0).getAccount(), equalTo(new AccountName("dez1337")));
        assertThat(accountReputations.get(1).getAccount(), not(equalTo(new AccountName("dez1337"))));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getRebloggedBy(CommunicationHandler, AccountName, Permlink)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetRebloggedBy() throws SteemCommunicationException, SteemResponseException {
        final List<AccountName> accountNames = FollowApi.getRebloggedBy(COMMUNICATION_HANDLER,
                new AccountName("dez1337"), new Permlink("steemj-v0-2-6-has-been-released-update-11"));

        assertThat(accountNames.size(), greaterThan(2));
        assertThat(accountNames.get(1), equalTo(new AccountName("jesuscirino")));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.apis.follow.FollowApi#getBlogAuthors(CommunicationHandler, AccountName)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetBlogAuthors() throws SteemCommunicationException, SteemResponseException {
        final List<PostsPerAuthorPair> blogAuthors = FollowApi.getBlogAuthors(COMMUNICATION_HANDLER,
                new AccountName("dez1337"));

        assertThat(blogAuthors.size(), greaterThan(2));
        assertTrue(blogAuthors.get(1).getAccount().getName().matches("[a-z0-9\\.-]{3,16}"));
        assertThat(blogAuthors.get(1).getNumberOfPosts(), greaterThan(0));
    }
}

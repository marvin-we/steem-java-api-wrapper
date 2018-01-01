package eu.bittrade.libs.steemj.plugins.apis.tags;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import eu.bittrade.libs.steemj.DiscussionQuery;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.Tag;
import eu.bittrade.libs.steemj.base.models.VoteState;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.exceptions.SteemTransformationException;
import eu.bittrade.libs.steemj.plugins.apis.database.models.state.Discussion;
import eu.bittrade.libs.steemj.plugins.apis.tags.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class implements the tags api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TagsApi {
    /** Add a private constructor to hide the implicit public one. */
    private TagsApi() {
    }

    /**
     * Use this method to get detailed values and metrics for tags. The methods
     * accepts a String as a search pattern and a number to limit the results.
     * 
     * <b>Example</b>
     * <p>
     * <code>getTrendingTags(communicationHandler, "steem", 2);</code> <br>
     * Will return two tags whose name has the biggest match with the String
     * "steem". An example response could contain the metrics and values for the
     * tag "steem" and "steemit", while "steem" would be the first entry in the
     * list as it has a bigger match than "steemit".
     * </p>
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param firstTagPattern
     *            The search pattern used to build the resulting list of tags.
     * @param limit
     *            The maximum number of results.
     * @return A list of the tags. The first entry in the list is the tag that
     *         has the biggest match with the <code>firstTagPattern</code>.
     *         while the last tag in the last has the smallest match.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public static List<Tag> getTrendingTags(CommunicationHandler communicationHandler, String firstTagPattern,
            int limit) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_TRENDING_TAGS);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = { firstTagPattern, String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Tag.class);
    }

    public static void getTagsUsedByAuthor() {
        
    }
    
    public static void getDiscussion() {
        
    }
    
    /**
     * Get the replies of a specific post.
     * 
     * @param author
     *            The authors name.
     * @param permlink
     *            The permlink of the article.
     * @return A list of discussions or null if the post has no replies.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getContentReplies(AccountName author, Permlink permlink)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_CONTENT_REPLIES);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = { author.getName(), permlink.getLink() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Discussion.class);
    }
    
    
    public static void getPostDiscussionsByPayout() {
        
    }
    
    public static void getCommentDiscussionsByPayout() {
        
    }
    
    /**
     * Get active discussions for a specified tag.
     * 
     * @param discussionQuery
     *            A query defining specific search parameters.
     * @param sortBy
     *            Choose the method used for sorting the results.
     * @return A list of discussions matching the given conditions.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getDiscussionsBy(DiscussionQuery discussionQuery, DiscussionSortType sortBy)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();

        requestObject.setApiMethod(RequestMethods.valueOf(sortBy.name()));
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        Object[] parameters = { discussionQuery };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Discussion.class);
        
        (get_discussions_by_trending)
        (get_discussions_by_created)
        (get_discussions_by_active)
        (get_discussions_by_cashout)
        (get_discussions_by_votes)
        (get_discussions_by_children)
        (get_discussions_by_hot)
        (get_discussions_by_feed)
        (get_discussions_by_blog)
        (get_discussions_by_comments)
        (get_discussions_by_promoted)
    }
    

    /**
     * /** Get a list of Content starting from the given post of the given user.
     * The list will be sorted by the Date of the last update.
     * 
     * @param username
     *            The name of the user.
     * @param permlink
     *            The permlink of an article.
     * @param limit
     *            Number of results.
     * @return A list of Content objects.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getRepliesByLastUpdate(AccountName username, Permlink permlink, int limit)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_REPLIES_BY_LAST_UPDATE);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        Object[] parameters = { username, permlink.getLink(), String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Discussion.class);
    }
    
    /**
     * Get a list of discussion for a given author.
     * 
     * @param author
     *            The authors name.
     * @param permlink
     *            The permlink of the article.
     * @param date
     *            Only return articles before this date. (This field seems to be
     *            ignored by the Steem api)
     * @param limit
     *            The number of results you want to receive.
     * @return A list of discussions.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<Discussion> getDiscussionsByAuthorBeforeDate(AccountName author, Permlink permlink, String date,
            int limit) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();

        requestObject.setApiMethod(RequestMethods.GET_DISCUSSIONS_BY_AUTHOR_BEFORE_DATE);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);

        // Verify that the date has the correct format.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SteemJConfig.getInstance().getDateTimePattern());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(SteemJConfig.getInstance().getTimeZoneId()));
        Date beforeDate;
        try {
            beforeDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new SteemTransformationException("Could not parse the received date to a Date object.", e);
        }

        String[] parameters = { author.getName(), permlink.getLink(), simpleDateFormat.format(beforeDate),
                String.valueOf(limit) };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Discussion.class);
    }
    
    /**
     * Get the active votes for a given post of a given author.
     * 
     * @param author
     *            The authors name.
     * @param permlink
     *            The permlink of the article.
     * @return A list of votes for a specific article.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public List<VoteState> getActiveVotes(AccountName author, Permlink permlink)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_ACTIVE_VOTES);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = { author.getName(), permlink.getLink() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, VoteState.class);
    }
}

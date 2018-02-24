/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.tags;

import java.util.List;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.tags.enums.DiscussionSortType;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.Discussion;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.DiscussionQuery;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.DiscussionQueryResult;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetActiveVotesArgs;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetActiveVotesReturn;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetDiscussionArgs;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetDiscussionsByAuthorBeforeDateArgs;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetRepliesByLastUpdateArgs;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetTagsUsedByAuthorArgs;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.GetTagsUsedByAuthorReturn;
import eu.bittrade.libs.steemj.plugins.apis.tags.models.Tag;

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
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_TRENDING_TAGS,
                null);

        return communicationHandler.performRequest(requestObject, Tag.class);
    }

    /**
     * 
     * @param communicationHandler
     * @param getTagsUsedByAuthorArgs
     * @return GetTagsUsedByAuthorReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static GetTagsUsedByAuthorReturn getTagsUsedByAuthor(CommunicationHandler communicationHandler,
            GetTagsUsedByAuthorArgs getTagsUsedByAuthorArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_TAGS_USED_BY_AUTHOR, getTagsUsedByAuthorArgs);

        return communicationHandler.performRequest(requestObject, GetTagsUsedByAuthorReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param getDiscussionArgs
     * @return Dicussions
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static Discussion getDiscussion(CommunicationHandler communicationHandler,
            GetDiscussionArgs getDiscussionArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_DISCUSSION,
                getDiscussionArgs);

        return communicationHandler.performRequest(requestObject, Discussion.class).get(0);
    }

    /**
     * Get the replies of a specific post.
     * 
     * @param communicationHandler
     * @param discussionQuery
     * 
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
    public static DiscussionQueryResult getContentReplies(CommunicationHandler communicationHandler,
            DiscussionQuery discussionQuery) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_CONTENT_REPLIES,
                discussionQuery);
        return communicationHandler.performRequest(requestObject, DiscussionQueryResult.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param discussionQuery
     * @return DiscussionQueryResult
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static DiscussionQueryResult getPostDiscussionsByPayout(CommunicationHandler communicationHandler,
            DiscussionQuery discussionQuery) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_POST_DISCUSSIONS_BY_PAYOUT, discussionQuery);

        return communicationHandler.performRequest(requestObject, DiscussionQueryResult.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param discussionQuery
     * @return DiscussionQueryResult
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static DiscussionQueryResult getCommentDiscussionsByPayout(CommunicationHandler communicationHandler,
            DiscussionQuery discussionQuery) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_COMMENT_DISCUSSIONS_BY_PAYOUT, discussionQuery);

        return communicationHandler.performRequest(requestObject, DiscussionQueryResult.class).get(0);
    }

    /**
     * Get active discussions for a specified tag.
     * 
     * @param communicationHandler
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
    public static List<Discussion> getDiscussionsBy(CommunicationHandler communicationHandler,
            DiscussionQuery discussionQuery, DiscussionSortType sortBy)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_DISCUSSIONS_BY_ACTIVE, discussionQuery);

        return communicationHandler.performRequest(requestObject, Discussion.class);
    }

    /**
     * /** Get a list of Content starting from the given post of the given user.
     * The list will be sorted by the Date of the last update.
     * 
     * @param communicationHandler
     * @param getRepliesByLastUpdateArgs
     * 
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
    public static List<Discussion> getRepliesByLastUpdate(CommunicationHandler communicationHandler,
            GetRepliesByLastUpdateArgs getRepliesByLastUpdateArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_REPLIES_BY_LAST_UPDATE, getRepliesByLastUpdateArgs);

        return communicationHandler.performRequest(requestObject, Discussion.class);
    }

    /**
     * Get a list of discussion for a given author.
     * 
     * @param communicationHandler
     * 
     * @param getDiscussionsByAuthorBeforeDateArgs
     * 
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
    public static DiscussionQueryResult getDiscussionsByAuthorBeforeDate(CommunicationHandler communicationHandler,
            GetDiscussionsByAuthorBeforeDateArgs getDiscussionsByAuthorBeforeDateArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_DISCUSSIONS_BY_AUTHOR_BEFORE_DATE, getDiscussionsByAuthorBeforeDateArgs);

        return communicationHandler.performRequest(requestObject, DiscussionQueryResult.class).get(0);
    }

    /**
     * Get the active votes for a given post of a given author.
     * 
     * @param communicationHandler
     * @param getActiveVotesArgs
     * 
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
    public static GetActiveVotesReturn getActiveVotes(CommunicationHandler communicationHandler,
            GetActiveVotesArgs getActiveVotesArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_ACTIVE_VOTES,
                getActiveVotesArgs);

        return communicationHandler.performRequest(requestObject, GetActiveVotesReturn.class).get(0);
    }
}

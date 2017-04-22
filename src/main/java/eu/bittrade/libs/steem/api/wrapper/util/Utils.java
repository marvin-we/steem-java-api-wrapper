package eu.bittrade.libs.steem.api.wrapper.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steem.api.wrapper.enums.DiscussionSortType;
import eu.bittrade.libs.steem.api.wrapper.enums.RequestMethods;

/**
 * This class contains some utility methods used by the steem api wrapper.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Utils {
    private static final Logger LOGGER = LogManager.getLogger(Utils.class);

    /** Add a private constructor to hide the implicit public one. */
    private Utils() {
    }

    public static RequestMethods getEquivalentRequestMethod(DiscussionSortType discussionSortType) {
        switch (discussionSortType) {
        case SORT_BY_ACTIVE:
            return RequestMethods.GET_DISCUSSIONS_BY_ACTIVE;
        case SORT_BY_BLOG:
            return RequestMethods.GET_DISCUSSIONS_BY_BLOG;
        case SORT_BY_CASHOUT:
            return RequestMethods.GET_DISCUSSIONS_BY_CASHOUT;
        case SORT_BY_CHILDREN:
            return RequestMethods.GET_DISCUSSIONS_BY_CHILDREN;
        case SORT_BY_COMMENTS:
            return RequestMethods.GET_DISCUSSIONS_BY_COMMENTS;
        case SORT_BY_CREATED:
            return RequestMethods.GET_DISCUSSIONS_BY_CREATED;
        case SORT_BY_FEED:
            return RequestMethods.GET_DISCUSSIONS_BY_FEED;
        case SORT_BY_HOT:
            return RequestMethods.GET_DISCUSSIONS_BY_HOT;
        case SORT_BY_PAYOUT:
            return RequestMethods.GET_DISCUSSIONS_BY_PAYOUT;
        case SORT_BY_PROMOTED:
            return RequestMethods.GET_DISCUSSIONS_BY_PROMOTED;
        case SORT_BY_TRENDING:
            return RequestMethods.GET_DISCUSSIONS_BY_TRENDING;
        case SORT_BY_TRENDING_30_DAYS:
            return RequestMethods.GET_DISCUSSIONS_BY_TRENDING30;
        case SORT_BY_VOTES:
            return RequestMethods.GET_DISCUSSIONS_BY_VOTES;
        default:
            LOGGER.warn(
                    "Unkown sort type. The resulting discussions are now sorted by the values of the 'active' field (SORT_BY_ACTIVE).");
            return RequestMethods.GET_DISCUSSIONS_BY_ACTIVE;
        }
    }
}

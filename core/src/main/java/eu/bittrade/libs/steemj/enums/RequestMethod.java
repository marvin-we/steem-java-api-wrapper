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
package eu.bittrade.libs.steemj.enums;

/**
 * An enumeration for all existing API methods.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum RequestMethod {
    // account_by_key_api
    /** Find accounts by their public key. */
    GET_KEY_REFERENCES,
    // account_history_api
    /** Get all operations in a block. */
    GET_OPS_IN_BLOCK,
    /** Get a transaction by its Id. */
    GET_TRANSACTION,
    /** Get the full history of an account. */
    GET_ACCOUNT_HISTORY,
    // block_api
    /** */
    GET_BLOCK,
    /** */
    GET_BLOCK_HEADER,
    // chain_api
    /** */
    PUSH_BLOCK,
    /** */
    PUSH_TRANSACTION,
    // condenser_api
    /** */
    GET_STATE,
    /** */
    GET_NEXT_SCHEDULED_HARDFORK,
    /** */
    GET_ACCOUNTS,
    /** */
    GET_ACCOUNT_VOTES,
    /** */
    GET_ACCOUNT_COUNT,
    /** */
    GET_OPEN_ORDERS,
    /** */
    GET_CHAIN_PROPERTIES,
    /** */
    GET_CONTENT,
    /** */
    GET_CONVERSION_REQUESTS,
    /** */
    GET_CURRENT_MEDIAN_HISTORY_PRICE,
    /** */
    GET_WITNESS_BY_ACCOUNT,
    /** */
    LOOKUP_WITNESS_ACCOUNTS,
    /** */
    LOOKUP_ACCOUNTS,
    /** */
    GET_WITNESSES,
    /** */
    GET_WITNESS_COUNT,
    /** */
    GET_WITNESSES_BY_VOTE,
    /** */
    GET_HARDFORK_VERSION,
    // database_api
    /** */
    GET_CONFIG,
    /** */
    GET_DYNAMIC_GLOBAL_PROPERTIES,
    /** */
    GET_WITNESS_SCHEDULE,
    /** */
    GET_HARDFORK_PROPERTIES,
    /** */
    GET_REWARD_FUNDS,
    /** */
    GET_CURRENT_PRICE_FEED,
    /** */
    GET_FEED_HISTORY,
    /** */
    LIST_WITNESSES,
    /** */
    FIND_WITNESSES,
    /** */
    LIST_WITNESS_VOTES,
    /** */
    GET_ACTIVE_WITNESSES,
    /** */
    LIST_ACCOUNTS,
    /** */
    FIND_ACCOUNTS,
    /** */
    LIST_OWNER_HISTORIES,
    /** */
    FIND_OWNER_HISTORIES,
    /** */
    LIST_ACCOUNT_RECOVERY_REQUESTS,
    /** */
    FIND_ACCOUNT_RECOVERY_REQUESTS,
    /** */
    LIST_CHANGE_RECOVERY_ACCOUNT_REQUESTS,
    /** */
    FIND_CHANGE_RECOVERY_ACCOUNT_REQUESTS,
    /** */
    LIST_ESCROWS,
    /** */
    FIND_ESCROWS,
    /** */
    LIST_WITHDRAW_VESTING_ROUTES,
    /** */
    FIND_WITHDRAW_VESTING_ROUTES,
    /** */
    LIST_SAVINGS_WITHDRAWALS,
    /** */
    FIND_SAVINGS_WITHDRAWALS,
    /** */
    LIST_VESTING_DELEGATIONS,
    /** */
    FIND_VESTING_DELEGATIONS,
    /** */
    LIST_VESTING_DELEGATION_EXPIRATIONS,
    /** */
    FIND_VESTING_DELEGATION_EXPIRATIONS,
    /** */
    LIST_SBD_CONVERSION_REQUESTS,
    /** */
    FIND_SBD_CONVERSION_REQUESTS,
    /** */
    LIST_DECLINE_VOTING_RIGHTS_REQUESTS,
    /** */
    FIND_DECLINE_VOTING_RIGHTS_REQUESTS,
    /** */
    LIST_COMMENTS,
    /** */
    FIND_COMMENTS,
    /** */
    LIST_VOTES,
    /** */
    FIND_VOTES,
    /** */
    LIST_LIMIT_ORDERS,
    /** */
    FIND_LIMIT_ORDERS,
    /** */
    GET_ORDER_BOOK,
    /** */
    GET_TRANSACTION_HEX,
    /** */
    GET_REQUIRED_SIGNATURES,
    /** */
    GET_POTENTIAL_SIGNATURES,
    /** */
    VERIFY_AUTHORITY,
    /** */
    VERIFY_ACCOUNT_AUTHORITY,
    /** */
    VERIFY_SIGNATURES,
    /** */
    GET_SMT_NEXT_IDENTIFIER,
    // debug_node_api
    /** */
    DEBUG_POP_BLOCK,
    /** */
    DEBUG_GET_WITNESS_SCHEDULE,
    /** */
    DEBUG_GET_HARDFORK_PROPERTY_OBJECT,
    /** */
    DEBUG_SET_HARDFORK,
    /** */
    DEBUG_HAS_HARDFORK,
    /** */
    DEBUG_GET_JSON_SCHEMA,
    // follow_api
    /** */
    GET_FOLLOWERS,
    /** */
    GET_FOLLOWING,
    /** */
    GET_FOLLOW_COUNT,
    /** */
    GET_FEED_ENTRIES,
    /** */
    GET_FEED,
    /** */
    GET_BLOG_ENTRIES,
    /** */
    GET_BLOG,
    /** */
    GET_ACCOUNT_REPUTATIONS,
    /** */
    GET_REBLOGGED_BY,
    /** */
    GET_BLOG_AUTHORS,
    // market_history_api
    // GET_ORDER_BOOK (doubled with database_api)
    /** */
    GET_TICKER,
    /** */
    GET_VOLUME,
    /** */
    GET_TRADE_HISTORY,
    /** */
    GET_RECENT_TRADES,
    /** */
    GET_MARKET_HISTORY,
    /** */
    GET_MARKET_HISTORY_BUCKETS,
    // network_broadcast_api
    /** */
    BROADCAST_TRANSACTION,
    /** */
    BROADCAST_TRANSACTION_SYNCHRONOUS,
    /** */
    BROADCAST_BLOCK,
    // tags_api
    /** */
    GET_TRENDING_TAGS,
    /** */
    GET_TAGS_USED_BY_AUTHOR,
    /** */
    GET_DISCUSSION,
    /** */
    GET_CONTENT_REPLIES,
    /** */
    GET_POST_DISCUSSIONS_BY_PAYOUT,
    /** */
    GET_COMMENT_DISCUSSIONS_BY_PAYOUT,
    /** */
    GET_DISCUSSIONS_BY_TRENDING,
    /** */
    GET_DISCUSSIONS_BY_CREATED,
    /** */
    GET_DISCUSSIONS_BY_ACTIVE,
    /** */
    GET_DISCUSSIONS_BY_CASHOUT,
    /** */
    GET_DISCUSSIONS_BY_VOTES,
    /** */
    GET_DISCUSSIONS_BY_CHILDREN,
    /** */
    GET_DISCUSSIONS_BY_HOT,
    /** */
    GET_DISCUSSIONS_BY_FEED,
    /** */
    GET_DISCUSSIONS_BY_BLOG,
    /** */
    GET_DISCUSSIONS_BY_COMMENTS,
    /** */
    GET_DISCUSSIONS_BY_PROMOTED,
    /** */
    GET_REPLIES_BY_LAST_UPDATE,
    /** */
    GET_DISCUSSIONS_BY_AUTHOR_BEFORE_DATE,
    /** */
    GET_ACTIVE_VOTES,
    // witness_api
    /** */
    GET_ACCOUNT_BANDWIDTH,
    /** */
    GET_REVERSE_RATIO
}

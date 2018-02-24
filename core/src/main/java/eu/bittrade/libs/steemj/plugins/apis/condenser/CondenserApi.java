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
package eu.bittrade.libs.steemj.plugins.apis.condenser;

import java.util.List;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.ExtendedAccount;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.ExtendedDynamicGlobalProperties;
import eu.bittrade.libs.steemj.plugins.apis.condenser.models.State;
import eu.bittrade.libs.steemj.plugins.apis.database.DatabaseApi;

/**
 * This class implements the "condenser_api".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CondenserApi {
    /** Add a private constructor to hide the implicit public one. */
    private CondenserApi() {
    }

    /**
     * Like
     * {@link DatabaseApi#getDynamicGlobalProperties(CommunicationHandler)}, but
     * returns an {@link ExtendedDynamicGlobalProperties} object providing
     * additional information.
     * 
     * @param communicationHandler
     * @return ExtendedDynamicGlobalProperties
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
    public static ExtendedDynamicGlobalProperties getDynamicGlobalProperties(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.CONDENSER_API,
                RequestMethod.GET_DYNAMIC_GLOBAL_PROPERTIES, null);

        return communicationHandler.performRequest(requestObject, ExtendedDynamicGlobalProperties.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @return
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
    public static List<ExtendedAccount> getAccounts(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.CONDENSER_API, RequestMethod.GET_ACCOUNTS, null);

        return communicationHandler.performRequest(requestObject, ExtendedAccount.class);
    }

    /**
     * This API is a short-cut for returning all of the state required for a
     * particular URL with a single query.
     * 
     * TODO: Provide examples.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param path
     *            TODO: Fix JavaDoc
     * @return TODO: Fix JavaDoc
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
    public static State getState(CommunicationHandler communicationHandler, Permlink path)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_STATE, null);

        return communicationHandler.performRequest(requestObject, State.class).get(0);
    }

    /**
     * Get the hardfork version the node you are connected to is using.
     * 
     * @return The hardfork version that the connected node is running on.
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
    public static String getHardforkVersion(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_HARDFORK_VERSION,
                null);

        return communicationHandler.performRequest(requestObject, String.class).get(0);
    }
    /*
     * (get_version) (get_trending_tags) (get_state) (get_active_witnesses)
     * (get_block_header) (get_block) (get_ops_in_block) (get_config)
     * (get_dynamic_global_properties) (get_chain_properties)
     * (get_current_median_history_price) (get_feed_history)
     * (get_witness_schedule) DONE (get_hardfork_version)
     * (get_next_scheduled_hardfork) (get_reward_fund) (get_key_references)
     * (get_accounts) (get_account_references) (lookup_account_names)
     * (lookup_accounts) (get_account_count) (get_owner_history)
     * (get_recovery_request) (get_escrow) (get_withdraw_routes)
     * (get_account_bandwidth) (get_savings_withdraw_from)
     * (get_savings_withdraw_to) (get_vesting_delegations)
     * (get_expiring_vesting_delegations) (get_witnesses)
     * (get_conversion_requests) (get_witness_by_account)
     * (get_witnesses_by_vote) (lookup_witness_accounts) (get_witness_count)
     * (get_open_orders) (get_transaction_hex) (get_transaction)
     * (get_required_signatures) (get_potential_signatures) (verify_authority)
     * (verify_account_authority) (get_active_votes) (get_account_votes)
     * (get_content) (get_content_replies) (get_tags_used_by_author)
     * (get_post_discussions_by_payout) (get_comment_discussions_by_payout)
     * (get_discussions_by_trending) (get_discussions_by_created)
     * (get_discussions_by_active) (get_discussions_by_cashout)
     * (get_discussions_by_votes) (get_discussions_by_children)
     * (get_discussions_by_hot) (get_discussions_by_feed)
     * (get_discussions_by_blog) (get_discussions_by_comments)
     * (get_discussions_by_promoted) (get_replies_by_last_update)
     * (get_discussions_by_author_before_date) (get_account_history)
     * (broadcast_transaction) (broadcast_transaction_synchronous)
     * (broadcast_block) (get_followers) (get_following) (get_follow_count)
     * (get_feed_entries) (get_feed) (get_blog_entries) (get_blog)
     * (get_account_reputations) (get_reblogged_by) (get_blog_authors)
     * (get_ticker) (get_volume) (get_order_book) (get_trade_history)
     * (get_recent_trades) (get_market_history) (get_market_history_buckets)
     */
}

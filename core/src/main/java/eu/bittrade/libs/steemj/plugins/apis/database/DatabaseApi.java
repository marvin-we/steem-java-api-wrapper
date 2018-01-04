package eu.bittrade.libs.steemj.plugins.apis.database;

import java.util.List;

import eu.bittrade.libs.steemj.base.models.FeedHistory;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.database.models.Config;
import eu.bittrade.libs.steemj.plugins.apis.database.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.plugins.apis.database.models.HardforkProperty;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListWitnessVotesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListWitnessesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.RewardFund;
import eu.bittrade.libs.steemj.plugins.apis.database.models.WitnessSchedule;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class implements the "database_api".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DatabaseApi {
    /** Add a private constructor to hide the implicit public one. */
    private DatabaseApi() {
    }
    

    /**
     * Get the configuration.
     * 
     * @return The steem configuration.
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
    public static Config getConfig(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_CONFIG);
        requestObject.setSteemApi(SteemApiType.CONDENSER_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Config.class).get(0);
    }
    

    /**
     * Get the global properties.
     * 
     * @return The dynamic global properties.
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
    public static DynamicGlobalProperty getDynamicGlobalProperties(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_DYNAMIC_GLOBAL_PROPERTIES);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, DynamicGlobalProperty.class).get(0);
    }
    
    /**
     * Get the witness schedule.
     * 
     * @return The witness schedule.
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
    public static WitnessSchedule getWitnessSchedule(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_WITNESS_SCHEDULE);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, WitnessSchedule.class).get(0);
    }
    
    public static HardforkProperty getHardforkProperties(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_HARDFORK_PROPERTIES);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        System.out.println(communicationHandler.performRequest(requestObject, WitnessSchedule.class).get(0));
        return null;
    }
    

    /**
     * Get detailed information of a specific reward fund.
     * 
     * @param rewordFundType
     *            One of the {@link eu.bittrade.libs.steemj.enums.RewardFundType
     *            RewardFundType}s.
     * @return A refund object containing detailed information about the
     *         requested reward fund.
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
    public static RewardFund getRewardFunds(CommunicationHandler communicationHandler, RewardFundType rewordFundType)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_REWARD_FUNDS);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        Object[] parameters = { rewordFundType.name().toLowerCase() };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, RewardFund.class).get(0);
    }
    
    public static Price getCurrentPriceFeed(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_CURRENT_PRICE_FEED);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        System.out.println(communicationHandler.performRequest(requestObject, Price.class).get(0));
        return null;
    }
    
    /**
     * Get the current price and a list of history prices combined in one
     * object.
     * 
     * @return The conversion history of SBD / STEEM.
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
    public FeedHistory getFeedHistory(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_FEED_HISTORY);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, FeedHistory.class).get(0);
    }

    public static ListWitnessesReturn listWitnesses(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.LIST_WITNESSES);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        System.out.println(communicationHandler.performRequest(requestObject, ListWitnessesReturn.class).get(0));
        return null;
    }
    
    public static ListWitnessesReturn findWitnesses(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_CURRENT_PRICE_FEED);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        System.out.println(communicationHandler.performRequest(requestObject, ListWitnessesReturn.class).get(0));
        return null;
    }
    
    public static ListWitnessVotesReturn listWitnessesVotesReturn(CommunicationHandler communicationHandler) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_CURRENT_PRICE_FEED);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        System.out.println(communicationHandler.performRequest(requestObject, ListWitnessVotesReturn.class).get(0));
        return null;
    }
    
    /**
     * Get the list of the current active witnesses.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @return The list of the current active witnesses.
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
    public static List<AccountName> getActiveWitnesses(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_ACTIVE_WITNESSES);
        requestObject.setSteemApi(SteemApiType.DATABASE_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, AccountName.class);
    }
    
    
   /* DECLARE_API_IMPL
    (
       DONE (get_config)
       DONE (get_dynamic_global_properties)
       DONE (get_witness_schedule)
       ~ (get_hardfork_properties)
       ~ (get_reward_funds)
       ~ (get_current_price_feed)
       DONE (get_feed_history)
       ~ (list_witnesses)
       ~ (find_witnesses)
       ~ (list_witness_votes)
       DONE (get_active_witnesses)
       (list_accounts)
       (find_accounts)
       (list_owner_histories)
       (find_owner_histories)
       (list_account_recovery_requests)
       (find_account_recovery_requests)
       (list_change_recovery_account_requests)
       (find_change_recovery_account_requests)
       (list_escrows)
       (find_escrows)
       (list_withdraw_vesting_routes)
       (find_withdraw_vesting_routes)
       (list_savings_withdrawals)
       (find_savings_withdrawals)
       (list_vesting_delegations)
       (find_vesting_delegations)
       (list_vesting_delegation_expirations)
       (find_vesting_delegation_expirations)
       (list_sbd_conversion_requests)
       (find_sbd_conversion_requests)
       (list_decline_voting_rights_requests)
       (find_decline_voting_rights_requests)
       (list_comments)
       (find_comments)
       (list_votes)
       (find_votes)
       (list_limit_orders)
       (find_limit_orders)
       (get_order_book)
       (get_transaction_hex)
       (get_required_signatures)
       (get_potential_signatures)
       (verify_authority)
       (verify_account_authority)
       (verify_signatures)
#ifdef STEEM_ENABLE_SMT
       (get_smt_next_identifier)
#endif
    )*/





    // #########################################################################
    // ## BLOCKS AND TRANSACTIONS ##############################################
    // #########################################################################





    /////////////
    // Globals //
    /////////////

    /*
     * /**
     * 
     * @brief Retrieve compile-time constants
     *
     * fc::variant_object get_config()const;
     **
     * 
     * @brief Return a JSON description of object representations
     *
     * std::string get_schema()const;
     * 
     * /**
     * 
     * @brief Retrieve the current @ref dynamic_global_property_object
     *
     * dynamic_global_property_api_obj get_dynamic_global_properties()const;
     * chain_properties get_chain_properties()const; price
     * get_current_median_history_price()const; feed_history_api_obj
     * get_feed_history()const; witness_schedule_api_obj
     * get_witness_schedule()const; hardfork_version
     * get_hardfork_version()const; scheduled_hardfork
     * get_next_scheduled_hardfork()const; reward_fund_api_obj get_reward_fund(
     * string name )const;
     * 
     * ////////// // Keys // //////////
     * 
     * vector<set<string>> get_key_references( vector<public_key_type> key
     * )const;
     * 
     * ////////////// // Accounts // //////////////
     * 
     * vector< extended_account > get_accounts( vector< string > names ) const;
     * 
     * /**
     * 
     * @return all accounts that referr to the key or account id in their owner
     * or active authorities.
     *
     * vector<account_id_type> get_account_references( account_id_type
     * account_id )const;
     * 
     * /**
     * 
     * @brief Get a list of accounts by name
     * 
     * @param account_names Names of the accounts to retrieve
     * 
     * @return The accounts holding the provided names
     *
     * This function has semantics identical to @ref get_objects
     *
     * vector<optional<account_api_obj>> lookup_account_names(const
     * vector<string>& account_names)const;
     * 
     * /**
     * 
     * @brief Get names and IDs for registered accounts
     * 
     * @param lower_bound_name Lower bound of the first name to return
     * 
     * @param limit Maximum number of results to return -- must not exceed 1000
     * 
     * @return Map of account names to corresponding IDs
     *
     * set<string> lookup_accounts(const string& lower_bound_name, uint32_t
     * limit)const;
     * 
     * /**
     * 
     * @brief Get the total number of accounts registered with the blockchain
     *
     * uint64_t get_account_count()const;
     * 
     * vector< owner_authority_history_api_obj > get_owner_history( string
     * account )const;
     * 
     * optional< account_recovery_request_api_obj > get_recovery_request( string
     * account ) const;
     * 
     * optional< escrow_api_obj > get_escrow( string from, uint32_t escrow_id
     * )const;
     * 
     * vector< withdraw_route > get_withdraw_routes( string account,
     * withdraw_route_type type = outgoing )const;
     * 
     * optional< account_bandwidth_api_obj > get_account_bandwidth( string
     * account, witness::bandwidth_type type )const;
     * 
     * vector< savings_withdraw_api_obj > get_savings_withdraw_from( string
     * account )const; vector< savings_withdraw_api_obj >
     * get_savings_withdraw_to( string account )const;
     * 
     * vector< vesting_delegation_api_obj > get_vesting_delegations( string
     * account, string from, uint32_t limit = 100 )const; vector<
     * vesting_delegation_expiration_api_obj > get_expiring_vesting_delegations(
     * string account, time_point_sec from, uint32_t limit = 100 )const;
     * 
     * /////////////// // Witnesses // ///////////////
     * 
     * /**
     * 
     * @brief Get a list of witnesses by ID
     * 
     * @param witness_ids IDs of the witnesses to retrieve
     * 
     * @return The witnesses corresponding to the provided IDs
     *
     * This function has semantics identical to @ref get_objects
     *
     * vector<optional<witness_api_obj>> get_witnesses(const
     * vector<witness_id_type>& witness_ids)const;
     * 
     * vector<convert_request_api_obj> get_conversion_requests( const string&
     * account_name )const;
     * 
     * /**
     * 
     * @brief Get the witness owned by a given account
     * 
     * @param account The name of the account whose witness should be retrieved
     * 
     * @return The witness object, or null if the account does not have a
     * witness
     *
     * fc::optional< witness_api_obj > get_witness_by_account( string
     * account_name )const;
     * 
     * /** This method is used to fetch witnesses with pagination.
     *
     * @return an array of `count` witnesses sorted by total votes after witness
     * `from` with at most `limit' results.
     *
     * vector< witness_api_obj > get_witnesses_by_vote( string from, uint32_t
     * limit )const;
     * 
     * /**
     * 
     * @brief Get names and IDs for registered witnesses
     * 
     * @param lower_bound_name Lower bound of the first name to return
     * 
     * @param limit Maximum number of results to return -- must not exceed 1000
     * 
     * @return Map of witness names to corresponding IDs
     *
     * set<account_name_type> lookup_witness_accounts(const string&
     * lower_bound_name, uint32_t limit)const;
     * 
     * /**
     * 
     * @brief Get the total number of witnesses registered with the blockchain
     *
     * uint64_t get_witness_count()const;
     * 
     * //////////// // Market // ////////////
     * 
     * /**
     * 
     * @breif Gets the current order book for STEEM:SBD market
     * 
     * @param limit Maximum number of orders for each side of the spread to
     * return -- Must not exceed 1000
     *
     * order_book get_order_book( uint32_t limit = 1000 )const;
     * vector<extended_limit_order> get_open_orders( string owner )const;
     * 
     * /**
     * 
     * @breif Gets the current liquidity reward queue.
     * 
     * @param start_account The account to start the list from, or "" to get the
     * head of the queue
     * 
     * @param limit Maxmimum number of accounts to return -- Must not exceed
     * 1000
     *
     * vector< liquidity_balance > get_liquidity_queue( string start_account,
     * uint32_t limit = 1000 )const;
     * 
     * //////////////////////////// // Authority / validation //
     * ////////////////////////////
     * 
     * /// @brief Get a hexdump of the serialized binary form of a transaction
     * std::string get_transaction_hex(const signed_transaction& trx)const;
     * annotated_signed_transaction get_transaction( transaction_id_type trx_id
     * )const;
     * 
     * /** This API will take a partially signed transaction and a set of public
     * keys that the owner has the ability to sign for and return the minimal
     * subset of public keys that should add signatures to the transaction.
     *
     * set<public_key_type> get_required_signatures( const signed_transaction&
     * trx, const flat_set<public_key_type>& available_keys )const;
     * 
     * /** This method will return the set of all public keys that could
     * possibly sign for a given transaction. This call can be used by wallets
     * to filter their set of public keys to just the relevant subset prior to
     * calling @ref get_required_signatures to get the minimum subset.
     *
     * set<public_key_type> get_potential_signatures( const signed_transaction&
     * trx )const;
     * 
     * /**
     * 
     * @return true of the @ref trx has all of the required signatures,
     * otherwise throws an exception
     *
     * bool verify_authority( const signed_transaction& trx )const;
     * 
     * /*
     * 
     * @return true if the signers have enough authority to authorize an account
     *
     * bool verify_account_authority( const string& name_or_id, const
     * flat_set<public_key_type>& signers )const;
     * 
     * /** if permlink is "" then it will return all votes for author
     *
     * vector<vote_state> get_active_votes( string author, string permlink
     * )const; vector<account_vote> get_account_votes( string voter )const;
     * 
     * 
     * discussion get_content( string author, string permlink )const;
     * vector<discussion> get_content_replies( string parent, string
     * parent_permlink )const;
     * 
     * ///@{ tags API /** This API will return the top 1000 tags used by an
     * author sorted by most frequently used * vector<pair<string,uint32_t>>
     * get_tags_used_by_author( const string& author )const; vector<discussion>
     * get_discussions_by_payout(const discussion_query& query )const;
     * vector<discussion> get_post_discussions_by_payout( const
     * discussion_query& query )const; vector<discussion>
     * get_comment_discussions_by_payout( const discussion_query& query )const;
     * vector<discussion> get_discussions_by_trending( const discussion_query&
     * query )const; vector<discussion> get_discussions_by_created( const
     * discussion_query& query )const; vector<discussion>
     * get_discussions_by_active( const discussion_query& query )const;
     * vector<discussion> get_discussions_by_cashout( const discussion_query&
     * query )const; vector<discussion> get_discussions_by_votes( const
     * discussion_query& query )const; vector<discussion>
     * get_discussions_by_children( const discussion_query& query )const;
     * vector<discussion> get_discussions_by_hot( const discussion_query& query
     * )const; vector<discussion> get_discussions_by_feed( const
     * discussion_query& query )const; vector<discussion>
     * get_discussions_by_blog( const discussion_query& query )const;
     * vector<discussion> get_discussions_by_comments( const discussion_query&
     * query )const; vector<discussion> get_discussions_by_promoted( const
     * discussion_query& query )const;
     * 
     * ///@}
     * 
     * /** For each of these filters: Get root content... Get any content... Get
     * root content in category.. Get any content in category...
     *
     * Return discussions Total Discussion Pending Payout Last Discussion Update
     * (or reply)... think Top Discussions by Total Payout
     *
     * Return content (comments) Pending Payout Amount Pending Payout Time
     * Creation Date
     *
     *
     * ///@{
     * 
     * 
     * 
     * /** Return the active discussions with the highest cumulative pending
     * payouts without respect to category, total pending payout means the
     * pending payout of all children as well.
     *
     * vector<discussion> get_replies_by_last_update( account_name_type
     * start_author, string start_permlink, uint32_t limit )const;
     * 
     * 
     * 
     * /** This method is used to fetch all posts/comments by start_author that
     * occur after before_date and start_permlink with up to limit being
     * returned.
     *
     * If start_permlink is empty then only before_date will be considered. If
     * both are specified the eariler to the two metrics will be used. This
     * should allow easy pagination.
     *
     * vector<discussion> get_discussions_by_author_before_date( string author,
     * string start_permlink, time_point_sec before_date, uint32_t limit )const;
     * 
     * /** Account operations have sequence numbers from 0 to N where N is the
     * most recent operation. This method returns operations in the range
     * [from-limit, from]
     *
     * @param from - the absolute sequence number, -1 means most recent, limit
     * is the number of operations before from.
     * 
     * @param limit - the maximum number of items that can be queried (0 to
     * 1000], must be less than from
     *
     * map<uint32_t, applied_operation> get_account_history( string account,
     * uint64_t from, uint32_t limit )const;
     */

}

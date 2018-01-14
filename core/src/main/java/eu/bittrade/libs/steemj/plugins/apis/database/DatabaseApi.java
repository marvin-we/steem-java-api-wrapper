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
package eu.bittrade.libs.steemj.plugins.apis.database;

import java.util.List;

import eu.bittrade.libs.steemj.base.models.FeedHistory;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.RewardFundType;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.database.models.Config;
import eu.bittrade.libs.steemj.plugins.apis.database.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindAccountRecoveryRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindAccountRecoveryRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindAccountsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindAccountsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindChangeRecoveryAccountRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindChangeRecoveryAccountRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindCommentsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindCommentsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindDeclineVotingRightsRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindDeclineVotingRightsRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindEscrowsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindEscrowsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindLimitOrdersArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindLimitOrdersReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindOwnerHistoriesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindOwnerHistoriesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindSavingsWithdrawalsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindSavingsWithdrawalsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindSbdConversionRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindSbdConversionRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindVestingDelegationExpirationsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindVestingDelegationExpirationsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindVestingDelegationsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindVestingDelegationsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindVotesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindVotesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindWithdrawVestingRoutesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.FindWithdrawVestingRoutesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.GetPotentialSignaturesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.GetPotentialSignaturesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.GetRequiredSignaturesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.GetRequiredSignaturesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.GetSmtNextIdentifierReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.GetTransactionHexArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.GetTransactionHexReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.HardforkProperty;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListAccountRecoveryRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListAccountRecoveryRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListAccountsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListAccountsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListChangeRecoveryAccountRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListChangeRecoveryAccountRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListCommentsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListCommentsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListDeclineVotingRightsRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListDeclineVotingRightsRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListEscrowsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListEscrowsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListLimitOrdersArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListLimitOrdersReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListOwnerHistoriesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListOwnerHistoriesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListSavingsWithdrawalsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListSavingsWithdrawalsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListSbdConversionRequestsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListSbdConversionRequestsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListVestingDelegationExpirationsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListVestingDelegationExpirationsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListVestingDelegationsArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListVestingDelegationsReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListVotesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListVotesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListWithdrawVestingRoutesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListWithdrawVestingRoutesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListWitnessVotesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.ListWitnessesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.RewardFund;
import eu.bittrade.libs.steemj.plugins.apis.database.models.VerifyAccountAuthorityArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.VerifyAccountAuthorityReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.VerifyAuthorityArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.VerifyAuthorityReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.VerifySignaturesArgs;
import eu.bittrade.libs.steemj.plugins.apis.database.models.VerifySignaturesReturn;
import eu.bittrade.libs.steemj.plugins.apis.database.models.WitnessSchedule;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookReturn;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Price;

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
     * @param communicationHandler
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
    public static Config getConfig(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_CONFIG, null);

        return communicationHandler.performRequest(requestObject, Config.class).get(0);
    }

    /**
     * Get the global properties.
     * 
     * @param communicationHandler
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
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_DYNAMIC_GLOBAL_PROPERTIES, null);

        return communicationHandler.performRequest(requestObject, DynamicGlobalProperty.class).get(0);
    }

    /**
     * Get the witness schedule.
     * 
     * @param communicationHandler
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
    public static WitnessSchedule getWitnessSchedule(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_WITNESS_SCHEDULE,
                null);

        return communicationHandler.performRequest(requestObject, WitnessSchedule.class).get(0);
    }

    /**
     * @param communicationHandler
     * @return HardforkProperty
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static HardforkProperty getHardforkProperties(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_HARDFORK_PROPERTIES, null);

        return communicationHandler.performRequest(requestObject, HardforkProperty.class).get(0);
    }

    /**
     * Get detailed information of a specific reward fund.
     * 
     * @param communicationHandler
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
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_REWARD_FUNDS,
                rewordFundType.name().toLowerCase());

        return communicationHandler.performRequest(requestObject, RewardFund.class).get(0);
    }

    /**
     * @param communicationHandler
     * @return Price
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static Price getCurrentPriceFeed(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_CURRENT_PRICE_FEED, null);

        return communicationHandler.performRequest(requestObject, Price.class).get(0);
    }

    /**
     * Get the current price and a list of history prices combined in one
     * object.
     * 
     * @param communicationHandler
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
    public FeedHistory getFeedHistory(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_FEED_HISTORY,
                null);

        return communicationHandler.performRequest(requestObject, FeedHistory.class).get(0);
    }

    /**
     * @param communicationHandler
     * @return ListWitnessesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListWitnessesReturn listWitnesses(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.LIST_WITNESSES,
                null);

        return communicationHandler.performRequest(requestObject, ListWitnessesReturn.class).get(0);
    }

    /**
     * @param communicationHandler
     * @return ListWitnessesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListWitnessesReturn findWitnesses(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_CURRENT_PRICE_FEED, null);

        return communicationHandler.performRequest(requestObject, ListWitnessesReturn.class).get(0);
    }

    /**
     * @param communicationHandler
     * @return ListWitnessVotesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListWitnessVotesReturn listWitnessesVotesReturn(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_CURRENT_PRICE_FEED, null);

        return communicationHandler.performRequest(requestObject, ListWitnessVotesReturn.class).get(0);
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
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_ACTIVE_WITNESSES,
                null);

        return communicationHandler.performRequest(requestObject, AccountName.class);
    }

    /**
     * 
     * @param communicationHandler
     * @param listAccountsArgs
     * @return ListAccountsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListAccountsReturn listAccounts(CommunicationHandler communicationHandler,
            ListAccountsArgs listAccountsArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.LIST_ACCOUNTS,
                listAccountsArgs);

        return communicationHandler.performRequest(requestObject, ListAccountsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findAccountsArgs
     * @return FindAccountsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindAccountsReturn findAccounts(CommunicationHandler communicationHandler,
            FindAccountsArgs findAccountsArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.FIND_ACCOUNTS,
                findAccountsArgs);

        return communicationHandler.performRequest(requestObject, FindAccountsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listOwnerHistoriesArgs
     * @return ListOwnerHistoriesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListOwnerHistoriesReturn listOwnerHistories(CommunicationHandler communicationHandler,
            ListOwnerHistoriesArgs listOwnerHistoriesArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.LIST_OWNER_HISTORIES,
                listOwnerHistoriesArgs);

        return communicationHandler.performRequest(requestObject, ListOwnerHistoriesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findOwnerHistoriesArgs
     * @return FindOwnerHistoriesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindOwnerHistoriesReturn findOwnerHistories(CommunicationHandler communicationHandler,
            FindOwnerHistoriesArgs findOwnerHistoriesArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.FIND_OWNER_HISTORIES,
                findOwnerHistoriesArgs);

        return communicationHandler.performRequest(requestObject, FindOwnerHistoriesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listAccountRecoveryRequestsArgs
     * @return ListAccountRecoveryRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListAccountRecoveryRequestsReturn listAccountRecoveryRequests(
            CommunicationHandler communicationHandler, ListAccountRecoveryRequestsArgs listAccountRecoveryRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_ACCOUNT_RECOVERY_REQUESTS, listAccountRecoveryRequestsArgs);

        return communicationHandler.performRequest(requestObject, ListAccountRecoveryRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findAccountRecoveryRequestsArgs
     * @return FindAccountRecoveryRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindAccountRecoveryRequestsReturn findAccountRecoveryRequests(
            CommunicationHandler communicationHandler, FindAccountRecoveryRequestsArgs findAccountRecoveryRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_ACCOUNT_RECOVERY_REQUESTS, findAccountRecoveryRequestsArgs);

        return communicationHandler.performRequest(requestObject, FindAccountRecoveryRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listChangeRecoveryAccountRequestsArgs
     * @return ListChangeRecoveryAccountRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListChangeRecoveryAccountRequestsReturn listChangeRecoveryAccountRequests(
            CommunicationHandler communicationHandler,
            ListChangeRecoveryAccountRequestsArgs listChangeRecoveryAccountRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_CHANGE_RECOVERY_ACCOUNT_REQUESTS, listChangeRecoveryAccountRequestsArgs);

        return communicationHandler.performRequest(requestObject, ListChangeRecoveryAccountRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findChangeRecoveryAccountRequestsArgs
     * @return FindChangeRecoveryAccountRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindChangeRecoveryAccountRequestsReturn findChangeRecoveryAccountRequests(
            CommunicationHandler communicationHandler,
            FindChangeRecoveryAccountRequestsArgs findChangeRecoveryAccountRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_CHANGE_RECOVERY_ACCOUNT_REQUESTS, findChangeRecoveryAccountRequestsArgs);

        return communicationHandler.performRequest(requestObject, FindChangeRecoveryAccountRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listEscrowsArgs
     * @return ListEscrowsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListEscrowsReturn listEscrows(CommunicationHandler communicationHandler,
            ListEscrowsArgs listEscrowsArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.LIST_ESCROWS,
                listEscrowsArgs);

        return communicationHandler.performRequest(requestObject, ListEscrowsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findEscrowsArgs
     * @return FindEscrowsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindEscrowsReturn findEscrows(CommunicationHandler communicationHandler,
            FindEscrowsArgs findEscrowsArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.FIND_ESCROWS,
                findEscrowsArgs);

        return communicationHandler.performRequest(requestObject, FindEscrowsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listWithdrawVestingRoutesArgs
     * @return ListWithdrawVestingRoutesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListWithdrawVestingRoutesReturn listWithdrawVestingRoutes(CommunicationHandler communicationHandler,
            ListWithdrawVestingRoutesArgs listWithdrawVestingRoutesArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_WITHDRAW_VESTING_ROUTES, listWithdrawVestingRoutesArgs);

        return communicationHandler.performRequest(requestObject, ListWithdrawVestingRoutesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findWithdrawVestingRoutesArgs
     * @return FindWithdrawVestingRoutesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindWithdrawVestingRoutesReturn findWithdrawVestingRoutes(CommunicationHandler communicationHandler,
            FindWithdrawVestingRoutesArgs findWithdrawVestingRoutesArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_WITHDRAW_VESTING_ROUTES, findWithdrawVestingRoutesArgs);

        return communicationHandler.performRequest(requestObject, FindWithdrawVestingRoutesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listSavingsWithdrawalsArgs
     * @return ListSavingsWithdrawalsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListSavingsWithdrawalsReturn listSavingsWithdrawals(CommunicationHandler communicationHandler,
            ListSavingsWithdrawalsArgs listSavingsWithdrawalsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_SAVINGS_WITHDRAWALS, listSavingsWithdrawalsArgs);

        return communicationHandler.performRequest(requestObject, ListSavingsWithdrawalsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findSavingsWithdrawalsArgs
     * @return FindSavingsWithdrawalsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindSavingsWithdrawalsReturn findSavingsWithdrawals(CommunicationHandler communicationHandler,
            FindSavingsWithdrawalsArgs findSavingsWithdrawalsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_SAVINGS_WITHDRAWALS, findSavingsWithdrawalsArgs);

        return communicationHandler.performRequest(requestObject, FindSavingsWithdrawalsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listVestingDelegationsArgs
     * @return ListVestingDelegationsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListVestingDelegationsReturn listVestingDelegations(CommunicationHandler communicationHandler,
            ListVestingDelegationsArgs listVestingDelegationsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_VESTING_DELEGATIONS, listVestingDelegationsArgs);

        return communicationHandler.performRequest(requestObject, ListVestingDelegationsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findVestingDelegationsArgs
     * @return FindVestingDelegationsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindVestingDelegationsReturn findVestingDelegations(CommunicationHandler communicationHandler,
            FindVestingDelegationsArgs findVestingDelegationsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_VESTING_DELEGATIONS, findVestingDelegationsArgs);

        return communicationHandler.performRequest(requestObject, FindVestingDelegationsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listVestingDelegationExpirationsArgs
     * @return ListVestingDelegationExpirationsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListVestingDelegationExpirationsReturn listVestingDelegationExpirations(
            CommunicationHandler communicationHandler,
            ListVestingDelegationExpirationsArgs listVestingDelegationExpirationsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_VESTING_DELEGATION_EXPIRATIONS, listVestingDelegationExpirationsArgs);

        return communicationHandler.performRequest(requestObject, ListVestingDelegationExpirationsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findVestingDelegationExpirationsArgs
     * @return FindVestingDelegationExpirationsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindVestingDelegationExpirationsReturn findVestingDelegationExpirations(
            CommunicationHandler communicationHandler,
            FindVestingDelegationExpirationsArgs findVestingDelegationExpirationsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_VESTING_DELEGATION_EXPIRATIONS, findVestingDelegationExpirationsArgs);

        return communicationHandler.performRequest(requestObject, FindVestingDelegationExpirationsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listSbdConversionRequestsArgs
     * @return ListSbdConversionRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListSbdConversionRequestsReturn listSbdConversionRequests(CommunicationHandler communicationHandler,
            ListSbdConversionRequestsArgs listSbdConversionRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_SBD_CONVERSION_REQUESTS, listSbdConversionRequestsArgs);

        return communicationHandler.performRequest(requestObject, ListSbdConversionRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findSbdConversionRequestsArgs
     * @return FindSbdConversionRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindSbdConversionRequestsReturn findSbdConversionRequests(CommunicationHandler communicationHandler,
            FindSbdConversionRequestsArgs findSbdConversionRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_SBD_CONVERSION_REQUESTS, findSbdConversionRequestsArgs);

        return communicationHandler.performRequest(requestObject, FindSbdConversionRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listDeclineVotingRightsRequestsArgs
     * @return ListDeclineVotingRightsRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListDeclineVotingRightsRequestsReturn listDeclineVotingRightsRequests(
            CommunicationHandler communicationHandler,
            ListDeclineVotingRightsRequestsArgs listDeclineVotingRightsRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.LIST_DECLINE_VOTING_RIGHTS_REQUESTS, listDeclineVotingRightsRequestsArgs);

        return communicationHandler.performRequest(requestObject, ListDeclineVotingRightsRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findDeclineVotingRightsRequestsArgs
     * @return FindDeclineVotingRightsRequestsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindDeclineVotingRightsRequestsReturn findDeclineVotingRightsRequests(
            CommunicationHandler communicationHandler,
            FindDeclineVotingRightsRequestsArgs findDeclineVotingRightsRequestsArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.FIND_DECLINE_VOTING_RIGHTS_REQUESTS, findDeclineVotingRightsRequestsArgs);

        return communicationHandler.performRequest(requestObject, FindDeclineVotingRightsRequestsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listCommentsArgs
     * @return ListCommentsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListCommentsReturn listComments(CommunicationHandler communicationHandler,
            ListCommentsArgs listCommentsArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.LIST_COMMENTS,
                listCommentsArgs);

        return communicationHandler.performRequest(requestObject, ListCommentsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findCommentsArgs
     * @return FindCommentsReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindCommentsReturn findComments(CommunicationHandler communicationHandler,
            FindCommentsArgs findCommentsArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.FIND_COMMENTS,
                findCommentsArgs);

        return communicationHandler.performRequest(requestObject, FindCommentsReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listVotesArgs
     * @return ListVotesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListVotesReturn listVotes(CommunicationHandler communicationHandler, ListVotesArgs listVotesArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.LIST_VOTES,
                listVotesArgs);

        return communicationHandler.performRequest(requestObject, ListVotesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findVotesArgs
     * @return FindVotesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindVotesReturn findVotes(CommunicationHandler communicationHandler, FindVotesArgs findVotesArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.FIND_VOTES,
                findVotesArgs);

        return communicationHandler.performRequest(requestObject, FindVotesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param listLimitOrdersArgs
     * @return ListLimitOrdersReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ListLimitOrdersReturn listLimitOrders(CommunicationHandler communicationHandler,
            ListLimitOrdersArgs listLimitOrdersArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.LIST_LIMIT_ORDERS,
                listLimitOrdersArgs);

        return communicationHandler.performRequest(requestObject, ListLimitOrdersReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param findLimitOrdersArgs
     * @return FindLimitOrdersReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static FindLimitOrdersReturn findLimitOrders(CommunicationHandler communicationHandler,
            FindLimitOrdersArgs findLimitOrdersArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.FIND_LIMIT_ORDERS,
                findLimitOrdersArgs);

        return communicationHandler.performRequest(requestObject, FindLimitOrdersReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param getOrderBookArgs
     * @return GetOrderBookReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static GetOrderBookReturn getOrderBook(CommunicationHandler communicationHandler,
            GetOrderBookArgs getOrderBookArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_ORDER_BOOK,
                getOrderBookArgs);

        return communicationHandler.performRequest(requestObject, GetOrderBookReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param getTransactionHexArgs
     * @return GetTransactionHexReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static GetTransactionHexReturn getTransactionHex(CommunicationHandler communicationHandler,
            GetTransactionHexArgs getTransactionHexArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.GET_TRANSACTION_HEX,
                getTransactionHexArgs);

        return communicationHandler.performRequest(requestObject, GetTransactionHexReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param getRequiredSignaturesArgs
     * @return GetRequiredSignaturesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static GetRequiredSignaturesReturn getRequiredSignatures(CommunicationHandler communicationHandler,
            GetRequiredSignaturesArgs getRequiredSignaturesArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_REQUIRED_SIGNATURES, getRequiredSignaturesArgs);

        return communicationHandler.performRequest(requestObject, GetRequiredSignaturesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param getPotentialSignaturesArgs
     * @return GetPotentialSignaturesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static GetPotentialSignaturesReturn getPotentialSignatures(CommunicationHandler communicationHandler,
            GetPotentialSignaturesArgs getPotentialSignaturesArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_POTENTIAL_SIGNATURES, getPotentialSignaturesArgs);

        return communicationHandler.performRequest(requestObject, GetPotentialSignaturesReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param verifyAuthorityArgs
     * @return VerifyAuthorityReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static VerifyAuthorityReturn verifyAuthority(CommunicationHandler communicationHandler,
            VerifyAuthorityArgs verifyAuthorityArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.VERIFY_AUTHORITY,
                verifyAuthorityArgs);

        return communicationHandler.performRequest(requestObject, VerifyAuthorityReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param verifyAccountAuthorityArgs
     * @return VerifyAccountAuthorityReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static VerifyAccountAuthorityReturn verifyAccountAuthority(CommunicationHandler communicationHandler,
            VerifyAccountAuthorityArgs verifyAccountAuthorityArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.VERIFY_ACCOUNT_AUTHORITY, null);

        return communicationHandler.performRequest(requestObject, VerifyAccountAuthorityReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @param verifySignaturesArgs
     * @return VerifySignaturesReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static VerifySignaturesReturn verifySignatures(CommunicationHandler communicationHandler,
            VerifySignaturesArgs verifySignaturesArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API, RequestMethod.VERIFY_SIGNATURES,
                null);

        return communicationHandler.performRequest(requestObject, VerifySignaturesReturn.class).get(0);
    }

    /**
     * Only availabel if "#ifdef STEEM_ENABLE_SMT"
     * 
     * @param communicationHandler
     * @return GetSmtNextIdentifierReturn
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static GetSmtNextIdentifierReturn getSmtNextIdentifier(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.DATABASE_API,
                RequestMethod.GET_SMT_NEXT_IDENTIFIER, null);

        return communicationHandler.performRequest(requestObject, GetSmtNextIdentifierReturn.class).get(0);
    }
}

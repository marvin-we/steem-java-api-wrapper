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
package eu.bittrade.libs.steemj.plugins.apis.market.history;

import java.security.InvalidParameterException;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetMarketHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetMarketHistoryBucketsReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetMarketHistoryReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetRecentTradesArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetRecentTradesReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetTickerReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetTradeHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetTradeHistoryReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetVolumeReturn;

/**
 * This class implements the market history api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketHistoryApi {
    /** Add a private constructor to hide the implicit public one. */
    private MarketHistoryApi() {
    }

    /**
     * Use this method to receive statistic values of the internal SBD:STEEM
     * market for the last 24 hours.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @return The market ticker for the internal SBD:STEEM market.
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
    public static GetTickerReturn getTicker(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.MARKET_HISTORY_API,
                RequestMethod.GET_TICKER.BROADCAST_BLOCK, null);

        return communicationHandler.performRequest(requestObject, GetTickerReturn.class).get(0);
    }

    /**
     * Use this method to get the SBD and Steem volume that has been traded in
     * the past 24 hours at the internal SBD:STEEM market.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @return The market volume for the past 24 hours.
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
    public static GetVolumeReturn getVolume(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.MARKET_HISTORY_API, RequestMethod.GET_VOLUME,
                null);

        return communicationHandler.performRequest(requestObject, GetVolumeReturn.class).get(0);
    }

    /**
     * Use this method to receive the current order book of the internal
     * SBD:STEEM market.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param limit
     *            The number of orders to have on each side of the order book.
     *            Maximum is 500.
     * @return Returns the current order book for the internal SBD:STEEM market.
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
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public static GetOrderBookReturn getOrderBook(CommunicationHandler communicationHandler,
            GetOrderBookArgs getOrderBookArgs) throws SteemCommunicationException, SteemResponseException {
        if (getOrderBookArgs.getLimit().longValue() < 0 || getOrderBookArgs.getLimit().longValue() > 500) {
            throw new InvalidParameterException("The limit can't be less than 0 or greater than 500.");
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.MARKET_HISTORY_API, RequestMethod.GET_ORDER_BOOK,
                getOrderBookArgs);

        return communicationHandler.performRequest(requestObject, GetOrderBookReturn.class).get(0);
    }

    /**
     * Use this method to get the trade history of the internal SBD:STEEM market
     * between the defined <code>start</code> and <code>end</code> time.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param start
     *            The start time of the trade history.
     * @param end
     *            The end time of the trade history.
     * @param limit
     *            The number of trades to return. Maximum is 1000.
     * @return A list of completed trades.
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
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public static GetTradeHistoryReturn getTradeHistory(CommunicationHandler communicationHandler,
            GetTradeHistoryArgs getTradeHistoryArgs) throws SteemCommunicationException, SteemResponseException {
        if (getTradeHistoryArgs.getLimit().longValue() < 0 || getTradeHistoryArgs.getLimit().longValue() > 1000) {
            throw new InvalidParameterException("The limit can't be less than 0 or greater than 1000.");
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.MARKET_HISTORY_API,
                RequestMethod.GET_TRADE_HISTORY, getTradeHistoryArgs);

        return communicationHandler.performRequest(requestObject, GetTradeHistoryReturn.class).get(0);
    }

    /**
     * Use this method to request the most recent trades for the internal
     * SBD:STEEM market. The number of results is limited by the
     * <code>limit</code> parameter.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param limit
     *            The number of trades to return. Maximum is 1000.
     * @return A list of completed trades.
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
     * @throws InvalidParameterException
     *             If the limit is less than 0 or greater than 500.
     */
    public static GetRecentTradesReturn getRecentTrades(CommunicationHandler communicationHandler,
            GetRecentTradesArgs getRecentTradesArgs) throws SteemCommunicationException, SteemResponseException {
        if (getRecentTradesArgs.getLimit().longValue() < 0 || getRecentTradesArgs.getLimit().longValue() > 1000) {
            throw new InvalidParameterException("The limit can't be less than 0 or greater than 500.");
        }

        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.MARKET_HISTORY_API,
                RequestMethod.GET_RECENT_TRADES, getRecentTradesArgs);

        return communicationHandler.performRequest(requestObject, GetRecentTradesReturn.class).get(0);
    }

    /**
     * Returns the market history for the internal SBD:STEEM market.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param bucketSeconds
     *            The size of buckets the history is broken into. The bucket
     *            size must be configured in the plugin options.
     * @param start
     *            The start time to get market history.
     * @param end
     *            The end time to get market history.
     * @return A list of market history
     *         {@link eu.bittrade.libs.steemj.plugins.apis.market.history.models.Bucket
     *         Bucket}s.
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
    public static GetMarketHistoryReturn getMarketHistory(CommunicationHandler communicationHandler,
            GetMarketHistoryArgs getMarketHistoryArgs) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(null, RequestMethod.GET_MARKET_HISTORY, getMarketHistoryArgs);

        return communicationHandler.performRequest(requestObject, GetMarketHistoryReturn.class).get(0);
    }

    /**
     * Use this method to receive the bucket seconds being tracked by the node.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @return Returns The bucket seconds being tracked by the node.
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
    public static GetMarketHistoryBucketsReturn getMarketHistoryBuckets(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.MARKET_HISTORY_API,
                RequestMethod.GET_MARKET_HISTORY_BUCKETS, null);

        return communicationHandler.performRequest(requestObject, GetMarketHistoryBucketsReturn.class).get(0);
    }
}

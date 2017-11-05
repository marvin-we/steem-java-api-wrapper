package eu.bittrade.libs.steemj.apis.market.history;

import java.security.InvalidParameterException;
import java.util.List;

import eu.bittrade.libs.steemj.apis.market.history.model.Bucket;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketTicker;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketTrade;
import eu.bittrade.libs.steemj.apis.market.history.model.MarketVolume;
import eu.bittrade.libs.steemj.apis.market.history.model.OrderBook;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

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
    public static MarketTicker getTicker(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_TICKER);
        requestObject.setSteemApi(SteemApiType.MARKET_HISTORY_API);

        Object[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, MarketTicker.class).get(0);
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
    public static MarketVolume getVolume(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_VOLUME);
        requestObject.setSteemApi(SteemApiType.MARKET_HISTORY_API);

        Object[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, MarketVolume.class).get(0);
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
    public static OrderBook getOrderBook(CommunicationHandler communicationHandler, short limit)
            throws SteemCommunicationException, SteemResponseException {
        if (limit < 0 || limit > 500) {
            throw new InvalidParameterException("The limit can't be less than 0 or greater than 500.");
        }

        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_ORDER_BOOK);
        requestObject.setSteemApi(SteemApiType.MARKET_HISTORY_API);

        Object[] parameters = { limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, OrderBook.class).get(0);
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
    public static List<MarketTrade> getTradeHistory(CommunicationHandler communicationHandler, TimePointSec start,
            TimePointSec end, short limit) throws SteemCommunicationException, SteemResponseException {
        if (limit < 0 || limit > 1000) {
            throw new InvalidParameterException("The limit can't be less than 0 or greater than 1000.");
        }

        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_TRADE_HISTORY);
        requestObject.setSteemApi(SteemApiType.MARKET_HISTORY_API);

        Object[] parameters = { start, end, limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, MarketTrade.class);
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
    public static List<MarketTrade> getRecentTrades(CommunicationHandler communicationHandler, short limit)
            throws SteemCommunicationException, SteemResponseException {
        if (limit < 0 || limit > 1000) {
            throw new InvalidParameterException("The limit can't be less than 0 or greater than 500.");
        }

        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_RECENT_TRADES);
        requestObject.setSteemApi(SteemApiType.MARKET_HISTORY_API);

        Object[] parameters = { limit };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, MarketTrade.class);
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
     *         {@link eu.bittrade.libs.steemj.apis.market.history.model.Bucket
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
    public static List<Bucket> getMarketHistory(CommunicationHandler communicationHandler, long bucketSeconds,
            TimePointSec start, TimePointSec end) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_MARKET_HISTORY);
        requestObject.setSteemApi(SteemApiType.MARKET_HISTORY_API);

        Object[] parameters = { bucketSeconds, start, end };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Bucket.class);
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
    public static List<Integer> getMarketHistoryBuckets(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_MARKET_HISTORY_BUCKETS);
        requestObject.setSteemApi(SteemApiType.MARKET_HISTORY_API);

        Object[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Integer.class);
    }
}

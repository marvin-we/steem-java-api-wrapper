package eu.bittrade.libs.steemj.plugins.apis.market.history;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import java.math.BigDecimal;
import java.util.List;

import org.joou.UInteger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.Bucket;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetMarketHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetOrderBookReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetRecentTradesArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetTickerReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetTradeHistoryArgs;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.GetVolumeReturn;
import eu.bittrade.libs.steemj.plugins.apis.market.history.models.MarketTrade;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.apis.follow.FollowApi FollowApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketHistoryApiIT extends BaseIT {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @BeforeClass
    public static void init() throws SteemCommunicationException, SteemResponseException {
        setupIntegrationTestEnvironment();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi#getTicker(CommunicationHandler)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testGetTicker() throws SteemCommunicationException, SteemResponseException {
        GetTickerReturn marketTicker = MarketHistoryApi.getTicker(COMMUNICATION_HANDLER);

        assertThat(marketTicker.getHighestBid(), greaterThan(0.0));
        assertThat(marketTicker.getLatest(), greaterThan(0.0));
        assertThat(marketTicker.getLowestAsk(), greaterThan(0.0));
        assertThat(marketTicker.getPercentChange(), allOf(greaterThan(-100.0), lessThan(100.0)));
        assertThat(marketTicker.getSbdVolume().getSymbol(), equalTo(AssetSymbolType.SBD));
        assertThat(marketTicker.getSteemVolume().toReal(), greaterThan(BigDecimal.valueOf(1.0)));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi#getVolume(CommunicationHandler)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testGetVolume() throws SteemCommunicationException, SteemResponseException {
        GetVolumeReturn marketVolume = MarketHistoryApi.getVolume(COMMUNICATION_HANDLER);

        assertThat(marketVolume.getSbdVolume().toReal(), greaterThan(BigDecimal.valueOf(0.0)));
        assertThat(marketVolume.getSbdVolume().getSymbol(), equalTo(AssetSymbolType.SBD));
        assertThat(marketVolume.getSteemVolume().toReal(), greaterThan(BigDecimal.valueOf(0.0)));
        assertThat(marketVolume.getSteemVolume().getSymbol(), equalTo(AssetSymbolType.STEEM));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi#getOrderBook(CommunicationHandler, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testGetOrderBook() throws SteemCommunicationException, SteemResponseException {
        GetOrderBookReturn orderBook = MarketHistoryApi.getOrderBook(COMMUNICATION_HANDLER,
                new GetOrderBookArgs(UInteger.valueOf(50)));

        assertThat(orderBook.getAsks().size(), lessThanOrEqualTo(50));
        assertThat(orderBook.getAsks().get(0).getRealPrice(), greaterThan(0.0));
        assertThat(orderBook.getAsks().get(0).getSbd(), greaterThan(0L));
        assertThat(orderBook.getAsks().get(0).getSteem(), greaterThan(0L));
        assertThat(orderBook.getBids().size(), lessThanOrEqualTo(50));
        assertThat(orderBook.getBids().get(0).getRealPrice(), greaterThan(0.0));
        assertThat(orderBook.getBids().get(0).getSbd(), greaterThan(0L));
        assertThat(orderBook.getBids().get(0).getSteem(), greaterThan(0L));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi#getTradeHistory(CommunicationHandler, TimePointSec, TimePointSec, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testGetTradeHistory() throws SteemCommunicationException, SteemResponseException {
        List<MarketTrade> marketTrades = MarketHistoryApi
                .getTradeHistory(COMMUNICATION_HANDLER, new GetTradeHistoryArgs(new TimePointSec(1497112817),
                        new TimePointSec(System.currentTimeMillis()), UInteger.valueOf(10)))
                .getTrades();

        assertThat(marketTrades.size(), lessThanOrEqualTo(10));
        assertThat(marketTrades.get(0).getDate().getDateTimeAsTimestamp(), greaterThanOrEqualTo(1441903217L));
        assertThat(marketTrades.get(0).getCurrentPays().getSymbol(),
                isOneOf(AssetSymbolType.STEEM, AssetSymbolType.SBD));
        assertThat(marketTrades.get(0).getOpenPays().getSymbol(), isOneOf(AssetSymbolType.STEEM, AssetSymbolType.SBD));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi#getRecentTrades(CommunicationHandler, short)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testGetRecentTrades() throws SteemCommunicationException, SteemResponseException {
        List<MarketTrade> marketTrades = MarketHistoryApi
                .getRecentTrades(COMMUNICATION_HANDLER, new GetRecentTradesArgs(UInteger.valueOf(30))).getTrades();

        assertThat(marketTrades.size(), lessThanOrEqualTo(30));
        assertThat(marketTrades.get(0).getDate().getDateTimeAsTimestamp(), greaterThanOrEqualTo(1441903217L));
        assertThat(marketTrades.get(0).getCurrentPays().getSymbol(),
                isOneOf(AssetSymbolType.STEEM, AssetSymbolType.SBD));
        assertThat(marketTrades.get(0).getOpenPays().getSymbol(), isOneOf(AssetSymbolType.STEEM, AssetSymbolType.SBD));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi#getMarketHistory(CommunicationHandler, long, TimePointSec, TimePointSec)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testGetMarketHistory() throws SteemCommunicationException, SteemResponseException {
        List<Bucket> marketHistory = MarketHistoryApi
                .getMarketHistory(COMMUNICATION_HANDLER, new GetMarketHistoryArgs(UInteger.valueOf(3600),
                        new TimePointSec(1504885989), new TimePointSec(System.currentTimeMillis())))
                .getBuckets();

        assertThat(marketHistory.size(), greaterThan(0));
        assertThat(marketHistory.get(0).getCloseSbd(), greaterThan(0L));
        assertThat(marketHistory.get(0).getCloseSteem(), greaterThan(0L));
        assertThat(marketHistory.get(0).getHighSbd(), greaterThan(0L));
        assertThat(marketHistory.get(0).getHighSteem(), greaterThan(0L));
        assertThat(marketHistory.get(0).getId(), greaterThan(0L));
        assertThat(marketHistory.get(0).getLowSbd(), greaterThan(0L));
        assertThat(marketHistory.get(0).getLowSteem(), greaterThan(0L));
        assertThat(marketHistory.get(0).getOpen().getDateTimeAsTimestamp(), greaterThan(1504885989L));
        assertThat(marketHistory.get(0).getOpenSbd(), greaterThan(0L));
        assertThat(marketHistory.get(0).getOpenSteem(), greaterThan(0L));
        assertThat(marketHistory.get(0).getSbdVolume(), greaterThan(0L));
        assertThat(marketHistory.get(0).getSeconds().longValue(), greaterThan(0L));
        assertThat(marketHistory.get(0).getSteemVolume(), greaterThan(0L));
        // Methods.
        assertThat(marketHistory.get(0).calculateHigh(), greaterThan(0.01));
        assertThat(marketHistory.get(0).calculateLow(), greaterThan(0.01));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.market.history.MarketHistoryApi#getMarketHistoryBuckets(CommunicationHandler)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Test
    @Category({ IntegrationTest.class })
    public void testGetMarketHistoryBuckets() throws SteemCommunicationException, SteemResponseException {
        List<UInteger> marketHistoryBuckets = MarketHistoryApi.getMarketHistoryBuckets(COMMUNICATION_HANDLER)
                .getBucketSizes();

        assertThat(marketHistoryBuckets.size(), greaterThan(0));
        assertThat(marketHistoryBuckets.get(0).longValue(), greaterThan(1L));
    }
}

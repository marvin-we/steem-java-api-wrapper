package eu.bittrade.libs.steemj.plugins.market.history;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIntegrationTest;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.TimePointSec;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.plugins.market.history.model.Bucket;
import eu.bittrade.libs.steemj.plugins.market.history.model.MarketTicker;
import eu.bittrade.libs.steemj.plugins.market.history.model.MarketTrade;
import eu.bittrade.libs.steemj.plugins.market.history.model.MarketVolume;
import eu.bittrade.libs.steemj.plugins.market.history.model.OrderBook;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.market.history.MarketHistoryApi
 * MarketHistoryApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketHistoryApiIT extends BaseIntegrationTest {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    @BeforeClass
    public static void init() throws SteemCommunicationException {
        setupIntegrationTestEnvironment();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    @Test
    @Category({ IntegrationTest.class })
    public void testGetTicker() throws SteemCommunicationException {
        MarketTicker marketTicker = MarketHistoryApi.getTicker(COMMUNICATION_HANDLER);

        // TODO: Assert
        System.out.print(marketTicker);
    }

    @Test
    @Category({ IntegrationTest.class })
    public void testGetVolume() throws SteemCommunicationException {
        MarketVolume marketVolume = MarketHistoryApi.getVolume(COMMUNICATION_HANDLER);

        // TODO: Assert
        System.out.print(marketVolume);
    }

    @Test
    @Category({ IntegrationTest.class })
    public void testGetOrderBook() throws SteemCommunicationException {
        OrderBook orderBook = MarketHistoryApi.getOrderBook(COMMUNICATION_HANDLER, (short) 50);

        // TODO: Assert
        System.out.print(orderBook);
    }

    @Test
    @Category({ IntegrationTest.class })
    public void testGetTradeHistory() throws SteemCommunicationException {
        List<MarketTrade> marketTrades = MarketHistoryApi.getTradeHistory(COMMUNICATION_HANDLER,
                new TimePointSec(1504885989), new TimePointSec(1505058789), (short) 10);

        // TODO: Assert
        System.out.print(marketTrades);
    }

    @Test
    @Category({ IntegrationTest.class })
    public void testGetRecentTrades() throws SteemCommunicationException {
        List<MarketTrade> marketTrades = MarketHistoryApi.getRecentTrades(COMMUNICATION_HANDLER, (short) 30);

        // TODO: Assert
        System.out.print(marketTrades);
    }

    @Test
    @Category({ IntegrationTest.class })
    public void testGetMarketHistory() throws SteemCommunicationException {
        List<Bucket> marketHistory = MarketHistoryApi.getMarketHistory(COMMUNICATION_HANDLER, 3600,
                new TimePointSec(1504885989), new TimePointSec(1505058789));

        // TODO: Assert
        System.out.print(marketHistory);
    }

    @Test
    @Category({ IntegrationTest.class })
    public void testGetMarketHistoryBuckets() throws SteemCommunicationException {
        List<Integer> marketHistoryBuckets = MarketHistoryApi.getMarketHistoryBuckets(COMMUNICATION_HANDLER);

        // TODO: Assert
        System.out.print(marketHistoryBuckets);
    }
}

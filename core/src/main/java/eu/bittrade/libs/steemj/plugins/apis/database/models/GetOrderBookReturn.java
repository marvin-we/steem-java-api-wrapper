package eu.bittrade.libs.steemj.plugins.apis.database.models;

/**
 * This class represents a Steem "get_order_book_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOrderBookReturn extends OrderBook {
    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetOrderBookReturn() {
    }
}

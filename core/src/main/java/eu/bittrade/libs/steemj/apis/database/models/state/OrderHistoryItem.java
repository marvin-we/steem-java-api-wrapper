package eu.bittrade.libs.steemj.apis.database.models.state;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.apis.database.enums.OrderType;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * This class represents a Steem "order_history_item" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class OrderHistoryItem {
    @JsonProperty("time")
    private TimePointSec time;
    @JsonProperty("type")
    private OrderType type;
    @JsonProperty("sbd_quantity")
    private Asset sbdQuantity;
    @JsonProperty("steem_quantity")
    private Asset steemQuantity;
    @JsonProperty("real_price")
    private double realPrice;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected OrderHistoryItem() {
    }

    /**
     * @return the time
     */
    public TimePointSec getTime() {
        return time;
    }

    /**
     * @return the type
     */
    public OrderType getType() {
        return type;
    }

    /**
     * @return the sbdQuantity
     */
    public Asset getSbdQuantity() {
        return sbdQuantity;
    }

    /**
     * @return the steemQuantity
     */
    public Asset getSteemQuantity() {
        return steemQuantity;
    }

    /**
     * @return the realPrice
     */
    public double getRealPrice() {
        return realPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

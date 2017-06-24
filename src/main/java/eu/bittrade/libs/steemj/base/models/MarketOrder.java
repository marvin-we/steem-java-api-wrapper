package eu.bittrade.libs.steemj.base.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MarketOrder {
    private String created;
    @JsonProperty("order_price")
    private List<Price> orderPrice;
    @JsonProperty("real_price")
    private String realPrice;
    private int steem;
    private int sdb;
    /* TODO
    price                order_price;
    double               real_price; // dollars per steem
    share_type           steem;
    share_type           sbd;
    fc::time_point_sec   created;
*/
    public String getCreated() {
        return created;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public List<Price> getOrderPrice() {
        return orderPrice;
    }

    public int getSteem() {
        return steem;
    }

    public int getSdb() {
        return sdb;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

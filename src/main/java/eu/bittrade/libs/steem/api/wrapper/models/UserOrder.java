package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class UserOrder {
    private long id;
    private String created;
    private String expiration;
    private String seller;
    private long orderid;
    @JsonProperty("for_sale")
    private long forSale;
    @JsonProperty("sell_price")
    private List<Price> sellPrice;
    @JsonProperty("real_price")
    private String realPrice;
    private Boolean rewarded;

    public long getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getSeller() {
        return seller;
    }

    public long getOrderid() {
        return orderid;
    }

    public long getForSale() {
        return forSale;
    }

    public List<Price> getSellPrice() {
        return sellPrice;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public Boolean getRewarded() {
        return rewarded;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

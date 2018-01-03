package eu.bittrade.libs.steemj.plugins.apis.market.history.models;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class implements the Steem "get_order_book_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOrderBookArgs {
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * Create a new {@link GetAccountBandwidthArgs} instance to be passed to the
     * {@link WitnessApi#getAccountBandwidth(CommunicationHandler, GetAccountBandwidthArgs)}
     * method.
     * 
     * @param limit
     */
    @JsonCreator()
    public GetOrderBookArgs(@Nullable @JsonProperty("limit") UInteger limit) {
        this.setLimit(limit);
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(@Nullable UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(500));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

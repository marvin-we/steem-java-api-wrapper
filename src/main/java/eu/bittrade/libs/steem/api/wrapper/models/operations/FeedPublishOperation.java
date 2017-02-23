package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.models.Price;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FeedPublishOperation extends Operation {
    @JsonProperty("publisher")
    private String publisher;
    @JsonProperty("exchange_rate")
    private Price exchangeRate;

    public String getPublisher() {
        return publisher;
    }

    public Price getExchangeRate() {
        return exchangeRate;
    }
}

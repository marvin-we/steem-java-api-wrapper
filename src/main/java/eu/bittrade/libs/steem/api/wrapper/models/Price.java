package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author http://steemit.com/@dez1337
 */
public class Price {
    private String base;
    private String quote;

    public String getBase() {
        return base;
    }

    public String getQuote() {
        return quote;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

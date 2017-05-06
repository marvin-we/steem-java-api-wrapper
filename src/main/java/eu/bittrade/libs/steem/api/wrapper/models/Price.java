package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/asset.hpp">Steem
 * price object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Price {
    private Asset base;
    private Asset quote;

    /**
     * 
     * @return
     */
    public Asset getBase() {
        return base;
    }

    /**
     * 
     * @return
     */
    public Asset getQuote() {
        return quote;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

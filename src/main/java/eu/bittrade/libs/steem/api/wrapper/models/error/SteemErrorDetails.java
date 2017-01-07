package eu.bittrade.libs.steem.api.wrapper.models.error;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemErrorDetails {
    private SteemErrorData data;
    private int code;
    private String message;

    public SteemErrorData getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

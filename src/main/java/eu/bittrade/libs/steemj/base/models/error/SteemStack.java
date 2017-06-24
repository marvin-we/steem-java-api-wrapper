package eu.bittrade.libs.steemj.base.models.error;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemStack {
    private SteemContext context;
    private String format;
    private SteemData data;

    public SteemContext getContext() {
        return context;
    }

    public String getFormat() {
        return format;
    }

    public SteemData getData() {
        return data;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

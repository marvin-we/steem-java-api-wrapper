package eu.bittrade.libs.steem.api.wrapper.models.error;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemContext {
    private String level;
    private String file;
    private int line;
    private String method;
    private String hostname;
    private String threadName;
    private Date timestamp;

    public String getLevel() {
        return level;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public String getMethod() {
        return method;
    }

    public String getHostname() {
        return hostname;
    }

    @JsonProperty("thread_name")
    public String getThreadName() {
        return threadName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

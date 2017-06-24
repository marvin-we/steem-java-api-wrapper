package eu.bittrade.libs.steem.api.wrapper.communication.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * As every response starts with an id and a result element, this wrapper class
 * can carry every kind of responses.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ResponseWrapperDTO<T> {
    private int responseId;
    private List<T> result;

    @JsonCreator
    public ResponseWrapperDTO(@JsonProperty("result") List<T> result) {
        this.result = result;
    }

    @JsonProperty("id")
    public int getResponseId() {
        return responseId;
    }

    public List<T> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

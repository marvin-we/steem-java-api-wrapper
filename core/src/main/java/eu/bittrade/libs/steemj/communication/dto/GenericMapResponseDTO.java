package eu.bittrade.libs.steemj.communication.dto;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * As every response starts with an id and a result element, this wrapper class
 * can carry every kind of responses.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GenericMapResponseDTO<K, V> {
    private int responseId;
    private List<Map<K, V>> result;

    /**
     * Create a new GenericMapResponseDTO object by providing a map of items.
     * 
     * @param result
     *            A list of items.
     */
    @JsonCreator
    public GenericMapResponseDTO(@JsonProperty("result") List<Map<K, V>> result) {
        this.result = result;
    }

    /**
     * Each request is send with a unique id. The Steem node will reply with the
     * same id so it is possible to check which request is answered by this
     * response.
     * 
     * @return The request/response id.
     */
    @JsonProperty("id")
    public int getResponseId() {
        return responseId;
    }

    /**
     * Get the map of returned items or null.
     * 
     * @return The returned items.
     */
    public List<Map<K, V>> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

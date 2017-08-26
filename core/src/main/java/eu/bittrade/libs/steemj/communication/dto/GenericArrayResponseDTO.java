package eu.bittrade.libs.steemj.communication.dto;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to wrap array based responses returned by a Steem node.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GenericArrayResponseDTO<T> {
    private int responseId;
    private List<T> result;

    /**
     * Create a new GenericArrayResponseDTO object by providing a list of items.
     * 
     * @param result
     *            A list of items.
     */
    @JsonCreator
    public GenericArrayResponseDTO(@JsonProperty("result") List<T> result) {
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
     * Get the list of returned items or null.
     * 
     * @return The returned items.
     */
    public List<T> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

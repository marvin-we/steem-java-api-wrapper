package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class implements the Steem "get_ops_in_block_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOpsInBlockReturn {
    @JsonProperty("ops")
    private List<AppliedOperation> operations;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetOpsInBlockReturn() {
    }

    /**
     * Get the list of {@link AppliedOperation AppliedOperations} returned from
     * the Steem Node.
     * 
     * @return A list of {@link AppliedOperation AppliedOperations}.
     */
    public List<AppliedOperation> getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

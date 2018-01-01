package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.operations.Operation;

/**
 * This class implements the Steem "get_ops_in_block_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetOpsInBlockReturn {
    @JsonProperty("ops")
    private ArrayList<Operation> operations;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetOpsInBlockReturn() {
    }

    /**
     * @return the operations
     */
    public ArrayList<Operation> getOperations() {
        return operations;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

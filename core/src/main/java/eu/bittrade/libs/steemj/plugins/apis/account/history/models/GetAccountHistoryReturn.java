package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.bittrade.libs.steemj.plugins.apis.account.history.models.deserializer.AppliedOperationHashMapDeserializer;

/**
 * This class implements the Steem "get_account_history_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetAccountHistoryReturn {
    @JsonProperty("history")
    @JsonDeserialize(using = AppliedOperationHashMapDeserializer.class)
    private Map<UInteger, AppliedOperation> history;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private GetAccountHistoryReturn() {
    }

    /**
     * @return the history
     */
    public Map<UInteger, AppliedOperation> getHistory() {
        return history;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}

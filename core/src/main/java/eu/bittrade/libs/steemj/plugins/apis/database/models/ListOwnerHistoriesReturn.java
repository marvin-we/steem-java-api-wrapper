package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "list_owner_histories_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListOwnerHistoriesReturn {
    @JsonProperty("owner_auths")
    private List<OwnerAuthorityHistory> ownerAuths;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ListOwnerHistoriesReturn() {
    }

    /**
     * @return the ownerAuths
     */
    public List<OwnerAuthorityHistory> getOwnerAuths() {
        return ownerAuths;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

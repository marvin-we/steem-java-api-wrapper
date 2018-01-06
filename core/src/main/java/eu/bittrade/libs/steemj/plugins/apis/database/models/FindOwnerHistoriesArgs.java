package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "find_owner_histories_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindOwnerHistoriesArgs {
    @JsonProperty("owner")
    private AccountName owner;

    /**
     * 
     * @param owner
     */
    @JsonCreator
    public FindOwnerHistoriesArgs(@JsonProperty("owner") AccountName owner) {
        this.setOwner(owner);
    }

    /**
     * @return the owner
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(AccountName owner) {
        this.owner = SteemJUtils.setIfNotNull(owner, "The owner needs to be provided.");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

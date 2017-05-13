package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;

public class CustomBinaryOperation extends Operation {
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_owner_auths")
    private List<AccountName> requiredOwnerAuths;
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_active_auths")
    private List<AccountName> requiredActiveAuths;
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_posting_auths")
    private List<AccountName> requiredPostingAuths;
    // Original type is vector< authority >.
    @JsonProperty("required_auths")
    private List<Authority> requiredAuths;
    private String id;
    private List<Character> data;

    public CustomBinaryOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the requiredOwnerAuths
     */
    public List<AccountName> getRequiredOwnerAuths() {
        return requiredOwnerAuths;
    }

    /**
     * @param requiredOwnerAuths
     *            the requiredOwnerAuths to set
     */
    public void setRequiredOwnerAuths(List<AccountName> requiredOwnerAuths) {
        this.requiredOwnerAuths = requiredOwnerAuths;
    }

    /**
     * @return the requiredActiveAuths
     */
    public List<AccountName> getRequiredActiveAuths() {
        return requiredActiveAuths;
    }

    /**
     * @param requiredActiveAuths
     *            the requiredActiveAuths to set
     */
    public void setRequiredActiveAuths(List<AccountName> requiredActiveAuths) {
        this.requiredActiveAuths = requiredActiveAuths;
    }

    /**
     * @return the requiredPostingAuths
     */
    public List<AccountName> getRequiredPostingAuths() {
        return requiredPostingAuths;
    }

    /**
     * @param requiredPostingAuths
     *            the requiredPostingAuths to set
     */
    public void setRequiredPostingAuths(List<AccountName> requiredPostingAuths) {
        this.requiredPostingAuths = requiredPostingAuths;
    }

    /**
     * @return the requiredAuths
     */
    public List<Authority> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * @param requiredAuths
     *            the requiredAuths to set
     */
    public void setRequiredAuths(List<Authority> requiredAuths) {
        this.requiredAuths = requiredAuths;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * The id must be less than 32 characters long.
     * 
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the data
     */
    public List<Character> getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(List<Character> data) {
        this.data = data;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

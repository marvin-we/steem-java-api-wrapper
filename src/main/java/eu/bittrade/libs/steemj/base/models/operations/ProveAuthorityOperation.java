package eu.bittrade.libs.steemj.base.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class represents the Steem "prove_authority_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ProveAuthorityOperation extends Operation {
    private AccountName challenged;
    @JsonProperty("require_owner")
    private Boolean requireOwner;

    /**
     * Create a new prove authority operation.
     */
    public ProveAuthorityOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
        // Set default values:
        this.setRequireOwner(false);
    }

    /**
     * @return the challenged
     */
    public AccountName getChallenged() {
        return challenged;
    }

    /**
     * @param challenged
     *            the challenged to set
     */
    public void setChallenged(AccountName challenged) {
        this.challenged = challenged;
    }

    /**
     * @return the requireOwner
     */
    public Boolean getRequireOwner() {
        return requireOwner;
    }

    /**
     * @param requireOwner
     *            the requireOwner to set
     */
    public void setRequireOwner(Boolean requireOwner) {
        this.requireOwner = requireOwner;
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

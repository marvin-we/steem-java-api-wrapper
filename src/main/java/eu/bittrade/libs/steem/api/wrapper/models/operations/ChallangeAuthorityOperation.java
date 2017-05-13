package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

public class ChallangeAuthorityOperation extends Operation {
    private AccountName challenger;
    private AccountName challenged;
    @JsonProperty("require_owner")
    private Boolean requireOwner;

    public ChallangeAuthorityOperation() {
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the challenger
     */
    public AccountName getChallenger() {
        return challenger;
    }

    /**
     * @param challenger
     *            the challenger to set
     */
    public void setChallenger(AccountName challenger) {
        this.challenger = challenger;
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

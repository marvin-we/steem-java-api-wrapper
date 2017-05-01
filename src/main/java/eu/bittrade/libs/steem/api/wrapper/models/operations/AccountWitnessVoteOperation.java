package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessVoteOperation extends Operation {
    @JsonProperty("account")
    private String[] account;
    @JsonProperty("witness")
    private String[] witness;
    @JsonProperty("approve")
    private Boolean approve;

    public AccountWitnessVoteOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String[] getAccount() {
        return account;
    }

    public String[] getWitness() {
        return witness;
    }

    public Boolean getApprove() {
        return approve;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}

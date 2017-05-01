package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class InterestOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("interest")
    private String interest;

    public InterestOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getOwner() {
        return owner;
    }

    public String getInterest() {
        return interest;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}

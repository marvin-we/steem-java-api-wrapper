package eu.bittrade.libs.steemj.base.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class represents a Steem "interest_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class InterestOperation extends Operation {
    @JsonProperty("owner")
    private AccountName owner;
    @JsonProperty("interest")
    private Asset interest;

    /**
     * Create a new interest operation.
     */
    public InterestOperation() {
        super(true);
    }
    
    /**
     * 
     * @return
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * 
     * @return
     */
    public Asset getInterest() {
        return interest;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // The byte representation is not needed for virtual operations as we
        // can't broadcast them.
        return new byte[0];
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

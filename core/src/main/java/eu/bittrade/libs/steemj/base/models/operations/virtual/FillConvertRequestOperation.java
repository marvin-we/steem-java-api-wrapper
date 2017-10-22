package eu.bittrade.libs.steemj.base.models.operations.virtual;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;

/**
 * This class represents a Steem "fill_convert_request_operation" object.
 * 
 * This operation type occurs if a "Convert to Steem" request has been fulfilled
 * by the blockchain.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FillConvertRequestOperation extends Operation {
    @JsonProperty("owner")
    private AccountName owner;
    // Original type is uint32_t so we have to use long here.
    @JsonProperty("requestid")
    private long requestId;
    @JsonProperty("amount_in")
    private Asset amountIn;
    @JsonProperty("amount_out")
    private Asset amountOut;

    /**
     * This operation is a virtual one and can only be created by the blockchain
     * itself. Due to that, this constructor is private.
     */
    private FillConvertRequestOperation() {
        super(true);
    }

    /**
     * Get the owner of this conversion request.
     * 
     * @return The owner as an AccountName instance.
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * Get the id of this request.
     * 
     * @return The id of this request.
     */
    public long getRequestId() {
        return requestId;
    }

    /**
     * Get the amount and the type of the currency that has been converted
     * within this operation.
     * 
     * @return The source asset.
     */
    public Asset getAmountIn() {
        return amountIn;
    }

    /**
     * Get the amount and the type of the target currency.
     * 
     * @return The target asset.
     */
    public Asset getAmountOut() {
        return amountOut;
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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        // A virtual operation can't be created by the user, therefore it also
        // does not require any authority.
        return null;
    }

    @Override
    public void validate(ValidationType validationType) {
        // There is no need to validate virtual operations.
    }
}

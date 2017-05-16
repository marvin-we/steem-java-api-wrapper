package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
 * This class represents the Steem "convert_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ConvertOperation extends Operation {
    @JsonProperty("owner")
    private AccountName owner;
    // Original type is uint32_t so we have to use long here.
    @JsonProperty("requestid")
    private long requestId;
    @JsonProperty("amount")
    private Asset amount;

    /**
     * This operation instructs the blockchain to start a conversion between
     * STEEM and SBD, The funds are deposited after STEEMIT_CONVERSION_DELAY.
     */
    public ConvertOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public AccountName getOwner() {
        return owner;
    }

    public void setOwner(AccountName owner) {
        this.owner = owner;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Asset getAmount() {
        return amount;
    }

    public void setAmount(Asset amount) {
        this.amount = amount;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedConvertOperation = new ByteArrayOutputStream()) {
            serializedConvertOperation
                    .write(SteemUtils.transformIntToVarIntByteArray(OperationType.CONVERT_OPERATION.ordinal()));
            serializedConvertOperation.write(this.getOwner().toByteArray());
            serializedConvertOperation.write(SteemUtils.transformIntToByteArray((int) this.getRequestId()));
            serializedConvertOperation.write(this.getAmount().toByteArray());

            return serializedConvertOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

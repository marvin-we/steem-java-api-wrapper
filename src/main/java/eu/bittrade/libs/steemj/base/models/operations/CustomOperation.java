package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bitcoinj.core.Utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "custom_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomOperation extends Operation {
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_auths")
    private List<AccountName> requiredAuths;
    // Original type is uint16_t.
    @JsonProperty("id")
    private short id;
    // Original type is vector< char >.
    @JsonProperty("data")
    private String data;

    /**
     * Create a new custom operation.
     */
    public CustomOperation() {
        super(false);
        // Set default values:
        this.setId(0);
    }

    /**
     * Get the list of account names whose private active keys were required to
     * sign this transaction.
     * 
     * @return The list of account names whose private active keys were required
     */
    public List<AccountName> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * Set the list of account names whose private active keys are required to
     * sign this transaction.
     * 
     * @param requiredAuths
     *            The account names whose private active keys are required.
     */
    public void setRequiredAuths(List<AccountName> requiredAuths) {
        this.requiredAuths = requiredAuths;

        List<ImmutablePair<AccountName, PrivateKeyType>> requiredPrivateKeys = new ArrayList<>();

        for (AccountName accountName : this.getRequiredAuths()) {
            requiredPrivateKeys.add(new ImmutablePair<>(accountName, PrivateKeyType.ACTIVE));
        }

        this.addRequiredPrivateKeyType(requiredPrivateKeys);
    }

    /**
     * Get the id of this operation.
     * 
     * @return The id of this operation.
     */
    public int getId() {
        return Short.toUnsignedInt(this.id);
    }

    /**
     * Set the id of this operation.
     * 
     * @param id
     *            The id to set.
     */
    public void setId(int id) {
        this.id = (short) id;
    }

    /**
     * Get the data that this operation contains. <b>Notice</b> that the
     * original type of this field is "vector&lt; char &gt;" and that its
     * returned as a String.
     * 
     * @return the data The data transfered with this operation.
     */
    public String getData() {
        return data;
    }

    /**
     * Set the data to send with this operation in its HEX representation.
     * 
     * @param data
     *            The data to set.
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCustomOperation = new ByteArrayOutputStream()) {
            serializedCustomOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.CUSTOM_OPERATION.ordinal()));

            serializedCustomOperation.write(SteemJUtils.transformIntToVarIntByteArray(this.getRequiredAuths().size()));

            for (AccountName accountName : this.getRequiredAuths()) {
                serializedCustomOperation.write(accountName.toByteArray());
            }

            serializedCustomOperation.write(SteemJUtils.transformShortToByteArray(this.getId()));

            //SteemJConfig.getInstance().setEncodingCharset(StandardCharsets.US_ASCII);
            //serializedCustomOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getData()));
            
            serializedCustomOperation.write(SteemJUtils.transformLongToVarIntByteArray(Integer.toUnsignedLong(this.getData().length())));
            serializedCustomOperation.write(this.getData().getBytes(StandardCharsets.US_ASCII));
            //for (char singleCharacter : this.getData().toCharArray()) {
            //    serializedCustomOperation.write(SteemJUtils.transformByteToLittleEndian((byte)singleCharacter));
            //}

            // serializedCustomOperation.write(Utils.HEX.decode(this.getData()));

            return serializedCustomOperation.toByteArray();
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

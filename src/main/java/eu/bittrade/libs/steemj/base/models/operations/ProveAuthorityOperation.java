package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
        super(false);
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
     * <b>Notice:</b> The private owner key of this account needs to be stored
     * in the key storage.
     * 
     * @param challenged
     *            the challenged to set
     */
    public void setChallenged(AccountName challenged) {
        this.challenged = challenged;

        // Update the List of required private key types.
        addRequiredPrivateKeyType(challenged, PrivateKeyType.OWNER);
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
        try (ByteArrayOutputStream serializedProveAuthorityOperation = new ByteArrayOutputStream()) {
            serializedProveAuthorityOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.PROVE_AUTHORITY_OPERATION.ordinal()));
            serializedProveAuthorityOperation.write(this.getChallenged().toByteArray());
            serializedProveAuthorityOperation.write(SteemJUtils.transformBooleanToByteArray(this.getRequireOwner()));

            return serializedProveAuthorityOperation.toByteArray();
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

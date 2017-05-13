package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
 * This class represents the Steem "reset_account_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ResetAccountOperation extends Operation {
    @JsonProperty("reset_account")
    private AccountName resetAccount;
    @JsonProperty("account_to_reset")
    private AccountName accountToReset;
    @JsonProperty("new_owner_authority")
    private Authority newOwnerAuthority;

    /**
     * Create a new reset account operation. This operation allows the
     * {@link #resetAccount resetAccount} to change the owner authority of the
     * {@link #accountToReset accountToReset} to the {@link #newOwnerAuthority
     * newOwnerAuthority} after 60 days of inactivity.
     */
    public ResetAccountOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * Get the account that performed the account reset.
     * 
     * @return The account that performed the account reset.
     */
    public AccountName getResetAccount() {
        return resetAccount;
    }

    /**
     * Set the account that will perform the account reset.
     * 
     * @param resetAccount
     *            The account that will perform the account reset.
     */
    public void setResetAccount(AccountName resetAccount) {
        this.resetAccount = resetAccount;
    }

    /**
     * Get the account that has been resettet with this operation.
     * 
     * @return The account that has been resettet with this operation.
     */
    public AccountName getAccountToReset() {
        return accountToReset;
    }

    /**
     * Set the account that will get resettet with this operation.
     * 
     * @param accountToReset
     *            The account that will get resettet with this operation.
     */
    public void setAccountToReset(AccountName accountToReset) {
        this.accountToReset = accountToReset;
    }

    /**
     * Get the new owner authority of the account.
     * 
     * @return The new owner authority of the account.
     */
    public Authority getNewOwnerAuthority() {
        return newOwnerAuthority;
    }

    /**
     * Set the new owner authority of the account.
     * 
     * @param newOwnerAuthority
     *            The new owner authority of the account.
     */
    public void setNewOwnerAuthority(Authority newOwnerAuthority) {
        this.newOwnerAuthority = newOwnerAuthority;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedResetAccountOperation = new ByteArrayOutputStream()) {
            serializedResetAccountOperation
                    .write(SteemUtils.transformIntToVarIntByteArray(OperationType.RESET_ACCOUNT_OPERATION.ordinal()));
            serializedResetAccountOperation.write(this.getResetAccount().toByteArray());
            serializedResetAccountOperation.write(this.getAccountToReset().toByteArray());
            serializedResetAccountOperation.write(this.getNewOwnerAuthority().toByteArray());

            return serializedResetAccountOperation.toByteArray();
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

package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
     * {@link #getResetAccount() resetAccount} to change the owner authority of
     * the {@link #getAccountToReset() accountToReset} to the
     * {@link #getNewOwnerAuthority() newOwnerAuthority} after 60 days of
     * inactivity.
     * 
     * @param resetAccount
     *            Set the account to perform the reset account operation with
     *            (see {@link #setResetAccount(AccountName)}).
     * @param accountToReset
     *            Set the account to reset (see
     *            {@link #setAccountToReset(AccountName)}).
     * @param newOwnerAuthority
     *            Set the new owner authority (see
     *            {@link #setNewOwnerAuthority(Authority)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public ResetAccountOperation(@JsonProperty("reset_account") AccountName resetAccount,
            @JsonProperty("account_to_reset") AccountName accountToReset,
            @JsonProperty("new_owner_authority") Authority newOwnerAuthority) {
        super(false);

        this.setResetAccount(resetAccount);
        this.setAccountToReset(accountToReset);
        this.setNewOwnerAuthority(newOwnerAuthority);
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
     * Set the account that will perform the account reset. <b>Notice:</b> The
     * private active key of this account needs to be stored in the key storage.
     * 
     * @param resetAccount
     *            The account that will perform the account reset.
     * @throws InvalidParameterException
     *             If the <code>resetAccount</code> name is null.
     */
    public void setResetAccount(AccountName resetAccount) {
        this.resetAccount = setIfNotNull(resetAccount, "The reset account can't be null.");
    }

    /**
     * Get the account that has been reseted with this operation.
     * 
     * @return The account that has been reseted with this operation.
     */
    public AccountName getAccountToReset() {
        return accountToReset;
    }

    /**
     * Set the account that will get reseted with this operation.
     * 
     * @param accountToReset
     *            The account that will get reseted with this operation.
     * @throws InvalidParameterException
     *             If the <code>accountToReset</code> is null.
     */
    public void setAccountToReset(AccountName accountToReset) {
        this.accountToReset = setIfNotNull(accountToReset, "The account to reset can't be null.");
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
     * @throws InvalidParameterException
     *             If the <code>newOwnerAuthority</code> is null, impossible or
     *             trivial.
     */
    public void setNewOwnerAuthority(Authority newOwnerAuthority) {
        this.newOwnerAuthority = setIfNotNull(newOwnerAuthority, "The new owner authority can't be null.");
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedResetAccountOperation = new ByteArrayOutputStream()) {
            serializedResetAccountOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.RESET_ACCOUNT_OPERATION.getOrderId()));
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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getResetAccount(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            if (newOwnerAuthority.isImpossible()) {
                throw new InvalidParameterException("The new owner authority can't be impossible.");
            } else if (newOwnerAuthority.getWeightThreshold() < 1) {
                throw new InvalidParameterException("The new owner authority can't be trivial.");
            }
        }
    }
}

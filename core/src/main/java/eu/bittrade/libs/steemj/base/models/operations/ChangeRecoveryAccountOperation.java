package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.annotations.SignatureRequired;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "change_recovery_account_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChangeRecoveryAccountOperation extends Operation {
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @SignatureRequired(type = PrivateKeyType.OWNER)
    @JsonProperty("new_recovery_account")
    private AccountName newRecoveryAccount;
    // Original type is "extension_type" which is an array of "future_extions".
    private List<FutureExtensions> extensions;

    /**
     * Create a new change recovery account operation.
     * 
     * Each account lists another account as their recovery account. The
     * recovery account has the ability to create account_recovery_requests for
     * the account to recover. An account can change their recovery account at
     * any time with a 30 day delay. This delay is to prevent an attacker from
     * changing the recovery account to a malicious account during an attack.
     * These 30 days match the 30 days that an owner authority is valid for
     * recovery purposes.
     *
     * On account creation the recovery account is set either to the creator of
     * the account (The account that pays the creation fee and is a signer on
     * the transaction) or to the empty string if the account was mined. An
     * account with no recovery has the top voted witness as a recovery account,
     * at the time the recover request is created. Note: This does mean the
     * effective recovery account of an account with no listed recovery account
     * can change at any time as witness vote weights. The top voted witness is
     * explicitly the most trusted witness according to stake.
     */
    public ChangeRecoveryAccountOperation() {
        super(false);
    }

    /**
     * Get the account that this operation has been executed for.
     * 
     * @return The account that would be recovered in case of compromise.
     */
    public AccountName getAccountToRecover() {
        return accountToRecover;
    }

    /**
     * Set the account for which the recover account should be changed.
     * 
     * @param accountToRecover
     *            The account that would be recovered in case of compromise.
     */
    public void setAccountToRecover(AccountName accountToRecover) {
        this.accountToRecover = accountToRecover;
    }

    /**
     * Get the recovery account which is the account that is allowed to create a
     * recovery request for the {@link #accountToRecover accountToRecover}.
     * 
     * @return The account that creates the recover request.
     */
    public AccountName getNewRecoveryAccount() {
        return newRecoveryAccount;
    }

    /**
     * Set the recovery account which is the account that is allowed to create a
     * recovery request for the {@link #accountToRecover accountToRecover}.
     * <b>Notice:</b> The private owner key of this account needs to be stored
     * in the key storage.
     * 
     * @param newRecoveryAccount
     *            The account that creates the recover request.
     */
    public void setNewRecoveryAccount(AccountName newRecoveryAccount) {
        this.newRecoveryAccount = newRecoveryAccount;
    }

    /**
     * Get the extensions added to this operation.
     * 
     * @return The extensions added to this operation.
     */
    public List<FutureExtensions> getExtensions() {
        if (extensions == null || extensions.isEmpty()) {
            // Create a new ArrayList that contains an empty FutureExtension so
            // one byte gets added to the signature for sure.
            extensions = new ArrayList<>();
            extensions.add(new FutureExtensions());
        }
        return extensions;
    }

    /**
     * Extensions are currently not used and will be ignored.
     * 
     * @param extensions
     *            The extensions added to this operation.
     */
    public void setExtensions(List<FutureExtensions> extensions) {
        this.extensions = extensions;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedChangeRecoveryAccountOperation = new ByteArrayOutputStream()) {
            serializedChangeRecoveryAccountOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.CHANGE_RECOVERY_ACCOUNT_OPERATION.ordinal()));
            serializedChangeRecoveryAccountOperation.write(this.getAccountToRecover().toByteArray());
            serializedChangeRecoveryAccountOperation.write(this.getNewRecoveryAccount().toByteArray());
            for (FutureExtensions futureExtensions : this.getExtensions()) {
                serializedChangeRecoveryAccountOperation.write(futureExtensions.toByteArray());
            }

            return serializedChangeRecoveryAccountOperation.toByteArray();
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

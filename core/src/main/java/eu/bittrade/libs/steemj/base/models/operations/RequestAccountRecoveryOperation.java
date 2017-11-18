package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "request_account_recovery_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RequestAccountRecoveryOperation extends Operation {
    @JsonProperty("recovery_account")
    private AccountName recoveryAccount;
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @JsonProperty("new_owner_authority")
    private Authority newOwnerAuthority;
    // Original type is "extension_type" which is an array of "future_extions".
    @JsonProperty("extensions")
    private List<FutureExtensions> extensions;

    /**
     * Create a new request account recovery operation.
     * 
     * All account recovery requests come from a listed recovery account. This
     * is secure based on the assumption that only a trusted account should be a
     * recovery account. It is the responsibility of the recovery account to
     * verify the identity of the account holder of the account to recover by
     * whichever means they have agreed upon. The blockchain assumes identity
     * has been verified when this operation is broadcast.
     *
     * This operation creates an account recovery request which the account to
     * recover has 24 hours to respond to before the request expires and is
     * invalidated.
     *
     * There can only be one active recovery request per account at any one
     * time. Pushing this operation for an account to recover when it already
     * has an active request will either update the request to a new new owner
     * authority and extend the request expiration to 24 hours from the current
     * head block time or it will delete the request. To cancel a request,
     * simply set the weight threshold of the new owner authority to 0, making
     * it an open authority.
     *
     * Additionally, the new owner authority must be satisfiable. In other
     * words, the sum of the key weights must be greater than or equal to the
     * weight threshold.
     *
     * This operation only needs to be signed by the the recovery account. The
     * account to recover confirms its identity to the blockchain in the
     * {@link eu.bittrade.libs.steemj.base.models.operations.RecoverAccountOperation
     * RecoverAccountOperation}.
     * 
     * @param recoveryAccount
     *            The recovery account to set (see
     *            {@link #setRecoveryAccount(AccountName)}).
     * @param accountToRecover
     *            The account to recover (see
     *            {@link #setAccountToRecover(AccountName)}).
     * @param newOwnerAuthority
     *            The new owner authority (see
     *            {@link #setNewOwnerAuthority(Authority)}).
     * @param extensions
     *            Additional extensions to set (see
     *            {@link #setExtensions(List)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public RequestAccountRecoveryOperation(@JsonProperty("recovery_account") AccountName recoveryAccount,
            @JsonProperty("account_to_recover") AccountName accountToRecover,
            @JsonProperty("new_owner_authority") Authority newOwnerAuthority,
            @JsonProperty("extensions") List<FutureExtensions> extensions) {
        super(false);

        this.setRecoveryAccount(recoveryAccount);
        this.setAccountToRecover(accountToRecover);
        this.setNewOwnerAuthority(newOwnerAuthority);
        this.setExtensions(extensions);
    }

    /**
     * Like
     * {@link #RequestAccountRecoveryOperation(AccountName, AccountName, Authority, List)},
     * but does not require extensions to be added.
     * 
     * @param recoveryAccount
     *            The recovery account to set (see
     *            {@link #setRecoveryAccount(AccountName)}).
     * @param accountToRecover
     *            The account to recover (see
     *            {@link #setAccountToRecover(AccountName)}).
     * @param newOwnerAuthority
     *            The new owner authority (see
     *            {@link #setNewOwnerAuthority(Authority)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public RequestAccountRecoveryOperation(AccountName recoveryAccount, AccountName accountToRecover,
            Authority newOwnerAuthority) {
        this(recoveryAccount, accountToRecover, newOwnerAuthority, null);
    }

    /**
     * Get the recovery account.
     * 
     * @return The recovery account.
     */
    public AccountName getRecoveryAccount() {
        return recoveryAccount;
    }

    /**
     * Set the recovery account. The recovery account is listed as the
     * {@link eu.bittrade.libs.steemj.base.models.Account#getRecoveryAccount()
     * getRecoveryAccount()} on the account to recover. You can receive this
     * account by using the
     * {@link eu.bittrade.libs.steemj.SteemJ#getAccounts(List)
     * getAccounts(List)} method when passing the {@link #getAccountToRecover()
     * accountToRecover}. <b>Notice:</b> The private active key of this account
     * needs to be stored in the key storage.
     * 
     * @param recoveryAccount
     *            The recovery account.
     * @throws InvalidParameterException
     *             If the provided <code>recoveryAccount</code> is null.
     */
    public void setRecoveryAccount(AccountName recoveryAccount) {
        this.recoveryAccount = setIfNotNull(recoveryAccount, "The recovery account can't be null.");
    }

    /**
     * Get the account to recover.
     * 
     * @return The account to recover.
     */
    public AccountName getAccountToRecover() {
        return accountToRecover;
    }

    /**
     * Set the account to recover.
     * 
     * @param accountToRecover
     *            The account to recover.
     * @throws InvalidParameterException
     *             If the provided <code>accountToRecover</code> is null.
     */
    public void setAccountToRecover(AccountName accountToRecover) {
        this.accountToRecover = setIfNotNull(accountToRecover, "The account to recover can't be null.");
    }

    /**
     * Get the new owner authority.
     * 
     * @return The new owner authority.
     */
    public Authority getNewOwnerAuthority() {
        return newOwnerAuthority;
    }

    /**
     * Set the new owner authority. The new owner authority the account to
     * recover wishes to have. This is secret known by the account to recover
     * and will be confirmed in a
     * {@link eu.bittrade.libs.steemj.base.models.operations.RecoverAccountOperation
     * RecoverAccountOperation}.
     * 
     * @param newOwnerAuthority
     *            The new owner authority.
     * @throws InvalidParameterException
     *             If the provided <code>newOwnerAuthority</code> is null.
     */
    public void setNewOwnerAuthority(Authority newOwnerAuthority) {
        this.newOwnerAuthority = setIfNotNull(newOwnerAuthority, "The new owner authority can't be null.");
    }

    /**
     * Get the list of configured extensions.
     * 
     * @return All extensions.
     */
    public List<FutureExtensions> getExtensions() {
        return extensions;
    }

    /**
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     *            Define a list of extensions.
     */
    public void setExtensions(List<FutureExtensions> extensions) {
        if (extensions == null) {
            this.extensions = new ArrayList<>();
        } else {
            this.extensions = extensions;
        }
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedRequestAccountRecoveryOperation = new ByteArrayOutputStream()) {
            serializedRequestAccountRecoveryOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.REQUEST_ACCOUNT_RECOVERY_OPERATION.getOrderId()));
            serializedRequestAccountRecoveryOperation.write(this.getRecoveryAccount().toByteArray());
            serializedRequestAccountRecoveryOperation.write(this.getAccountToRecover().toByteArray());
            serializedRequestAccountRecoveryOperation.write(this.getNewOwnerAuthority().toByteArray());

            serializedRequestAccountRecoveryOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
            for (FutureExtensions futureExtensions : this.getExtensions()) {
                serializedRequestAccountRecoveryOperation.write(futureExtensions.toByteArray());
            }

            return serializedRequestAccountRecoveryOperation.toByteArray();
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
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getRecoveryAccount(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        return;
    }
}

package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.FutureExtensions;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "recover_account_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RecoverAccountOperation extends Operation {
    @JsonProperty("account_to_recover")
    private AccountName accountToRecover;
    @JsonProperty("new_owner_authority")
    private Authority newOwnerAuthority;
    @JsonProperty("recent_owner_authority")
    private Authority recentOwnerAuthority;
    // Original type is "extension_type" which is an array of "future_extions".
    private List<FutureExtensions> extensions;

    /**
     * Create a new recover account operation.
     * 
     * Recover an account to a new authority using a previous authority and
     * verification of the recovery account as proof of identity. This operation
     * can only succeed if there was a recovery request sent by the account's
     * recover account.
     *
     * In order to recover the account, the account holder must provide proof of
     * past ownership and proof of identity to the recovery account. Being able
     * to satisfy an owner authority that was used in the past 30 days is
     * sufficient to prove past ownership. The get_owner_history function in the
     * database API returns past owner authorities that are valid for account
     * recovery.
     *
     * Proving identity is an off chain contract between the account holder and
     * the recovery account. The recovery request contains a new authority which
     * must be satisfied by the account holder to regain control. The actual
     * process of verifying authority may become complicated, but that is an
     * application level concern, not a blockchain concern.
     *
     * This operation requires both the past and future owner authorities in the
     * operation because neither of them can be derived from the current chain
     * state. The operation must be signed by keys that satisfy both the new
     * owner authority and the recent owner authority. Failing either fails the
     * operation entirely.
     *
     * If a recovery request was made inadvertently, the account holder should
     * contact the recovery account to have the request deleted.
     *
     * The two step combination of the account recovery request and recover is
     * safe because the recovery account never has access to secrets of the
     * account to recover. They simply act as an on chain endorsement of off
     * chain identity. In other systems, a fork would be required to enforce
     * such off chain state. Additionally, an account cannot be permanently
     * recovered to the wrong account. While any owner authority from the past
     * 30 days can be used, including a compromised authority, the account can
     * be continually recovered until the recovery account is confident a
     * combination of uncompromised authorities were used to recover the
     * account. The actual process of verifying authority may become
     * complicated, but that is an application level concern, not the
     * blockchain's concern.
     */
    public RecoverAccountOperation() {
        // Define the required key type for this operation.
        super(false);
    }

    /**
     * Get the account to be recovered.
     * 
     * @return The account to be recovered.
     */
    public AccountName getAccountToRecover() {
        return accountToRecover;
    }

    /**
     * Set the account to recover. <b>Notice:</b> The private owner key of this
     * account needs to be stored in the key storage.
     * 
     * 
     * @param accountToRecover
     *            The account to recover.
     */
    public void setAccountToRecover(AccountName accountToRecover) {
        this.accountToRecover = accountToRecover;

        // Update the List of required private key types.
        addRequiredPrivateKeyType(accountToRecover, PrivateKeyType.OWNER);
    }

    /**
     * Get the new owner authority of the {@link #accountToRecover
     * accountToRecover}.
     * 
     * @return The new owner authority.
     */
    public Authority getNewOwnerAuthority() {
        return newOwnerAuthority;
    }

    /**
     * Set the new owner authority of the {@link #accountToRecover
     * accountToRecover}.
     * 
     * The new owner authority has to be the same that has been defined in a
     * {@link eu.bittrade.libs.steemj.base.models.operations.RequestAccountRecoveryOperation
     * RequestAccountRecoveryOperation} which has been executed before.
     * 
     * @param newOwnerAuthority
     *            The new owner authority.
     */
    public void setNewOwnerAuthority(Authority newOwnerAuthority) {
        this.newOwnerAuthority = newOwnerAuthority;
    }

    /**
     * Get the previous owner authority of the {@link #accountToRecover
     * accountToRecover}.
     * 
     * @return The previous owner authority.
     */
    public Authority getRecentOwnerAuthority() {
        return recentOwnerAuthority;
    }

    /**
     * Set the previous owner authority that the {@link #accountToRecover
     * accountToRecover} holder will use to prove past ownership of the account
     * to be recovered.
     * 
     * @param recentOwnerAuthority
     *            The previous owner authority.
     */
    public void setRecentOwnerAuthority(Authority recentOwnerAuthority) {
        this.recentOwnerAuthority = recentOwnerAuthority;
    }

    /**
     * Get the list of configured extensions.
     * 
     * @return All extensions.
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
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     *            Define a list of extensions.
     */
    public void setExtensions(List<FutureExtensions> extensions) {
        this.extensions = extensions;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedRecoverAccountOperation = new ByteArrayOutputStream()) {
            serializedRecoverAccountOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.RECOVER_ACCOUNT_OPERATION.ordinal()));
            serializedRecoverAccountOperation.write(this.getAccountToRecover().toByteArray());
            serializedRecoverAccountOperation.write(this.getNewOwnerAuthority().toByteArray());
            serializedRecoverAccountOperation.write(this.getRecentOwnerAuthority().toByteArray());
            for (FutureExtensions futureExtensions : this.getExtensions()) {
                serializedRecoverAccountOperation.write(futureExtensions.toByteArray());
            }

            return serializedRecoverAccountOperation.toByteArray();
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

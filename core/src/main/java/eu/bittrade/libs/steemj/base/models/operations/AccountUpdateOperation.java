package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Authority;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "account_update_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperation extends AbstractAccountOperation {
    @JsonProperty("account")
    private AccountName account;

    /**
     * Create a new create account update operation. Use this operation to
     * update the keys of an existing account.
     */
    public AccountUpdateOperation() {
        super(false);
    }

    /**
     * Get the owner {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getAccount() account}.
     * 
     * @return The owner authority.
     */
    @Override
    public Authority getOwner() {
        return owner;
    }

    /**
     * Set the new owner {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getAccount() account}.
     * 
     * @param owner
     *            The owner authority.
     */
    @Override
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * Get the active {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getAccount() account}.
     * 
     * @return The active authority.
     */
    @Override
    public Authority getActive() {
        return active;
    }

    /**
     * Set the new active {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getAccount() account}.
     * 
     * @param active
     *            The active authority.
     */
    @Override
    public void setActive(Authority active) {
        this.active = active;
    }

    /**
     * Get the posting {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getAccount() account}.
     * 
     * @return The posting authority.
     */
    @Override
    public Authority getPosting() {
        return posting;
    }

    /**
     * Set the new posting {@link eu.bittrade.libs.steemj.base.models.Authority
     * Authority} of the {@link #getAccount() account}.
     * 
     * @param posting
     *            The posting authority.
     */
    @Override
    public void setPosting(Authority posting) {
        this.posting = posting;
    }

    /**
     * Get the memo {@link eu.bittrade.libs.steemj.base.models.PublicKey
     * PublicKey} of the {@link #getAccount() account}.
     * 
     * @return The memo key.
     */
    @Override
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * Set the new memo {@link eu.bittrade.libs.steemj.base.models.PublicKey
     * PublicKey} of the {@link #getAccount() account}.
     * 
     * @param memoKey
     *            The memo key.
     */
    @Override
    public void setMemoKey(PublicKey memoKey) {
        this.memoKey = memoKey;
    }

    /**
     * Get the account name of the account that has been changed.
     * 
     * @return The account name of the changed account.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account name of the account that has been changed. <b>Notice:</b>
     * The private owner key of this account needs to be stored in the key
     * storage.
     * 
     * @param account
     *            The account name of the account to change.
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountUpdateOperation = new ByteArrayOutputStream()) {
            serializedAccountUpdateOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_UPDATE_OPERATION.ordinal()));
            serializedAccountUpdateOperation.write(this.getAccount().toByteArray());
            serializedAccountUpdateOperation.write(this.getOwner().toByteArray());
            serializedAccountUpdateOperation.write(this.getActive().toByteArray());
            serializedAccountUpdateOperation.write(this.getPosting().toByteArray());
            serializedAccountUpdateOperation.write(this.getMemoKey().toByteArray());
            serializedAccountUpdateOperation
                    .write(SteemJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));

            return serializedAccountUpdateOperation.toByteArray();
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
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.OWNER);
    }
}

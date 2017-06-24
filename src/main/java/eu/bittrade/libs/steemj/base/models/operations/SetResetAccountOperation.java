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
 * This class represents the Steem "set_reset_account_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetResetAccountOperation extends Operation {
    private AccountName account;
    @JsonProperty("current_reset_account")
    private AccountName currentResetAccount;
    @JsonProperty("reset_account")
    private AccountName resetAccount;

    /**
     * Create a new set reset account operation.
     * 
     * This operation allows the {@link #account account} owner to control which
     * account has the power to execute the 'reset_account_operation' after 60
     * days.
     */
    public SetResetAccountOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.OWNER);
    }

    /**
     * Get the account which the "set reset account operation" has been executed
     * for.
     * 
     * @return The account which the "set reset account operation" has been
     *         executed for.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Define for which account this "set reset account operation" is for.
     * 
     * @param account
     *            The account which the "set reset account operation" has been
     *            executed for.
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * Get the current reset account of the {@link #account account}. For newly
     * created accounts this is <i> new AccountName("null") </i> in most cases.
     * 
     * @return The current reset account for the {@link #account account}.
     */
    public AccountName getCurrentResetAccount() {
        return currentResetAccount;
    }

    /**
     * Set the current reset account of the {@link #account account}. For newly
     * created accounts this is <i> new AccountName("null") </i> in most cases.
     * 
     * @param currentResetAccount
     *            The current reset account for the {@link #account account}.
     */
    public void setCurrentResetAccount(AccountName currentResetAccount) {
        this.currentResetAccount = currentResetAccount;
    }

    /**
     * Get the new reset account which has been set with this operation.
     * 
     * @return The new reset account which has been set with this operation.
     */
    public AccountName getResetAccount() {
        return resetAccount;
    }

    /**
     * Set the new reset account for the {@link #account account}.
     * 
     * @param resetAccount
     *            The new reset account which has been set with this operation.
     */
    public void setResetAccount(AccountName resetAccount) {
        this.resetAccount = resetAccount;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedSetResetAccountOperation = new ByteArrayOutputStream()) {
            serializedSetResetAccountOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.SET_RESET_ACCOUNT_OPERATION.ordinal()));
            serializedSetResetAccountOperation.write(this.getAccount().toByteArray());
            serializedSetResetAccountOperation.write(this.getCurrentResetAccount().toByteArray());
            serializedSetResetAccountOperation.write(this.getResetAccount().toByteArray());

            return serializedSetResetAccountOperation.toByteArray();
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

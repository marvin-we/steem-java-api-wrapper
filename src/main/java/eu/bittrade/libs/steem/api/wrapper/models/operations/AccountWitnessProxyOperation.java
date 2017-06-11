package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "account_witness_proxy_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountWitnessProxyOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("proxy")
    private AccountName proxy;

    /**
     * Create a new create account witness proxy operation. Use this operation
     * to allow {@link #account account} to vote for a witness on your behalf.
     */
    public AccountWitnessProxyOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.ACTIVE);
    }

    /**
     * Get the account for which a witness proxy has been configured.
     * 
     * @return The account for which a witness proxy has been configured.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account for which a witness proxy should be set.
     * 
     * @param account
     *            The account for which a witness proxy should be set.
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * Get the account name of the witness proxy.
     * 
     * @return The account name of the witness proxy.
     */
    public AccountName getProxy() {
        return proxy;
    }

    /**
     * Set the account name of the witness proxy.
     * 
     * @param proxy
     *            The account name of the witness proxy.
     */
    public void setProxy(AccountName proxy) {
        this.proxy = proxy;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountWitnessProxyOperation = new ByteArrayOutputStream()) {
            serializedAccountWitnessProxyOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_WITNESS_PROXY_OPERATION.ordinal()));
            serializedAccountWitnessProxyOperation.write(this.getAccount().toByteArray());
            serializedAccountWitnessProxyOperation.write(this.getProxy().toByteArray());

            return serializedAccountWitnessProxyOperation.toByteArray();
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

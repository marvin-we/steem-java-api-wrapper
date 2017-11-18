package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
     * to allow {@link #getAccount() account} to vote for a witness on your
     * behalf.
     * 
     * @param account
     *            Define the account to set the proxy for (see
     *            {@link #setAccount(AccountName)}).
     * @param proxy
     *            Define the proxy for the <code>account</code> (see
     *            {@link #setProxy(AccountName)}).
     * @throws InvalidParameterException
     *             If one of the parameters does not fulfill the requirements.
     */
    public AccountWitnessProxyOperation(@JsonProperty("account") AccountName account,
            @JsonProperty("proxy") AccountName proxy) {
        super(false);

        this.setAccount(account);
        this.setProxy(proxy);
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
     * Set the account for which a witness proxy should be set. <b>Notice:</b>
     * The private active key of this account needs to be stored in the key
     * storage.
     * 
     * @param account
     *            The account for which a witness proxy should be set.
     * @throws InvalidParameterException
     *             If the account is null or if the same account has already
     *             been configured as the proxy.
     */
    public void setAccount(AccountName account) {
        this.account = setIfNotNull(account, "The account can't be null.");
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
     * @throws InvalidParameterException
     *             If the proxy is null or if the same account has already been
     *             configured as the <code>account</code>.
     */
    public void setProxy(AccountName proxy) {
        AccountName localProxy = proxy;
        if (localProxy == null) {
            localProxy = new AccountName("");
        }

        this.proxy = localProxy;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountWitnessProxyOperation = new ByteArrayOutputStream()) {
            serializedAccountWitnessProxyOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.ACCOUNT_WITNESS_PROXY_OPERATION.getOrderId()));
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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && (this.getProxy() != null && this.getProxy().equals(account))) {
            throw new InvalidParameterException("You can't proxy yourself.");
        }
    }
}

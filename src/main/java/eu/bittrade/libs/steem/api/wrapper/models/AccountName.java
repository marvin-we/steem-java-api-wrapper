package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;
import eu.bittrade.libs.steem.api.wrapper.models.serializer.AccountNameSerializer;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem data type "account_name_type".
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = AccountNameSerializer.class)
public class AccountName implements ByteTransformable {
    private String accountName;

    /**
     * Create an account name object with an empty account name.
     */
    public AccountName() {
    }

    /**
     * Create an account name object containing the given account name.
     * 
     * @param accountName
     *            The account name.
     */
    public AccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Get the account name of this instance.
     * 
     * @return The account name.
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Set the account name of this instance.
     * 
     * @param accountName
     *            The account name.
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        return SteemJUtils.transformStringToVarIntByteArray(accountName);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

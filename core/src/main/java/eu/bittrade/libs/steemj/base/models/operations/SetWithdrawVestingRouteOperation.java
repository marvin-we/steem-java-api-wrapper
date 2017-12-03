package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "set_withdraw_vesting_route_operation"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SetWithdrawVestingRouteOperation extends Operation {
    @JsonProperty("from_account")
    private AccountName fromAccount;
    @JsonProperty("to_account")
    private AccountName toAccount;
    // Original type is uint16_t so we use int here.
    @JsonProperty("percent")
    private int percent;
    @JsonProperty("auto_vest")
    private boolean autoVest;

    /**
     * Create a new set withdraw vesting route operation.
     * 
     * This operation allows an account to setup a vesting withdraw but with the
     * additional request for the funds to be transferred directly to another
     * account's balance rather than the withdrawing account. In addition, those
     * funds can be immediately vested again, circumventing the conversion from
     * vests to steem and back, guaranteeing they maintain their value.
     * 
     * @param fromAccount
     *            The account to set the route for (see
     *            {@link #setFromAccount(AccountName)}).
     * @param toAccount
     *            The account to transfer the funds to (see
     *            {@link #setToAccount(AccountName)}).
     * @param percent
     *            Define the percentage of the reward that should be transferred
     *            to the <code>toAccount</code> (see {@link #setPercent(int)}).
     * @param autoVest
     *            Define if the founds should automatically be vested again (see
     *            {@link #setAutoVest(Boolean)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public SetWithdrawVestingRouteOperation(@JsonProperty("from_account") AccountName fromAccount,
            @JsonProperty("to_account") AccountName toAccount, @JsonProperty("percent") int percent,
            @JsonProperty("auto_vest") boolean autoVest) {
        super(false);

        this.setFromAccount(fromAccount);
        this.setToAccount(toAccount);
        this.setPercent(percent);
        this.setAutoVest(autoVest);
    }

    /**
     * Like
     * {@link #SetWithdrawVestingRouteOperation(AccountName, AccountName, int, boolean)},
     * but will automatically use the default values (<code>percent=0</code> and
     * <code>autoVest=false</code>).
     * 
     * @param fromAccount
     *            The account to set the route for (see
     *            {@link #setFromAccount(AccountName)}).
     * @param toAccount
     *            The account to transfer the funds to (see
     *            {@link #setToAccount(AccountName)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public SetWithdrawVestingRouteOperation(AccountName fromAccount, AccountName toAccount) {
        this(fromAccount, toAccount, 0, false);
    }

    /**
     * Get the account whose funds will be transfered to the
     * {@link #getToAccount() toAccount}.
     * 
     * @return The account whose funds will be transfered.
     */
    public AccountName getFromAccount() {
        return fromAccount;
    }

    /**
     * Set the account whose funds will be transfered to the
     * {@link #getToAccount() toAccount}. <b>Notice:</b> The private active key
     * of this account needs to be stored in the key storage.
     * 
     * @param fromAccount
     *            The account whose funds will be transfered.
     * @throws InvalidParameterException
     *             If the <code>fromAccount</code> is null.
     */
    public void setFromAccount(AccountName fromAccount) {
        this.fromAccount = setIfNotNull(fromAccount, "The fromAccount can't be null.");
    }

    /**
     * Get the account to which the funds have been redirected.
     * 
     * @return The account to which the funds getting redirected.
     */
    public AccountName getToAccount() {
        return toAccount;
    }

    /**
     * Set the account to which the funds getting redirected.
     * 
     * @param toAccount
     *            The account to which the funds getting redirected.
     * @throws InvalidParameterException
     *             If the <code>toAccount</code> is null.
     */
    public void setToAccount(AccountName toAccount) {
        this.toAccount = setIfNotNull(toAccount, "The toAccount can't be null.");
    }

    /**
     * Get the information which percentage of the reward has been transfered to
     * the {@link #getToAccount() toAccount}.
     * 
     * @return The redirected percentage.
     */
    public int getPercent() {
        return percent;
    }

    /**
     * Define which percentage of the reward should be transfered to the
     * {@link #getToAccount() toAccount}.
     * 
     * @param percent
     *            The percentage to redirect.
     * @throws InvalidParameterException
     *             If the given percentage is lower than 0 or higher than 10000
     *             which is equivalent to 0.00%-100.00%.
     */
    public void setPercent(int percent) {
        this.percent = percent;
    }

    /**
     * Get the information if the routed funds have been directly reinvested.
     * 
     * @return True if the funds should be reinvested or false if not.
     */
    public boolean getAutoVest() {
        return autoVest;
    }

    /**
     * Define if the routed funds should be directly reinvested.
     * 
     * @param autoVest
     *            True if the funds should be reinvested or false if not.
     */
    public void setAutoVest(Boolean autoVest) {
        if (autoVest == null) {
            this.autoVest = Boolean.FALSE;
        } else {
            this.autoVest = autoVest;
        }
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedSetWithdrawVestingRouteOperation = new ByteArrayOutputStream()) {
            serializedSetWithdrawVestingRouteOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.SET_WITHDRAW_VESTING_ROUTE_OPERATION.getOrderId()));
            serializedSetWithdrawVestingRouteOperation.write(this.getFromAccount().toByteArray());
            serializedSetWithdrawVestingRouteOperation.write(this.getToAccount().toByteArray());
            serializedSetWithdrawVestingRouteOperation.write(SteemJUtils.transformShortToByteArray(this.getPercent()));
            serializedSetWithdrawVestingRouteOperation
                    .write(SteemJUtils.transformBooleanToByteArray(this.getAutoVest()));

            return serializedSetWithdrawVestingRouteOperation.toByteArray();
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
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getFromAccount(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if ((!ValidationType.SKIP_VALIDATION.equals(validationType)) && (percent < 0 || percent > 10000)) {
            throw new InvalidParameterException(
                    "The given percentage must be a postive number between 0 and 10000 which is equivalent to 0.00%-100.00%.");
        }
    }
}

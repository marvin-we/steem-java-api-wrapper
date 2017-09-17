package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.annotations.SignatureRequired;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
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
    @SignatureRequired(type = PrivateKeyType.ACTIVE)
    @JsonProperty("from_account")
    private AccountName fromAccount;
    @JsonProperty("to_account")
    private AccountName toAccount;
    // Original type is uint16_t so we use int here.
    @JsonProperty("percent")
    private int percent;
    @JsonProperty("auto_vest")
    private Boolean autoVest;

    /**
     * Create a new set withdraw vesting route operation.
     * 
     * This operation allows an account to setup a vesting withdraw but with the
     * additional request for the funds to be transferred directly to another
     * account's balance rather than the withdrawing account. In addition, those
     * funds can be immediately vested again, circumventing the conversion from
     * vests to steem and back, guaranteeing they maintain their value.
     */
    public SetWithdrawVestingRouteOperation() {
        super(false);
        // Set default values:
        this.setPercent(0);
        this.setAutoVest(false);
    }

    /**
     * Get the account whose funds will be transfered to the {@link #toAccount
     * toAccount}.
     * 
     * @return The account whose funds will be transfered.
     */
    public AccountName getFromAccount() {
        return fromAccount;
    }

    /**
     * Set the account whose funds will be transfered to the {@link #toAccount
     * toAccount}. <b>Notice:</b> The private active key of this account needs
     * to be stored in the key storage.
     * 
     * @param fromAccount
     *            The account whose funds will be transfered.
     */
    public void setFromAccount(AccountName fromAccount) {
        this.fromAccount = fromAccount;
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
     */
    public void setToAccount(AccountName toAccount) {
        this.toAccount = toAccount;
    }

    /**
     * Get the information which percentage of the reward has been transfered to
     * the {@link #toAccount toAccount}.
     * 
     * @return The redirected percentage.
     */
    public int getPercent() {
        return percent;
    }

    /**
     * Define which percentage of the reward should be transfered to the
     * {@link #toAccount toAccount}.
     * 
     * @param percent
     *            The percentage to redirect.
     */
    public void setPercent(int percent) {
        this.percent = percent;
    }

    /**
     * Get the information if the routed funds have been directly reinvested.
     * 
     * @return True if the funds should be reinvested or false if not.
     */
    public Boolean getAutoVest() {
        return autoVest;
    }

    /**
     * Define if the routed funds should be directly reinvested.
     * 
     * @param autoVest
     *            True if the funds should be reinvested or false if not.
     */
    public void setAutoVest(Boolean autoVest) {
        this.autoVest = autoVest;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedSetWithdrawVestingRouteOperation = new ByteArrayOutputStream()) {
            serializedSetWithdrawVestingRouteOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.SET_WITHDRAW_VESTING_ROUTE_OPERATION.ordinal()));
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
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getOwner(), PrivateKeyType.ACTIVE);
    }
}

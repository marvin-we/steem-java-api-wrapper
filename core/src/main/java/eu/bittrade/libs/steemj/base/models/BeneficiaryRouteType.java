package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.Validatable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "beneficiary_route_type" object
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BeneficiaryRouteType implements ByteTransformable, Validatable {
    @JsonProperty("account")
    private AccountName account;
    // Original type is uint16_t.
    @JsonProperty("weight")
    private short weight;

    /**
     * Create a new beneficiary route type.
     * 
     * @param account
     *            The account who is the beneficiary of this comment (see
     *            {@link #setAccount(AccountName)}).
     * @param weight
     *            The percentage the <code>account</code> will receive from the
     *            comment payout (see {@link #setWeight(short)}).
     * @throws InvalidParameterException
     *             If a parameter does not fulfill the requirements.
     */
    @JsonCreator
    public BeneficiaryRouteType(@JsonProperty("account") AccountName account, @JsonProperty("weight") short weight) {
        this.setAccount(account);
        this.setWeight(weight);
    }

    /**
     * Get the account who is the beneficiary of this comment.
     * 
     * @return The beneficiary.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Define the beneficiary for this comment.
     * 
     * @param account
     *            The beneficiary account.
     * @throws InvalidParameterException
     *             If the <code>account</code> is null.
     */
    public void setAccount(AccountName account) {
        if (account == null) {
            throw new InvalidParameterException("The account cannot be null.");
        }

        this.account = account;
    }

    /**
     * Get the percentage the {@link #getAccount() account} will receive from
     * the comment payout.
     * 
     * @return The percentage of the payout to relay.
     */
    public short getWeight() {
        return weight;
    }

    /**
     * Set the percentage the {@link #getAccount() account} will receive from
     * the comment payout.
     * 
     * @param weight
     *            The percentage of the payout to relay.
     * @throws InvalidParameterException
     *             If the <code>weight</code> is negative.
     */
    public void setWeight(short weight) {
        if (weight < 0) {
            throw new InvalidParameterException("The weight cannot be negative.");
        }

        this.weight = weight;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedBeneficiaryRouteType = new ByteArrayOutputStream()) {
            serializedBeneficiaryRouteType.write(this.getAccount().toByteArray());
            serializedBeneficiaryRouteType.write(SteemJUtils.transformShortToByteArray(this.getWeight()));

            return serializedBeneficiaryRouteType.toByteArray();
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
    public void validate(ValidationType validationType) {
        if (!validationType.equals(ValidationType.SKIP_VALIDATION) && this.getWeight() > 10000) {
            throw new InvalidParameterException("Cannot allocate more than 100% of rewards to one account.");
        }
    }
}

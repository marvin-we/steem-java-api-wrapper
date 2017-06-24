package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "beneficiary_route_type" object
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BeneficiaryRouteType implements ByteTransformable {
    private AccountName account;
    // Original type is uint16_t.
    private short weight;

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
     *            The beneficiary.
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * Get the percentage the {@link #account account} will receive from the
     * comment payout.
     * 
     * @return The percentage of the payout to relay.
     */
    public short getWeight() {
        return weight;
    }

    /**
     * Set the percentage the {@link #account account} will receive from the
     * comment payout.
     * 
     * @param weight
     *            The percentage of the payout to relay.
     */
    public void setWeight(short weight) {
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
}

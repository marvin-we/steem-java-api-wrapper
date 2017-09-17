package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "chain_properties" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ChainProperties implements ByteTransformable {
    @JsonProperty("account_creation_fee")
    private Asset accountCreationFee;
    // Original type is uint32_t so we use long here.
    @JsonProperty("maximum_block_size")
    private long maximumBlockSize;
    // Original type is uint16_t so we use int here.
    @JsonProperty("sbd_interest_rate")
    private int sdbInterestRate;

    /**
     * Create a new ChainProperties object.
     * 
     * @param accountCreationFee
     * @param maximumBlockSize
     * @param sdbInterestRate
     */
    @JsonCreator
    public ChainProperties(@JsonProperty("account_creation_fee") Asset accountCreationFee,
            @JsonProperty("maximum_block_size") long maximumBlockSize,
            @JsonProperty("sbd_interest_rate") int sdbInterestRate) {
        this.setAccountCreationFee(accountCreationFee);
        this.setMaximumBlockSize(maximumBlockSize);
        this.setSdbInterestRate(sdbInterestRate);
    }

    /**
     * @return The fee that needs to be paid when a new account is created.
     */
    public Asset getAccountCreationFee() {
        return accountCreationFee;
    }

    /**
     * Define the account creation fee.
     * 
     * This fee, paid in STEEM, is converted into VESTING SHARES for the new
     * account. Accounts without vesting shares cannot earn usage rations and
     * therefore are powerless. This minimum fee requires all accounts to have
     * some kind of commitment to the network that includes the ability to vote
     * and make transactions.
     * 
     * @param accountCreationFee
     *            The accountCreationFee to set.
     * @throws InvalidParameterException
     *             If the fee is not present or less than 0.
     */
    public void setAccountCreationFee(Asset accountCreationFee) {
        if (accountCreationFee == null || accountCreationFee.getAmount() <= 0) {
            throw new InvalidParameterException("The account creation fee needs to be greater than 0.");
        }
        this.accountCreationFee = accountCreationFee;
    }

    /**
     * @return The maximum block size.
     */
    public long getMaximumBlockSize() {
        return maximumBlockSize;
    }

    /**
     * Set the <code>maximumBlockSize</code> which is used by the network to
     * tune rate limiting and capacity.
     * 
     * @param maximumBlockSize
     *            The maximum block size to set.
     * @throws InvalidParameterException
     *             If the maximum block size is less than the minimal block size
     *             (65536).
     */
    public void setMaximumBlockSize(long maximumBlockSize) {
        if (maximumBlockSize < 65536) {
            throw new InvalidParameterException(
                    "The maximum block size needs to be greater than 65536 (minum block size).");
        }
        this.maximumBlockSize = maximumBlockSize;
    }

    /**
     * @return The interest rate for SBD.
     */
    public int getSdbInterestRate() {
        return sdbInterestRate;
    }

    /**
     * Define the interest rate for SBD in percent, while 0 equals 0% and 10000
     * equals 100.00%.
     * 
     * @param sdbInterestRate
     *            The SBD interest rate to set.
     * @throws InvalidParameterException
     *             If the SBD interest rate is less than 0% or more than 100%
     *             (10000).
     */
    public void setSdbInterestRate(int sdbInterestRate) {
        if (sdbInterestRate < 0 || sdbInterestRate > 10000) {
            throw new InvalidParameterException(
                    "The SBD interest rate needs to be somewhere between 0 and 10000 (100%).");
        }
        this.sdbInterestRate = sdbInterestRate;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedChainProperties = new ByteArrayOutputStream()) {
            serializedChainProperties.write(this.getAccountCreationFee().toByteArray());
            serializedChainProperties.write(SteemJUtils.transformIntToByteArray((int) this.getMaximumBlockSize()));
            serializedChainProperties.write(SteemJUtils.transformShortToByteArray(this.getSdbInterestRate()));

            return serializedChainProperties.toByteArray();
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

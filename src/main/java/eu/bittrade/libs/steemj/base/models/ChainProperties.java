package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
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
     * @return the accountCreationFee
     */
    public Asset getAccountCreationFee() {
        return accountCreationFee;
    }

    /**
     * @param accountCreationFee
     *            the accountCreationFee to set
     */
    public void setAccountCreationFee(Asset accountCreationFee) {
        this.accountCreationFee = accountCreationFee;
    }

    /**
     * @return the maximumBlockSize
     */
    public long getMaximumBlockSize() {
        return maximumBlockSize;
    }

    /**
     * @param maximumBlockSize
     *            the maximumBlockSize to set
     */
    public void setMaximumBlockSize(long maximumBlockSize) {
        this.maximumBlockSize = maximumBlockSize;
    }

    /**
     * @return the sdbInterestRate
     */
    public int getSdbInterestRate() {
        return sdbInterestRate;
    }

    /**
     * @param sdbInterestRate
     *            the sdbInterestRate to set
     */
    public void setSdbInterestRate(int sdbInterestRate) {
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

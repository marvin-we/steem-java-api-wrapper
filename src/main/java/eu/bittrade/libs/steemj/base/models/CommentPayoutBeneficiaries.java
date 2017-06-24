package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class represents a Steem "comment_payout_beneficiaries" object
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentPayoutBeneficiaries implements ByteTransformable {
    @JsonIgnore
    private int index;
    private List<BeneficiaryRouteType> beneficiaries;

    /**
     * Default constructor if no inner elements are present.
     */
    public CommentPayoutBeneficiaries() {
    }

    /**
     * 
     * @param index
     */
    @JsonCreator
    public CommentPayoutBeneficiaries(@JsonProperty int index) {
        this.index = index;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index
     *            the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the beneficiaries
     */
    public List<BeneficiaryRouteType> getBeneficiaries() {
        return beneficiaries;
    }

    /**
     * @param beneficiaries
     *            the beneficiaries to set
     */
    public void setBeneficiaries(List<BeneficiaryRouteType> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentPayoutBeneficiaries = new ByteArrayOutputStream()) {

            // TODO for (BeneficiaryRouteType veneficiaryRouteType :
            // this.getBeneficiaries()) {
            // serializedCommentPayoutBeneficiaries.write(veneficiaryRouteType.toByteArray());
            // }

            return serializedCommentPayoutBeneficiaries.toByteArray();
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

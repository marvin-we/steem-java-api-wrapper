package eu.bittrade.libs.steem.api.wrapper.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;

/**
 * This class represents a Steem "comment_payout_beneficiaries" object
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentPayoutBeneficiaries implements ByteTransformable {
    private List<BeneficiaryRouteType> beneficiaries;

    /**
     * Get the beneficiaries of this comment.
     * 
     * @return The beneficiaries of this comment.
     */
    public List<BeneficiaryRouteType> getBeneficiaries() {
        return beneficiaries;
    }

    /**
     * Set the beneficiaries of this comment.
     * 
     * @param beneficiaries
     *            The beneficiaries of this comment.
     */
    public void setBeneficiaries(List<BeneficiaryRouteType> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentPayoutBeneficiaries = new ByteArrayOutputStream()) {

            for (BeneficiaryRouteType veneficiaryRouteType : this.getBeneficiaries()) {
                serializedCommentPayoutBeneficiaries.write(veneficiaryRouteType.toByteArray());
            }

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

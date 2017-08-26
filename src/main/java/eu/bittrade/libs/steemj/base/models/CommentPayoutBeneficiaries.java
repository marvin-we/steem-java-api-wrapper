package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "comment_payout_beneficiaries" object
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentPayoutBeneficiaries implements ByteTransformable {
    private List<BeneficiaryRouteType> beneficiaries;

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

            serializedCommentPayoutBeneficiaries
                    .write(SteemJUtils.transformLongToVarIntByteArray(this.getBeneficiaries().size()));

            for (BeneficiaryRouteType beneficiaryRouteType : this.getBeneficiaries()) {
                serializedCommentPayoutBeneficiaries.write(beneficiaryRouteType.toByteArray());
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

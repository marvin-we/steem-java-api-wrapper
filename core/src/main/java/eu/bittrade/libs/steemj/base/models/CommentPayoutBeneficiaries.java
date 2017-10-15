package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.bittrade.libs.steemj.enums.CommentOptionsExtensionsType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "comment_payout_beneficiaries" object
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize
public class CommentPayoutBeneficiaries extends CommentOptionsExtension {
    @JsonProperty("beneficiaries")
    private List<BeneficiaryRouteType> beneficiaries;

    /**
     * @return The beneficiaries.
     */
    public List<BeneficiaryRouteType> getBeneficiaries() {
        return beneficiaries;
    }

    /**
     * @param beneficiaries
     *            The beneficiaries to set.
     */
    public void setBeneficiaries(List<BeneficiaryRouteType> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentPayoutBeneficiaries = new ByteArrayOutputStream()) {
            serializedCommentPayoutBeneficiaries.write(SteemJUtils.transformIntToVarIntByteArray(
                    CommentOptionsExtensionsType.COMMENT_PAYOUT_BENEFICIARIES.ordinal()));

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

    @Override
    public void validate(ValidationType validationType) {
        if (!validationType.equals(ValidationType.SKIP_VALIDATION)) {
            if (this.getBeneficiaries().isEmpty()) {
                throw new InvalidParameterException("Must specify at least one beneficiary.");
            } else if (this.getBeneficiaries().size() >= 128) {
                // Require size serialization fits in one byte.
                throw new InvalidParameterException("Cannot specify more than 127 beneficiaries.");
            }

            // TODO: Verify if the list is sorted correctly.
            // FC_ASSERT( beneficiaries[i - 1] < beneficiaries[i], "Benficiaries
            // must be specified in sorted order (account ascending)" );

            int sum = 0;
            for (BeneficiaryRouteType beneficiaryRouteType : this.getBeneficiaries()) {
                sum += beneficiaryRouteType.getWeight();
                if (sum > 10000) {
                    throw new InvalidParameterException("Cannot allocate more than 100% of rewards to a comment");
                }
            }
        }
    }
}

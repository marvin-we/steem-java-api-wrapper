package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class repesents a Steem "comment_options_extenson" object.
 * 
 * The "comment_options_extenson" class is a "static_variant" which can carry
 * different types in theory. The resulting json of th "static_variant" fields
 * looks like this:
 * 
 * <p>
 * [0,{"beneficiaries":[{"account":"esteemapp","weight":500}]}]
 * </p>
 * 
 * It seems that the "0" indicates the type, so using <code>@JsonTypeInfo</code>
 * / <code>@JsonSubTypes</code> would be the cleaner solution here. Sadly,
 * Jackson does not allow Integer Ids at the moment.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class CommentOptionsExtension implements ByteTransformable {
    /**
     * This field is used to indicate the sub-type of the carried
     * comment_options_extenson object.
     */
    private int classTypeIdentifier;
    /**
     * The comment_options_extension instance of the {@link #classTypeIdentifier
     * classTypeIdentifier}.
     */
    private CommentPayoutBeneficiaries commentPayoutBeneficiaries;

    /**
     * This constructor is only used by Jackson to handle the WRAPPER_ARRAY in a
     * custom way.
     * 
     * @param classTypeIdentifier
     *            The sub-type identifier.
     * @param commentPayoutBeneficiaries
     *            The comment_options_extension instance.
     */
    @JsonCreator
    private CommentOptionsExtension(@JsonProperty(index = 0, value = "classTypeIdentifier") Integer classTypeIdentifier,
            @JsonProperty(index = 1, value = "commentPayoutBeneficiaries") CommentPayoutBeneficiaries commentPayoutBeneficiaries) {
        this.classTypeIdentifier = classTypeIdentifier;
        this.commentPayoutBeneficiaries = commentPayoutBeneficiaries;
    }

    /**
     * Create a new Steem "comment_options_extenson" object by providing a
     * <code>commentPayoutBeneficiaries</code>.
     * 
     * @param commentPayoutBeneficiaries
     *            The
     *            {@link eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries
     *            CommentPayoutBeneficiaries} instance that should be added.
     */
    public CommentOptionsExtension(CommentPayoutBeneficiaries commentPayoutBeneficiaries) {
        classTypeIdentifier = 0;
        this.commentPayoutBeneficiaries = commentPayoutBeneficiaries;
    }

    /**
     * Get the id of the used sub-type.
     * 
     * @return The sub-type id.
     */
    public int getClassTypeIdentifier() {
        return classTypeIdentifier;
    }

    /**
     * Get the comment_payout_beneficiaries object wrapped in this object.
     * 
     * @return The commentPayoutBeneficiaries object.
     */
    public CommentPayoutBeneficiaries getCommentPayoutBeneficiaries() {
        return commentPayoutBeneficiaries;
    }

    /**
     * Set the comment_payout_beneficiaries object wrapped in this object.
     * 
     * @param commentPayoutBeneficiaries
     *            The CommentPayoutBeneficiaries object to set.
     */
    public void setCommentPayoutBeneficiaries(CommentPayoutBeneficiaries commentPayoutBeneficiaries) {
        this.commentPayoutBeneficiaries = commentPayoutBeneficiaries;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentOptionsExtension = new ByteArrayOutputStream()) {
            if (this.getCommentPayoutBeneficiaries() == null) {
                byte[] extension = { 0x00 };
                serializedCommentOptionsExtension.write(extension);
            } else {
                serializedCommentOptionsExtension.write(this.getCommentPayoutBeneficiaries().toByteArray());
            }

            return serializedCommentOptionsExtension.toByteArray();
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

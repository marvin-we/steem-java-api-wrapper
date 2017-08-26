package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.serializer.CommentOptionsExtensionSerializer;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class repesents a Steem "comment_options_extenson" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = CommentOptionsExtensionSerializer.class)
public class CommentOptionsExtension implements ByteTransformable {
    private List<CommentPayoutBeneficiaries> innerCommentOptionsExtension;

    /**
     * Default constructor if no inner elements are present.
     */
    public CommentOptionsExtension() {
    }

    /**
     * 
     * @param innerCommentOptionsExtension
     */
    @JsonCreator
    public CommentOptionsExtension(@JsonProperty List<CommentPayoutBeneficiaries> innerCommentOptionsExtension) {
        this.innerCommentOptionsExtension = innerCommentOptionsExtension;
    }

    /**
     * @return the innerCommentOptionsExtension
     */
    public List<CommentPayoutBeneficiaries> getInnerCommentOptionsExtension() {
        return innerCommentOptionsExtension;
    }

    /**
     * @param innerCommentOptionsExtension
     *            the innerCommentOptionsExtension to set
     */
    public void setInnerCommentOptionsExtension(List<CommentPayoutBeneficiaries> innerCommentOptionsExtension) {
        this.innerCommentOptionsExtension = innerCommentOptionsExtension;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentOptionsExtension = new ByteArrayOutputStream()) {
            byte[] extension = { 0x00 };
            serializedCommentOptionsExtension.write(extension);

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

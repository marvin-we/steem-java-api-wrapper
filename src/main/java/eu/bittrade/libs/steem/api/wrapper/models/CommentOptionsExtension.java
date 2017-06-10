package eu.bittrade.libs.steem.api.wrapper.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsExtension implements ByteTransformable {
    /*
     * TODO: Implement members. This object looks like this:
     *
     * typedef static_variant< comment_payout_beneficiaries >
     * comment_options_extension;
     * 
     * While comment_payout_beneficiaries looks like this: vector<
     * beneficiary_route_type > beneficiaries;
     */
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

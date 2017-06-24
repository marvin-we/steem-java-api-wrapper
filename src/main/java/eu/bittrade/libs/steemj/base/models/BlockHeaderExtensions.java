package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.serializer.BlockHeaderExtensionsSerializer;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = BlockHeaderExtensionsSerializer.class)
public class BlockHeaderExtensions implements ByteTransformable {
    /*
     * TODO: Implement members. This object looks like this:
     *
     * typedef static_variant< void_t, version, // Normal witness version
     * reporting, for diagnostics and voting hardfork_version_vote // Voting for
     * the next hardfork to trigger >
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

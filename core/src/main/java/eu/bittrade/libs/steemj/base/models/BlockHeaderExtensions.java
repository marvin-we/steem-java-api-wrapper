package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.deserializer.BlockHeaderExtensionsDeserializer;
import eu.bittrade.libs.steemj.base.models.serializer.BlockHeaderExtensionsSerializer;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = BlockHeaderExtensionsSerializer.class)
@JsonDeserialize(using = BlockHeaderExtensionsDeserializer.class)
public class BlockHeaderExtensions implements ByteTransformable {
    private String version;
    private HardforkVersionVote hardforkVersionVote;

    /**
     * Normal witness version reporting, for diagnostics and voting
     * 
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Voting for the next hardfork to trigger
     * 
     * @return the hardforkVersionVote
     */
    public HardforkVersionVote getHardforkVersionVote() {
        return hardforkVersionVote;
    }

    /**
     * @param hardforkVersionVote
     *            the hardforkVersionVote to set
     */
    public void setHardforkVersionVote(HardforkVersionVote hardforkVersionVote) {
        this.hardforkVersionVote = hardforkVersionVote;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentOptionsExtension = new ByteArrayOutputStream()) {
            byte[] extension = { 0x00 };
            serializedCommentOptionsExtension.write(extension);
            // TODO
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

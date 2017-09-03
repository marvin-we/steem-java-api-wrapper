package eu.bittrade.libs.steemj.base.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.serializer.FutureExtensionsSerializer;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class represents a "future_extensions_type" object which has no member
 * in the current version.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = FutureExtensionsSerializer.class)
public class FutureExtensions implements ByteTransformable {
    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedFutureExtensions = new ByteArrayOutputStream()) {
            byte[] extension = { 0x00 };
            serializedFutureExtensions.write(extension);

            return serializedFutureExtensions.toByteArray();
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

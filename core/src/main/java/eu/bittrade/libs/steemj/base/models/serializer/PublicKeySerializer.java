package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steemj.protocol.PublicKey;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PublicKeySerializer extends JsonSerializer<PublicKey> {

    @Override
    public void serialize(PublicKey publicKey, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(publicKey.getAddressFromPublicKey());
    }
}

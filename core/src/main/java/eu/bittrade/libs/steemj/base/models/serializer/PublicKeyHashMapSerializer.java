package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steemj.base.models.PublicKey;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PublicKeyHashMapSerializer extends JsonSerializer<Map<PublicKey, Integer>> {

    @Override
    public void serialize(Map<PublicKey, Integer> keyAuthMap, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Entry<PublicKey, Integer> keyAuth : keyAuthMap.entrySet()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(keyAuth.getKey().getAddressFromPublicKey());
            jsonGenerator.writeNumber(keyAuth.getValue());
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
    }
}

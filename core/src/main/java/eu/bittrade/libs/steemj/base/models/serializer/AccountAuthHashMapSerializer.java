package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountAuthHashMapSerializer extends JsonSerializer<Map<String, Integer>> {

    @Override
    public void serialize(Map<String, Integer> accountAuthMap, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Entry<String, Integer> accountAuth : accountAuthMap.entrySet()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(accountAuth.getKey());
            jsonGenerator.writeNumber(accountAuth.getValue());
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
    }
}

package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountAuthHashMapDeserializer extends JsonDeserializer<Map<String, Integer>> {
    @Override
    public Map<String, Integer> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        HashMap<String, Integer> result = new HashMap<>();

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode rootNode = codec.readTree(jsonParser);

        if (rootNode.isArray()) {
            for (JsonNode node : (ArrayNode) rootNode) {
                result.put((node.get(0)).asText(), (node.get(0)).asInt());
            }

            return result;
        }

        throw new IllegalArgumentException("JSON Node is not an array.");
    }
}

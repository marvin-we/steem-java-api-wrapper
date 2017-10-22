package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class OperationHistoryHashMapDeserializer extends JsonDeserializer<Map<BigInteger, AppliedOperation>> {
    @Override
    public Map<BigInteger, AppliedOperation> deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

        HashMap<BigInteger, AppliedOperation> result = new HashMap<>();

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode rootNode = codec.readTree(jsonParser);

        if (rootNode.isArray()) {
            for (JsonNode node : (ArrayNode) rootNode) {
                result.put(new BigInteger((node.get(0)).asText()),
                        CommunicationHandler.getObjectMapper().treeToValue(node.get(1), AppliedOperation.class));
            }

            return result;
        }

        throw new IllegalArgumentException("JSON Node is not an array.");
    }
}

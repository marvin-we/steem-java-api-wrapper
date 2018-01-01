package eu.bittrade.libs.steemj.plugins.apis.account.history.models.deserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joou.UInteger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.AppliedOperation;

/**
 * Due to FasterXML/jackson-databind#1152 it is not possible to parse Maps in an
 * Array Format with Jackson out of the box. Therefore this custom Deserializer
 * is required.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AppliedOperationHashMapDeserializer extends JsonDeserializer<Map<UInteger, AppliedOperation>> {
    @Override
    public Map<UInteger, AppliedOperation> deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

        HashMap<UInteger, AppliedOperation> result = new HashMap<>();

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode rootNode = codec.readTree(jsonParser);

        if (rootNode.isArray()) {
            for (JsonNode node : (ArrayNode) rootNode) {
                result.put(UInteger.valueOf(node.get(0).asLong()),
                        CommunicationHandler.getObjectMapper().treeToValue(node.get(1), AppliedOperation.class));
            }

            return result;
        }

        throw new IllegalArgumentException("JSON Node is not an array.");
    }
}

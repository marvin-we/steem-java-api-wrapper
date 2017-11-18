package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GuestBloggerPairDeserializer extends JsonDeserializer<List<Pair<AccountName, Long>>> {
    @Override
    public List<Pair<AccountName, Long>> deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext) throws IOException {

        List<Pair<AccountName, Long>> result = new ArrayList<>();

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode rootNode = codec.readTree(jsonParser);

        if (rootNode.isArray()) {
            for (JsonNode node : (ArrayNode) rootNode) {
                // result.put((node.get(0)).asText(), (node.get(0)).asInt());
            }

            return result;
        }

        throw new IllegalArgumentException("JSON Node is not an array.");
    }
}

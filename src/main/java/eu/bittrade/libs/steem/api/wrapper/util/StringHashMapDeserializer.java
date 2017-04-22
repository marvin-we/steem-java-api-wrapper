package eu.bittrade.libs.steem.api.wrapper.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class StringHashMapDeserializer extends JsonDeserializer<Map<String, Integer>> {
    @Override
    public Map<String, Integer> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        HashMap<String, Integer> result = new HashMap<>();

        ObjectCodec codec = p.getCodec();
        TreeNode rootNode = codec.readTree(p);

        if (rootNode.isArray()){
            for (JsonNode node : (ArrayNode) rootNode){
                result.put((node.get(0)).asText(), (node.get(0)).asInt());
            }

            return result;
        }
        
        throw new IllegalArgumentException("JSON Node is not an array.");
    }
}

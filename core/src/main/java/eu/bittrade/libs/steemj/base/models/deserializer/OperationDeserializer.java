package eu.bittrade.libs.steemj.base.models.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import eu.bittrade.libs.steemj.base.models.operations.Operation;

import java.io.IOException;

public class OperationDeserializer extends JsonDeserializer<Operation> {

    @Override
    public Operation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = arg0.readValueAsTree();

        try {
            return mapper.readValue(node.toString(), Operation.class);
        } catch (JsonMappingException e) {
            System.out.println("Issue in deserializing : " + e.getMessage() + "for :" + node.toString());
        } catch (Exception e) {
            throw e;
        }
        return null;
    }
}

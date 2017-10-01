package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import eu.bittrade.libs.steemj.base.models.BlockHeaderExtensions;
import eu.bittrade.libs.steemj.base.models.HardforkVersionVote;
import eu.bittrade.libs.steemj.base.models.Version;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;

/**
 * This class is used as a Java implementation of a variant that can contain a:
 * <ul>
 * <li>void</li>
 * <li>version</li>
 * <li>hardfork_version_vote</li>
 * </ul>
 * 
 * A response can look like this:
 * <p>
 * <code>[1,"0.19.0"],[2,{"hf_version":"0.19.0","hf_time":"2020-05-31T00:46:40"}]</code>
 * </p>
 * While the first element of the array is the ID of the type (version,
 * hardfork_version_vote).
 * 
 * The cleanest solution would be to use the of the Jackson "@JsonSubTypes", but
 * sadly, this only works if the name is a String (see
 * https://github.com/FasterXML/jackson-databind/issues/1751).
 * 
 * Due to that things have to be done manually.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockHeaderExtensionsDeserializer extends JsonDeserializer<BlockHeaderExtensions> {
    @Override
    public BlockHeaderExtensions deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.readValueAsTree();

        if (node == null || node.size() != 2 || !node.get(0).isInt()) {
            throw new IllegalArgumentException("The received JSON does not has the required structure.");
        }

        // Get the core class by mapping the given type id.
        int coreTypeId = node.get(0).asInt();
        switch (coreTypeId) {
        case 0:
            return null;
        case 1:
            return CommunicationHandler.getObjectMapper().treeToValue(node.get(1), Version.class);
        case 2:
            return CommunicationHandler.getObjectMapper().treeToValue(node.get(1), HardforkVersionVote.class);
        default:
            throw new IllegalArgumentException("Unknown extension type id '" + coreTypeId + "'.");
        }
    }
}
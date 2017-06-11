package eu.bittrade.libs.steem.api.wrapper.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steem.api.wrapper.models.BlockHeaderExtensions;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockHeaderExtensionsSerializer extends JsonSerializer<BlockHeaderExtensions> {

    @Override
    public void serialize(BlockHeaderExtensions blockHeaderExtensions, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        // As long as Extensions are not supported we simply return nothing here.
    }
}

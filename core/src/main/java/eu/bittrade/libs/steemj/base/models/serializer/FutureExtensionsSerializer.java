package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steemj.base.models.FutureExtensions;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FutureExtensionsSerializer extends JsonSerializer<FutureExtensions> {

    @Override
    public void serialize(FutureExtensions futureExtensions, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        // As long as Extensions are not supported we simply return nothing
        // here.
    }
}

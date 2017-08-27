package eu.bittrade.libs.steemj.plugins.follow.model.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steemj.plugins.follow.enums.FollowType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowTypeSerializer extends JsonSerializer<FollowType> {
    @Override
    public void serialize(FollowType followType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        jsonGenerator.writeString(followType.toString().toLowerCase());
    }
}

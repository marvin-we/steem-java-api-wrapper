package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TimePointSecSerializer extends JsonSerializer<TimePointSec> {

    @Override
    public void serialize(TimePointSec timePointSec, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(timePointSec.getDateTime());
    }
}

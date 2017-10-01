package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.bittrade.libs.steemj.enums.CurveId;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CurveIdDeserializer extends JsonDeserializer<CurveId> {
    @Override
    public CurveId deserialize(JsonParser jasonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonToken currentToken = jasonParser.currentToken();
        if (currentToken != null && JsonToken.VALUE_STRING.equals(currentToken)) {
            CurveId curveId = CurveId.valueOf(jasonParser.getText().toUpperCase());

            if (curveId == null) {
                throw new IllegalArgumentException("Could not deserialize '" + currentToken + "' to a CurveId type.");
            }

            return curveId;
        }

        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.VALUE_STRING + "'.");
    }
}
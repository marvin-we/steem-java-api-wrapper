package eu.bittrade.libs.steemj.apis.follow.model.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowTypeDeserializer extends JsonDeserializer<FollowType> {
    @Override
    public FollowType deserialize(JsonParser jasonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonToken currentToken = jasonParser.currentToken();
        if (currentToken != null && JsonToken.VALUE_STRING.equals(currentToken)) {
            if (jasonParser.getText().isEmpty()) {
                return FollowType.UNDEFINED;
            }

            FollowType followType = FollowType.valueOf(jasonParser.getText().toUpperCase());

            if (followType == null) {
                throw new IllegalArgumentException("Could not deserialize '" + currentToken + "' to a follow type.");
            }

            return followType;
        }

        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.VALUE_STRING + "'.");
    }
}
package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.bittrade.libs.steemj.base.models.BlockHeaderExtensions;
import eu.bittrade.libs.steemj.base.models.HardforkVersionVote;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockHeaderExtensionsDeserializer extends JsonDeserializer<BlockHeaderExtensions> {
    @Override
    public BlockHeaderExtensions deserialize(JsonParser jasonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonToken currentToken = jasonParser.currentToken();
        if (currentToken != null && JsonToken.START_ARRAY.equals(currentToken)) {
            BlockHeaderExtensions blockHeaderExtensions = new BlockHeaderExtensions();
            // Example response looks like this:
            // [1,"0.19.0"],[2,{"hf_version":"0.19.0","hf_time":"2020-05-31T00:46:40"}]
            // As we do not need the ID we skip the first elements.
            for (int i = 0; i < 2; i++) {
                jasonParser.nextToken();
            }

            blockHeaderExtensions.setVersion(jasonParser.getText());

            for (int i = 0; i < 6; i++) {
                jasonParser.nextToken();
            }

            HardforkVersionVote hardforkVersionVote = new HardforkVersionVote();
            hardforkVersionVote.setHfVersion(jasonParser.getText());

            for (int i = 0; i < 2; i++) {
                jasonParser.nextToken();
            }

            SimpleDateFormat simpleDateFormatter = new SimpleDateFormat(
                    SteemJConfig.getInstance().getDateTimePattern());

            String date = jasonParser.getText();
            try {
                hardforkVersionVote.setHfTime(simpleDateFormatter.parse(date)); 
            } catch (ParseException e) {
                throw new IllegalArgumentException("Could not parse '" + date + "' into a date.");
            }

            for (int i = 0; i < 2; i++) {
                jasonParser.nextToken();
            }

            blockHeaderExtensions.setHardforkVersionVote(hardforkVersionVote);

            return blockHeaderExtensions;
        }

        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.START_ARRAY + "'.");
    }
}
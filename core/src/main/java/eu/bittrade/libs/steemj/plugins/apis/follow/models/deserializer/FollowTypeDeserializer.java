/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.follow.models.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType;

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

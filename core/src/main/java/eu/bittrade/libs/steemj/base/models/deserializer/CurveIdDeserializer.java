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
package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.bittrade.libs.steemj.protocol.enums.CurveId;

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

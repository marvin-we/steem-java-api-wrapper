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
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.protocol.Asset;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AssetDeserializer extends JsonDeserializer<Asset> {
    @Override
    public Asset deserialize(JsonParser jasonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken currentToken = jasonParser.currentToken();
        if (currentToken != null && JsonToken.VALUE_STRING.equals(currentToken)) {
            String[] assetFields = jasonParser.getText().split(" ");

            if (assetFields.length == 2) {
                /*
                 * Set the symbol first which calculates the precision
                 * internally.
                 *
                 * The amount is provided as a double value while we need a long
                 * value for the byte representation so we transform the amount
                 * into a long value here.
                 */
                return new Asset(new BigDecimal(assetFields[0]), AssetSymbolType.valueOf(assetFields[1]));
            }
        }

        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.VALUE_STRING + "'.");
    }
}

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
package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steemj.protocol.PublicKey;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PublicKeyHashMapSerializer extends JsonSerializer<Map<PublicKey, Integer>> {

    @Override
    public void serialize(Map<PublicKey, Integer> keyAuthMap, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Entry<PublicKey, Integer> keyAuth : keyAuthMap.entrySet()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(keyAuth.getKey().getAddressFromPublicKey());
            jsonGenerator.writeNumber(keyAuth.getValue());
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
    }
}

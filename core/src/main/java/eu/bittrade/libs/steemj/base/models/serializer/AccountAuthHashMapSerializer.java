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

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountAuthHashMapSerializer extends JsonSerializer<Map<String, Integer>> {

    @Override
    public void serialize(Map<String, Integer> accountAuthMap, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (Entry<String, Integer> accountAuth : accountAuthMap.entrySet()) {
            jsonGenerator.writeStartArray();
            jsonGenerator.writeString(accountAuth.getKey());
            jsonGenerator.writeNumber(accountAuth.getValue());
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeEndArray();
    }
}

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
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;

/**
 * This class repesents a Steem "comment_options_extenson" object.
 * 
 * The "comment_options_extenson" class is a "static_variant" which can carry
 * different types. The resulting json of th "static_variant" fields looks like
 * this:
 * 
 * <p>
 * [0,{"beneficiaries":[{"account":"esteemapp","weight":500}]}]
 * </p>
 * 
 * The "0" indicates the type, so using <code>@JsonTypeInfo</code> /
 * <code>@JsonSubTypes</code> would be the cleaner solution here. Sadly, Jackson
 * does not allow Integer Ids at the moment.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsExtensionDeserializer extends JsonDeserializer<CommentOptionsExtension> {
    @Override
    public CommentOptionsExtension deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.readValueAsTree();

        if (node == null || node.size() != 2 || !node.get(0).isInt()) {
            throw new IllegalArgumentException("The received JSON does not has the required structure.");
        }

        // Get the core class by mapping the given type id.
        int coreTypeId = node.get(0).asInt();
        if (coreTypeId == 0) {
            return CommunicationHandler.getObjectMapper().treeToValue(node.get(1), CommentPayoutBeneficiaries.class);
        }

        throw new IllegalArgumentException("Unknown extension type id '" + coreTypeId + "'.");
    }
}

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
package eu.bittrade.libs.steemj.protocol.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemTransformationException;
import eu.bittrade.libs.steemj.interfaces.Validatable;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.plugins.apis.follow.models.operations.ReblogOperation;

/**
 * This class is a wrapper for the different kinds of Operations, a
 * {@link CustomJsonOperation} can carry.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY)
@JsonSubTypes({ @Type(value = FollowOperation.class, name = "follow"),
        @Type(value = ReblogOperation.class, name = "reblog") })
public abstract class CustomJsonOperationPayload extends BaseOperation implements Validatable {
    /**
     * Transform the operation into its json representation.
     * 
     * @return The json representation of this operation.
     * @throws SteemTransformationException
     *             If the operation could not be transformed into a valid json
     *             string.
     */
    public String toJson() throws SteemTransformationException {
        try {
            return CommunicationHandler.getObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new SteemTransformationException("Cannot transform operation into its json representation.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

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
package eu.bittrade.libs.steemj.base.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.deserializer.CommentOptionsExtensionDeserializer;
import eu.bittrade.libs.steemj.base.models.serializer.CommentOptionsExtensionSerializer;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.interfaces.Validatable;

/**
 * This class repesents a Steem "comment_options_extenson" object.
 * 
 * The "comment_options_extenson" class is a "static_variant" which can carry
 * different types. The resulting json of the "static_variant" fields looks like
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
@JsonDeserialize(using = CommentOptionsExtensionDeserializer.class)
@JsonSerialize(using = CommentOptionsExtensionSerializer.class)
public abstract class CommentOptionsExtension implements ByteTransformable, Validatable , HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

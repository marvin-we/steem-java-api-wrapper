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

import eu.bittrade.libs.steemj.base.models.deserializer.BlockHeaderExtensionsDeserializer;
import eu.bittrade.libs.steemj.base.models.serializer.BlockHeaderExtensionsSerializer;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class is used as a Java implementation of a variant that can contain a:
 * <ul>
 * <li>void</li>
 * <li>version</li>
 * <li>hardfork_version_vote</li>
 * </ul>
 * 
 * The used type is indicated by an id, which is the first field in a JSON
 * response.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = BlockHeaderExtensionsSerializer.class)
@JsonDeserialize(using = BlockHeaderExtensionsDeserializer.class)
public abstract class BlockHeaderExtensions implements ByteTransformable , HasJsonAnyGetterSetter {
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

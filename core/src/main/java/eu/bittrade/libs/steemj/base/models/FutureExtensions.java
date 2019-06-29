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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.serializer.FutureExtensionsSerializer;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class represents a "future_extensions_type" object which has no member
 * in the current version.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = FutureExtensionsSerializer.class)
public class FutureExtensions implements ByteTransformable , HasJsonAnyGetterSetter {
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
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedFutureExtensions = new ByteArrayOutputStream()) {
            byte[] extension = { 0x00 };
            serializedFutureExtensions.write(extension);

            return serializedFutureExtensions.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

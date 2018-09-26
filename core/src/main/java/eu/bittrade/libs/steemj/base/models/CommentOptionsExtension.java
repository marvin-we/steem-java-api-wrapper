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

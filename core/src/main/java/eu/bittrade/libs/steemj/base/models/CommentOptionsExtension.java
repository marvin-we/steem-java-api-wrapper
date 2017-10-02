package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.deserializer.CommentOptionsExtensionDeserializer;
import eu.bittrade.libs.steemj.base.models.serializer.CommentOptionsExtensionSerializer;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

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
@JsonDeserialize(using = CommentOptionsExtensionDeserializer.class)
@JsonSerialize(using = CommentOptionsExtensionSerializer.class)
public abstract class CommentOptionsExtension implements ByteTransformable {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

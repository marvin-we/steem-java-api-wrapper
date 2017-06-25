package eu.bittrade.libs.steemj.plugins.follow.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.plugins.follow.model.deserializer.FollowTypeDeserializer;
import eu.bittrade.libs.steemj.plugins.follow.model.serializer.FollowTypeSerializer;

/**
 * An enumeration for all existing follow types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize(using = FollowTypeDeserializer.class)
@JsonSerialize(using = FollowTypeSerializer.class)
public enum FollowType {
    UNDEFINED, BLOG, IGNORE
}

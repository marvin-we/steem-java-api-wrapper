package eu.bittrade.libs.steemj.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.bittrade.libs.steemj.base.models.deserializer.CurveIdDeserializer;

/**
 * An enumeration for all existing curve types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize(using = CurveIdDeserializer.class)
public enum CurveId {
    QUADRATIC, QUADRATIC_CURATION, LINEAR, SQUARE_ROOT
}

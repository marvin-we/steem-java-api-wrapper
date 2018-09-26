package eu.bittrade.libs.steemj.interfaces;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public interface HasJsonAnyGetterSetter {
	@JsonAnyGetter
	Map<String, Object> _getter();
	@JsonAnySetter
	void _setter(String key, Object value);
}

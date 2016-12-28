package dez.steemit.com.models.error;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author http://steemit.com/@dez1337
 */
public class SteemData {
	private String name;
	// The error only contains "what" or "api" depending on the kind.
	private String what;
	private String type;
	private Map<String, Integer> api;

	public String getName() {
		return name;
	}

	public String getWhat() {
		return what;
	}
	
	public String getType() {
		return type;
	}
	
	public Map<String, Integer> getApi() {
		return api;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

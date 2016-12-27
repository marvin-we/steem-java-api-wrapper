package dez.steemit.com.models.error;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SteemData {
	private String name;
	private String what;

	public String getName() {
		return name;
	}

	public String getWhat() {
		return what;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

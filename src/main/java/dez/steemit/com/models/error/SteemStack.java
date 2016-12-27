package dez.steemit.com.models.error;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SteemStack {
	private SteemContext context;
	private String format;
	private SteemData data;

	public SteemContext getContext() {
		return context;
	}

	public String getFormat() {
		return format;
	}

	public SteemData getData() {
		return data;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

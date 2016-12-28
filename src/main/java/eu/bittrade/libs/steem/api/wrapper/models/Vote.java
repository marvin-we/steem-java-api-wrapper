package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author http://steemit.com/@dez1337
 */
public class Vote {
	private String authorperm;
	private String weight;
	private String rshares;
	private int percent;
	private Date time;

	public String getAuthorperm() {
		return authorperm;
	}

	public String getWeight() {
		return weight;
	}

	public String getRshares() {
		return rshares;
	}

	public int getPercent() {
		return percent;
	}

	public Date getTime() {
		return time;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}

package eu.bittrade.libs.steemj.plugins.apis.follow.models;

import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class implements the Steem "get_followers_args" object.
 * 
 * @author <a href="http://steemit.com/@paatrick">paatrick</a>
 */
public class GetFollowersArgs {

	@JsonProperty("account")
	private AccountName account;
	@JsonProperty("start")
	private AccountName start;
	@JsonProperty("type")
	private FollowType type;
	@JsonProperty("limit")
	private UInteger limit;
	
	public GetFollowersArgs(@JsonProperty("account") AccountName account, 
			@JsonProperty("start") AccountName start, 
			@JsonProperty("type") FollowType type, 
			@JsonProperty("limit") UInteger limit) {
		this.setAccount(account);
		this.setStart(start);
		this.setType(type);
		this.setLimit(limit);
	}

	public AccountName getAccount() {
		return account;
	}

	public void setAccount(AccountName account) {
		this.account = account;
	}

	public AccountName getStart() {
		return start;
	}

	public void setStart(AccountName start) {
		this.start = start;
	}

	public FollowType getType() {
		return type;
	}

	public void setType(FollowType type) {
		this.type = type;
	}

	public UInteger getLimit() {
		return limit;
	}

	public void setLimit(UInteger limit) {
		this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(1000));
	}
	
}

package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AuthorRewardOperation extends Operation {
    @JsonProperty("author")
    private String author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("sbd_payout")
    private String sbdPayout;
    @JsonProperty("steem_payout")
    private String steemPayout;
    @JsonProperty("vesting_payout")
    private String vestingPayout;

    public String getAuthor() {
        return author;
    }

    public String getPermlink() {
        return permlink;
    }

    public String getSbdPayout() {
        return sbdPayout;
    }

    public String getSteemPayout() {
        return steemPayout;
    }

    public String getVestingPayout() {
        return vestingPayout;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}

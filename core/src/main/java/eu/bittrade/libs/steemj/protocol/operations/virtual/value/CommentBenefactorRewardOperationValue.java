package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class CommentBenefactorRewardOperationValue {
		@JsonProperty("benefactor")
	  	private AccountName benefactor;
		@JsonProperty("author")
	    private AccountName author;
		@JsonProperty("permlink")
	    private Permlink permlink;
		@JsonProperty("sbd_payout")
	    private Asset sbdPayout;
		@JsonProperty("steem_payout")
		private Asset steemPayout;
		@JsonProperty("vesting_payout")
		private Asset vestingPayout;

	    /**
	     * @return the benefactor
	     */
	    public AccountName getBenefactor() {
	        return benefactor;
	    }

	    /**
	     * @return the author
	     */
	    public AccountName getAuthor() {
	        return author;
	    }

	    /**
	     * @return the permlink
	     */
	    public Permlink getPermlink() {
	        return permlink;
	    }

	    /**
	     * @return the sbd payout
	     */
	    public Asset getSbdPayout() {
			return sbdPayout;
		}

	    /**
	     * @return the steem payout
	     */
		public Asset getSteemPayout() {
			return steemPayout;
		}

		/**
	     * @return the vesting payout
	     */
		public Asset getVestingPayout() {
			return vestingPayout;
		}

}

package eu.bittrade.libs.steem.api.wrapper.models;

import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is the java implementation of the Steem "reward_fund_object".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RewardFund {
    // TODO: Original type is "reward_fund_id_type".
    private int id;
    // TODO: Original type is "reward_fund_name_type".
    private String name;
    @JsonProperty("reward_balance")
    private Asset rewardBalance;
    // Original type is uint128_t so we use BigInteger here.
    @JsonProperty("recent_claims")
    private BigInteger recentClaims;
    @JsonProperty("last_update")
    private Date lastUpdate;
    // Original type is uint128_t so we use BigInteger here.
    @JsonProperty("content_constant")
    private BigInteger contentConstant;
    // Original type is uint16_t so we use int here.
    @JsonProperty("percent_curation_rewards")
    private int percentCurationRewards;
    // Original type is uint16_t so we use int here.
    @JsonProperty("percent_content_rewards")
    private int percentContentRewards;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the rewardBalance
     */
    public Asset getRewardBalance() {
        return rewardBalance;
    }

    /**
     * @return the recentClaims
     */
    public BigInteger getRecentClaims() {
        return recentClaims;
    }

    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @return the contentConstant
     */
    public BigInteger getContentConstant() {
        return contentConstant;
    }

    /**
     * @return the percentCurationRewards
     */
    public int getPercentCurationRewards() {
        return percentCurationRewards;
    }

    /**
     * @return the percentContentRewards
     */
    public int getPercentContentRewards() {
        return percentContentRewards;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

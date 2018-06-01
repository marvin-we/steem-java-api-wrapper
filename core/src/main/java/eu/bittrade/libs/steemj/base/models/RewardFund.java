package eu.bittrade.libs.steemj.base.models;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.enums.CurveId;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class is the java implementation of the Steem "reward_fund_object".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RewardFund implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    // Original type is "reward_fund_id_type".
    private long id;
    // Original type is "reward_fund_name_type".
    private String name;
    @JsonProperty("reward_balance")
    private Asset rewardBalance;
    // Original type is uint128_t so we use BigInteger here.
    @JsonProperty("recent_claims")
    private BigInteger recentClaims;
    @JsonProperty("last_update")
    private TimePointSec lastUpdate;
    // Original type is uint128_t so we use BigInteger here.
    @JsonProperty("content_constant")
    private BigInteger contentConstant;
    // Original type is uint16_t so we use int here.
    @JsonProperty("percent_curation_rewards")
    private int percentCurationRewards;
    // Original type is uint16_t so we use int here.
    @JsonProperty("percent_content_rewards")
    private int percentContentRewards;
    // TODO: Original type is curve id.
    @JsonProperty("author_reward_curve")
    private CurveId authorRewardCurve;
    @JsonProperty("curation_reward_curve")
    private CurveId curationRewardCurve;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private RewardFund() {
    }

    /**
     * @return the id
     */
    public long getId() {
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
    public TimePointSec getLastUpdate() {
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

    /**
     * @return the authorRewardCurve
     */
    public CurveId getAuthorRewardCurve() {
        return authorRewardCurve;
    }

    /**
     * @return the curationRewardCurve
     */
    public CurveId getCurationRewardCurve() {
        return curationRewardCurve;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UShort;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.protocol.Asset;
import eu.bittrade.libs.steemj.protocol.enums.CurveId;

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
    // Original type is uint16_t.
    @JsonProperty("percent_curation_rewards")
    private UShort percentCurationRewards;
    // Original type is uint16_t.
    @JsonProperty("percent_content_rewards")
    private UShort percentContentRewards;
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
    public UShort getPercentCurationRewards() {
        return percentCurationRewards;
    }

    /**
     * @return the percentContentRewards
     */
    public UShort getPercentContentRewards() {
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

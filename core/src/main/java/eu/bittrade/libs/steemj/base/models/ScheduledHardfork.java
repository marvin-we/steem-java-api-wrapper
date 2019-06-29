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
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.base.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.protocol.HardforkVersion;

/**
 * This object represents the Steem "scheduled_hardfork" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ScheduledHardfork implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    @JsonProperty("hf_version")
    private HardforkVersion hardforkVersion;
    @JsonProperty("live_time")
    private TimePointSec liveTime;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ScheduledHardfork() {
    }

    /**
     * @return The next expected Hardfork Version.
     */
    public HardforkVersion getHardforkVersion() {
        return hardforkVersion;
    }

    /**
     * @return The time when the next Hardfork is planned.
     */
    public TimePointSec getLiveTime() {
        return liveTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

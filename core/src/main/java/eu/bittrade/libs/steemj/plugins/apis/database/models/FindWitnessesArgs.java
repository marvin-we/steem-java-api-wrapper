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

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "find_witnesses_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindWitnessesArgs {
    @JsonProperty("owners")
    private List<AccountName> owners;

    /**
     * 
     * @param owners
     */
    @JsonCreator()
    public FindWitnessesArgs(@JsonProperty("owners") List<AccountName> owners) {
        this.setOwners(owners);
    }

    /**
     * @return the owners
     */
    public List<AccountName> getOwners() {
        return owners;
    }

    /**
     * @param owners
     *            the owners to set
     */
    public void setOwners(List<AccountName> owners) {
        this.owners = owners;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

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
package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.EscrowObject;

/**
 * This class represents a Steem "list_escrows_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListEscrowsReturn {
    @JsonProperty("escrows")
    private List<EscrowObject> escrows;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to <code>protected</code> as this class is the parent of
     * the {@link FindEscrowsReturn} class.
     */
    protected ListEscrowsReturn() {
    }

    /**
     * @return the escrows
     */
    public List<EscrowObject> getEscrows() {
        return escrows;
    }

    /**
     * @param escrows
     *            the escrows to set
     */
    public void setEscrows(List<EscrowObject> escrows) {
        this.escrows = escrows;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

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
package eu.bittrade.libs.steemj.protocol;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;

/**
 * This class is the java implementation of the Steem "signed_block" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SignedBlock extends SignedBlockHeader {
    @JsonProperty("transactions")
    protected List<SignedTransaction> transactions;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to protected as this object is the parent of
     * {@link ExtendedSignedBlock}.
     */
    protected SignedBlock() {
    }

    /**
     * @return A list of transactions processed in this block.
     */
    public List<SignedTransaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

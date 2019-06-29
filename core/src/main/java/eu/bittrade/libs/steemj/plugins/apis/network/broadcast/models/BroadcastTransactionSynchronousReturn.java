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
package eu.bittrade.libs.steemj.plugins.apis.network.broadcast.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.protocol.TransactionId;

/**
 * This class represents a Steem "broadcast_transaction_synchronous_return"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BroadcastTransactionSynchronousReturn implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    @JsonProperty("id")
    private TransactionId id;
    @JsonProperty("block_num")
    private int blockNum = 0;
    @JsonProperty("trx_num")
    private int trxNum = 0;
    @JsonProperty("expired")
    private boolean expired = false;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected BroadcastTransactionSynchronousReturn() {
    }

    /**
     * Get the Id of the applied transaction.
     * 
     * @return The transaction Id.
     */
    public TransactionId getId() {
        return id;
    }

    /**
     * Get the block number the applied transaction has been processed with.
     * 
     * @return The block number.
     */
    public int getBlockNum() {
        return blockNum;
    }

    /**
     * Get the transaction number inside the block.
     * 
     * @return The transaction number.
     */
    public int getTrxNum() {
        return trxNum;
    }

    /**
     * Check if the applied transaction is already expired.
     * 
     * @return <code>True</code> if the transaction is already expired or
     *         <code>false</code> if not.
     */
    public boolean isExpired() {
        return expired;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

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
package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.plugins.apis.account.history.models.deserializer.OperationWrapper;
import eu.bittrade.libs.steemj.protocol.TransactionId;

/**
 * This class is the java implementation of the Steem "api_operation_object"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AppliedOperation {
    @JsonProperty("trx_id")
    private TransactionId trxId;
    // Original type is uint32_t.
    @JsonProperty("block")
    private UInteger block;
    // Original type is uint32_t.
    @JsonProperty("trx_in_block")
    private UInteger trxInBlock;
    // Original type is uint16_t.
    @JsonProperty("op_in_trx")
    private UShort opInTrx;
    // Original type is uint64_t.
    @JsonProperty("virtual_op")
    private ULong virtualOp;
    @JsonProperty("timestamp")
    private TimePointSec timestamp;
    @JsonProperty("op")
    private OperationWrapper operationWrapper;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private AppliedOperation() {
    }

    /**
     * Get the id of this transaction.
     * 
     * @return The transaction id.
     */
    public TransactionId getTrxId() {
        return trxId;
    }

    /**
     * Get the block number.
     * 
     * @return The block number.
     */
    public UInteger getBlock() {
        return block;
    }

    /**
     * Get the number of transactions inside the block.
     * 
     * @return The number of transactions inside the block.
     */
    public UInteger getTrxInBlock() {
        return trxInBlock;
    }

    /**
     * Get the number of operations inside the transaction.
     * 
     * @return The number of operations inside the transaction.
     */
    public UShort getOpInTrx() {
        return opInTrx;
    }

    /**
     * Get the number of virtual operations inside the transaction.
     * 
     * @return The number of virtual operations inside the transaction.
     */
    public ULong getVirtualOp() {
        return virtualOp;
    }

    /**
     * Get the time point at which this transaction has been submitted.
     * 
     * @return The submission date and time.
     */
    public TimePointSec getTimestamp() {
        return timestamp;
    }

    /**
     * Get a wrapper object which stores the whole operation object.
     * 
     * @return The operation object.
     */
    public OperationWrapper getOperationWrapper() {
        return operationWrapper;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

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
package eu.bittrade.libs.steemj.protocol;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "annotated_signed_transaction" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AnnotatedSignedTransaction implements Serializable {
    /** Generated serial version uid. */
    private static final long serialVersionUID = 1737019021825341056L;
    @JsonProperty("transaction_id")
    protected TransactionId transactionId;
    @JsonProperty("block_num")
    protected int blockNum;
    @JsonProperty("transaction_num")
    protected int transactionNum;

    /**
     * This object is only returned from some methods. As there is no need to
     * initiate it, the constructor is private.
     */
    private AnnotatedSignedTransaction() {
        // Apply default values:
        this.blockNum = 0;
        this.transactionNum = 0;
    }

    /**
     * @return the transactionId
     */
    public TransactionId getTransactionId() {
        return transactionId;
    }

    /**
     * @return the blockNum
     */
    public int getBlockNum() {
        return blockNum;
    }

    /**
     * @return the transactionNum
     */
    public int getTransactionNum() {
        return transactionNum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

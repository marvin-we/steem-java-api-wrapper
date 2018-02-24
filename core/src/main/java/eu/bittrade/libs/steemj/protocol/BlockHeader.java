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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.BlockHeaderExtensions;
import eu.bittrade.libs.steemj.base.models.BlockId;
import eu.bittrade.libs.steemj.base.models.Checksum;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.fc.TimePointSec;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockHeader implements ByteTransformable {
    protected BlockId previous;
    protected TimePointSec timestamp;
    protected AccountName witness;
    @JsonProperty("transaction_merkle_root")
    protected Checksum transactionMerkleRoot;
    // Original type is "block_header_extensions_type" which is an array of
    // "block_header_extensions".
    protected List<BlockHeaderExtensions> extensions;

    /**
     * @return The block id of the previous block.
     */
    public BlockId getPrevious() {
        return previous;
    }

    /**
     * @param previous
     *            The block id of the previous block to set.
     */
    public void setPrevious(BlockId previous) {
        this.previous = previous;
    }

    /**
     * @return the witness
     */
    public AccountName getWitness() {
        return witness;
    }

    /**
     * @param witness
     *            the witness to set
     */
    public void setWitness(AccountName witness) {
        this.witness = witness;
    }

    /**
     * @return the transactionMerkleRoot
     */
    public Checksum getTransactionMerkleRoot() {
        return transactionMerkleRoot;
    }

    /**
     * @param transactionMerkleRoot
     *            the transactionMerkleRoot to set
     */
    public void setTransactionMerkleRoot(Checksum transactionMerkleRoot) {
        this.transactionMerkleRoot = transactionMerkleRoot;
    }

    /**
     * Get the list of configured extensions.
     * 
     * @return All extensions.
     */
    public List<BlockHeaderExtensions> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        return extensions;
    }

    /**
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     *            Define a list of extensions.
     */
    public void setExtensions(List<BlockHeaderExtensions> extensions) {
        this.extensions = extensions;
    }

    /**
     * @return the timestamp
     */
    public TimePointSec getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(TimePointSec timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        // serializedRecoverAccountOperation
        // .write(SteemJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
        return null;
    }
}

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
package eu.bittrade.libs.steemj.plugins.apis.block.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.block.BlockApi;

/**
 * This class is the java implementation of the Steem "get_block_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetBlockArgs {
    @JsonProperty("block_num")
    private UInteger blockNumber;

    // TODO: Original: Height of the block to be returned.
    /**
     * Create a new {@link GetBlockHeaderArgs} instance to be passed to the
     * {@link BlockApi#getBlock(CommunicationHandler, UInteger)} method.
     * 
     * @param blockNumber
     *            The block number to search for.
     */
    @JsonCreator()
    public GetBlockArgs(@JsonProperty("block_num") UInteger blockNumber) {
        this.setBlockNumber(blockNumber);
    }

    /**
     * @return The currently configured block number to search for.
     */
    public UInteger getBlockNumber() {
        return blockNumber;
    }

    /**
     * @param blockNumber
     *            The block number to search for.
     */
    public void setBlockNumber(UInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

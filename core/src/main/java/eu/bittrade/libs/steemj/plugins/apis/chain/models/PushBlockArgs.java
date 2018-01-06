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
package eu.bittrade.libs.steemj.plugins.apis.chain.models;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.SignedBlock;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is the java implementation of the Steem "push_block_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PushBlockArgs {
    @JsonProperty("block")
    private SignedBlock block;
    @JsonProperty("currently_syncing")
    private boolean currentlySyncing;

    /**
     * 
     * @param block
     * @param currentlySyncing
     */
    public PushBlockArgs(@JsonProperty("block") SignedBlock block,
            @Nullable @JsonProperty("currently_syncing") boolean currentlySyncing) {
        this.setBlock(block);
        this.setCurrentlySyncing(currentlySyncing);
    }

    /**
     * @return the block
     */
    public SignedBlock getBlock() {
        return block;
    }

    /**
     * @param block
     *            the block to set
     */
    public void setBlock(SignedBlock block) {
        this.block = SteemJUtils.setIfNotNull(block, "The block needs to be provided.");
    }

    /**
     * @return the currentlySyncing
     */
    public boolean isCurrentlySyncing() {
        return currentlySyncing;
    }

    /**
     * @param currentlySyncing
     *            the currentlySyncing to set
     */
    public void setCurrentlySyncing(boolean currentlySyncing) {
        this.currentlySyncing = SteemJUtils.setIfNotNull(currentlySyncing, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

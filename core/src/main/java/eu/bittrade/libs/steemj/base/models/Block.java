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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;

/**
 * This class is the java implementation of the Steem "block_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Block extends ExtendedSignedBlock {
    // Original type is comment_id_type.
    private long id;
    // Original type is uint32_t so we use long here.
    @JsonProperty("ref_prefix")
    private long refPrefix;
    // Original type is uint32_t so we use long here.
    @JsonProperty("block_num")
    private long blockNum;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Block() {
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the refPrefix
     */
    public long getRefPrefix() {
        return refPrefix;
    }

    /**
     * @return the blockNum
     */
    public long getBlockNum() {
        return blockNum;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

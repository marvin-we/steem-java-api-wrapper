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

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.crypto.core.ECKey.ECDSASignature;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class is the java implementation of the Steem "signed_block_header"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SignedBlockHeader extends BlockHeader implements ByteTransformable {
    @JsonProperty("witness_signature")
    protected String witnessSignature;

    /**
     * @return the witnessSignature
     */
    public ECDSASignature getWitnessSignature() {
        return ECDSASignature.decodeFromDER(CryptoUtils.HEX.decode(witnessSignature));
    }

    /**
     * @param witnessSignature
     *            the witnessSignature to set
     */
    public void setWitnessSignature(String witnessSignature) {
        this.witnessSignature = witnessSignature;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        super.toByteArray();
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

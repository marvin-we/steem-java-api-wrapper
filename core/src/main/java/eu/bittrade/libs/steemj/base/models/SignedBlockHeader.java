package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class is the java implementation of the Steem "signed_block_header"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SignedBlockHeader extends BlockHeader implements ByteTransformable {
    // TODO: Original type is "signature_type".
    @JsonProperty("witness_signature")
    protected String witnessSignature;

    /**
     * @return the witnessSignature
     */
    public String getWitnessSignature() {
        return witnessSignature;
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

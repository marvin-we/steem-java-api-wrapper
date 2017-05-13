package eu.bittrade.libs.steem.api.wrapper.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignedBlockHeader extends BlockHeader {
    // TODO: Original type is "signature_type".
    @JsonProperty("witness_signature")
    private String witnessSignature;

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
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

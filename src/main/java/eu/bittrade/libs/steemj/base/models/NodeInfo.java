package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class NodeInfo {
    private String blockchainVersion;
    private String steemRevision;
    private String fcRevision;

    @JsonProperty("blockchain_version")
    public String getBlockchainVersion() {
        return blockchainVersion;
    }

    @JsonProperty("steem_revision")
    public String getSteemRevision() {
        return steemRevision;
    }

    @JsonProperty("fc_revision")
    public String getFcRevision() {
        return fcRevision;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

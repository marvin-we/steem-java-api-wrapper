package eu.bittrade.libs.steemj.base.models;

import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class is the java implementation of the Steem "hardfork_version_vote"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize
@JsonSerialize
public class HardforkVersionVote extends BlockHeaderExtensions {
    @JsonProperty("hf_version")
    protected HardforkVersion hfVersion;
    @JsonProperty("hf_time")
    protected TimePointSec hfTime;

    /**
     * Create a new hardfork version vote object.
     * 
     * @param hfVersion
     *            The hardfork version to set.
     * @param hfTime
     *            The hardfork time to set.
     */
    @JsonCreator
    public HardforkVersionVote(@JsonProperty("hf_version") HardforkVersion hfVersion,
            @JsonProperty("hf_time") TimePointSec hfTime) {
        this.setHfTime(hfTime);
        this.setHfVersion(hfVersion);
    }

    /**
     * @return the hfVersion
     */
    public HardforkVersion getHfVersion() {
        return hfVersion;
    }

    /**
     * @param hfVersion
     *            the hfVersion to set
     */
    public void setHfVersion(HardforkVersion hfVersion) {
        this.hfVersion = hfVersion;
    }

    /**
     * @return the hfTime
     */
    public TimePointSec getHfTime() {
        return hfTime;
    }

    /**
     * @param hfTime
     *            the hfTime to set
     */
    public void setHfTime(TimePointSec hfTime) {
        this.hfTime = hfTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedHardforkVersionVoteExtension = new ByteArrayOutputStream()) {
            serializedHardforkVersionVoteExtension.write(this.getHfVersion().toByteArray());
            serializedHardforkVersionVoteExtension.write(this.getHfTime().toByteArray());

            return serializedHardforkVersionVoteExtension.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }
}

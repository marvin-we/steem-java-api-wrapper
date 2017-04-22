package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import javax.activity.InvalidActivityException;

import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.VarInt;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperation extends Operation {
    @JsonProperty("voter")
    private String voter;
    @JsonProperty("author")
    private String author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("weight")
    private short weight;

    public VoteOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getVoter() {
        return voter;
    }

    public String getAuthor() {
        return author;
    }

    public String getPermlink() {
        return permlink;
    }

    public short getWeight() {
        return weight;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    /**
     * Set the weight of the vote in percent. (max. is 10000 which is a weight
     * of 100%)
     * 
     * @param weight
     * @throws InvalidActivityException
     *             If the weight is greater than 10000.
     */
    public void setWeight(short weight) throws InvalidActivityException {
        if (this.weight > 10000) {
            throw new InvalidActivityException("The weight can't be higher than 10000 which is equivalent to 100%.");
        }
        this.weight = weight;
    }

    @Override
    public byte[] toByteArray() throws UnsupportedEncodingException {
        byte[] serializedVoteOperation = {};

        VarInt operationType = new VarInt(OperationType.VOTE_OPERATION.ordinal());
        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation, operationType.encode());

        // Serializing the voter name is done in two steps: 1. Length as VarInt
        // 2.
        // The account name.
        VarInt voterAccountNameLength = new VarInt(this.voter.length());
        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation, voterAccountNameLength.encode());

        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation,
                ByteBuffer.allocate(voter.length()).put(this.voter.getBytes(StandardCharsets.US_ASCII)).array());

        // Same procedure for the author.
        VarInt authorAccountNameLength = new VarInt(this.author.length());
        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation, authorAccountNameLength.encode());

        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation,
                ByteBuffer.allocate(author.length()).put(this.author.getBytes(StandardCharsets.US_ASCII)).array());

        // Same procedure for the permanent link.
        VarInt permaLinkLength = new VarInt(this.permlink.length());
        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation, permaLinkLength.encode());

        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation,
                ByteBuffer.allocate(permlink.length()).put(this.permlink.getBytes(StandardCharsets.US_ASCII)).array());

        serializedVoteOperation = ArrayUtils.addAll(serializedVoteOperation,
                ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(this.weight).array());

        return serializedVoteOperation;
    }
}

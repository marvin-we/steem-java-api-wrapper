package eu.bittrade.libs.steem.api.wrapper.communication.dto;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.VarInt;

import eu.bittrade.libs.steem.api.wrapper.util.OperationTypes;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteDTO extends OperationDTO {
    private String author;
    private String permlink;
    private String voter;
    private short weight;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPermlink() {
        return permlink;
    }

    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public short getWeight() {
        return weight;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    @Override
    byte[] serializeOperation() throws UnsupportedEncodingException {
        byte[] serializedVote = {};

        VarInt operationType = new VarInt(OperationTypes.VOTE_OPERATION.ordinal());
        serializedVote = ArrayUtils.addAll(serializedVote, operationType.encode());

        // Serialize the voter name is done in two steps: 1. Length as VarInt 2.
        // The account name.
        VarInt voterAccountNameLength = new VarInt(this.voter.length());
        serializedVote = ArrayUtils.addAll(serializedVote, voterAccountNameLength.encode());

        serializedVote = ArrayUtils.addAll(serializedVote, ByteBuffer.allocate(voter.length())
                .order(ByteOrder.LITTLE_ENDIAN).put(this.voter.getBytes("UTF-8")).array());

        // Same procedure for the author.
        VarInt authorAccountNameLength = new VarInt(this.author.length());
        serializedVote = ArrayUtils.addAll(serializedVote, authorAccountNameLength.encode());

        serializedVote = ArrayUtils.addAll(serializedVote, ByteBuffer.allocate(author.length())
                .order(ByteOrder.LITTLE_ENDIAN).put(this.author.getBytes("UTF-8")).array());

        // Same procedure for the permanent link.
        VarInt permaLinkLength = new VarInt(this.permlink.length());
        serializedVote = ArrayUtils.addAll(serializedVote, permaLinkLength.encode());

        serializedVote = ArrayUtils.addAll(serializedVote, ByteBuffer.allocate(permlink.length())
                .order(ByteOrder.LITTLE_ENDIAN).put(this.permlink.getBytes("UTF-8")).array());

        serializedVote = ArrayUtils.addAll(serializedVote,
                ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(this.weight).array());

        return serializedVote;
    }
}

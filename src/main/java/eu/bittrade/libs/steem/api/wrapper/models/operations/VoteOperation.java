package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.activity.InvalidActivityException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.util.SteemUtils;

/**
 * This class represents the Steem "vote_operation" object.
 * 
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
     *            The weight of this vote.
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
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedVoteOperation = new ByteArrayOutputStream()) {
            serializedVoteOperation.write(SteemUtils.transformIntToVarIntByteArray(OperationType.VOTE_OPERATION.ordinal()));
            serializedVoteOperation.write(SteemUtils.transformStringToVarIntByteArray(this.voter));
            serializedVoteOperation.write(SteemUtils.transformStringToVarIntByteArray(this.author));
            serializedVoteOperation.write(SteemUtils.transformStringToVarIntByteArray(this.permlink));
            serializedVoteOperation.write(SteemUtils.transformShortToByteArray(this.weight));

            return serializedVoteOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

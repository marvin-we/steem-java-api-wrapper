package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "vote_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VoteOperation extends Operation {
    @JsonProperty("voter")
    private AccountName voter;
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;
    @JsonProperty("weight")
    private short weight;

    /**
     * Create a new vote operation to vote for a comment or a post.
     * 
     * @param voter
     *            Set the account that votes. {@link #setVoter(AccountName)}.
     * @param author
     *            Set the author of the post/comment to vote for.
     *            {@link #setAuthor(AccountName)}.
     * @param permlink
     *            Set the permanent link of the post/comment to vote for.
     *            {@link #setPermlink(Permlink)}.
     * @param weight
     *            Set the voting weight. {@link #setWeight(short)}.
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    @JsonCreator
    public VoteOperation(@JsonProperty("voter") AccountName voter, @JsonProperty("author") AccountName author,
            @JsonProperty("permlink") Permlink permlink, @JsonProperty("weight") short weight) {
        super(false);
        // Set default values:
        this.setVoter(voter);
        this.setAuthor(author);
        this.setPermlink(permlink);
        this.setWeight(weight);
    }

    /**
     * Like {@link #VoteOperation(AccountName, AccountName, Permlink, short)},
     * but will use a default weight of '0'.
     * 
     * @param voter
     *            Set the account that votes. {@link #setVoter(AccountName)}.
     * @param author
     *            Set the author of the post/comment to vote for.
     *            {@link #setAuthor(AccountName)}.
     * @param permlink
     *            Set the permanent link of the post/comment to vote for.
     *            {@link #setPermlink(Permlink)}.
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public VoteOperation(AccountName voter, AccountName author, Permlink permlink) {
        super(false);
        // Set default values:
        this.setVoter(voter);
        this.setAuthor(author);
        this.setPermlink(permlink);
        this.setWeight((short) 0);
    }

    /**
     * Get the account that has performed the vote.
     * 
     * @return The account that has performed the vote.
     */
    public AccountName getVoter() {
        return voter;
    }

    /**
     * Get the author of the post or comment that has been voted for.
     * 
     * @return The author of the post or comment that has been voted for.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * Get the permanent link of the post or comment that has been voted for.
     * 
     * @return The permanent link of the post or comment that has been voted
     *         for.
     */
    public Permlink getPermlink() {
        return permlink;
    }

    /**
     * Get the weight with that the user has voted for a comment or post.
     * 
     * @return The weight with that the user has voted for a comment or post.
     */
    public short getWeight() {
        return weight;
    }

    /**
     * Set the account name that should perform the vote. <b>Notice:</b> The
     * private posting key of this account needs to be stored in the key
     * storage.
     * 
     * @param voter
     *            The account name that should perform the vote.
     * @throws InvalidParameterException
     *             If no voter is provided.
     */
    public void setVoter(AccountName voter) {
        this.voter = setIfNotNull(voter, "An voter needs to be provided.");
    }

    /**
     * Set the author of the post or comment that should be voted for.
     * 
     * @param author
     *            The author of the post or comment that should be voted for.
     * @throws InvalidParameterException
     *             If no author is provided.
     */
    public void setAuthor(AccountName author) {
        this.author = setIfNotNull(author, "An author needs to be provided.");
    }

    /**
     * Set the permanent link of the post or comment that should be voted for.
     * 
     * @param permlink
     *            The permanent link of the post or comment that should be voted
     *            for.
     * @throws InvalidParameterException
     *             If no permlink has been provided.
     */
    public void setPermlink(Permlink permlink) {
        this.permlink = setIfNotNull(permlink, "A permlink needs to be provided.");
    }

    /**
     * Set the weight of the vote in percent. (max. is 10000 which is a weight
     * of 100%)
     * 
     * @param weight
     *            The weight of this vote.
     * @throws InvalidParameterException
     *             If the weight is greater than 10000 or less than -10000.
     */
    public void setWeight(short weight) {
        this.weight = weight;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedVoteOperation = new ByteArrayOutputStream()) {
            serializedVoteOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.VOTE_OPERATION.getOrderId()));
            serializedVoteOperation.write(this.getVoter().toByteArray());
            serializedVoteOperation.write(this.getAuthor().toByteArray());
            serializedVoteOperation.write(this.getPermlink().toByteArray());
            serializedVoteOperation.write(SteemJUtils.transformShortToByteArray(this.getWeight()));

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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getVoter(), PrivateKeyType.POSTING);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)) {
            if (weight > 10000) {
                throw new InvalidParameterException(
                        "The voting weight can't be higher than 10000 which is equivalent to 100%.");
            } else if (weight < -10000) {
                throw new InvalidParameterException(
                        "The voting weight can't be lower than -10000 which is equivalent to -100%.");
            }
        }
    }
}

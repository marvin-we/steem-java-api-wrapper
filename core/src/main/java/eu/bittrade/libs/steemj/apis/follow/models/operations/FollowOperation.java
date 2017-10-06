package eu.bittrade.libs.steemj.apis.follow.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.apis.follow.enums.FollowType;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "follow_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowOperation extends Operation {
    private AccountName follower;
    private AccountName following;
    private List<FollowType> what;

    /**
     * Create a new follow operation to follow or ignore a user.
     * <b>Attention</b> This operation is currently (HF 0.19) disabled. Please
     * use a
     * {@link eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation
     * CustomJsonOperation] instead.}
     */
    public FollowOperation() {
        super(false);
    }

    /**
     * Get the account who follows the {@link #getFollowing()} account.
     * 
     * @return The account name of the follower.
     */
    public AccountName getFollower() {
        return follower;

    }

    /**
     * Set the account who follows the {@link #getFollowing()} account.
     * <b>Notice:</b> The private posting key of this account needs to be stored
     * in the key storage.
     * 
     * @param follower
     *            The account name of the follower
     */
    public void setFollower(AccountName follower) {
        this.follower = follower;
    }

    /**
     * @return the following
     */
    public AccountName getFollowing() {
        return following;
    }

    /**
     * @param following
     *            the following to set
     */
    public void setFollowing(AccountName following) {
        this.following = following;
    }

    /**
     * @return the what
     */
    public List<FollowType> getWhat() {
        return what;
    }

    /**
     * @param what
     *            the what to set
     */
    public void setWhat(List<FollowType> what) {
        this.what = what;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedVoteOperation = new ByteArrayOutputStream()) {
            serializedVoteOperation.write(this.getFollower().toByteArray());
            serializedVoteOperation.write(this.getFollowing().toByteArray());

            for (FollowType followType : this.getWhat()) {
                serializedVoteOperation
                        .write(SteemJUtils.transformStringToVarIntByteArray(followType.toString().toLowerCase()));
            }

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
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getFollower(), PrivateKeyType.POSTING);
    }
}

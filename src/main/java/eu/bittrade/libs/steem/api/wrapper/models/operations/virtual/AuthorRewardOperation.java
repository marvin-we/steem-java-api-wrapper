package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;
import eu.bittrade.libs.steem.api.wrapper.util.Utils;

/**
 * This class represents the Steem "author_reward_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AuthorRewardOperation extends Operation {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("sbd_payout")
    private Asset sbdPayout;
    @JsonProperty("steem_payout")
    private Asset steemPayout;
    @JsonProperty("vesting_payout")
    private Asset vestingPayout;

    /**
     * Create a new author reward operation.
     */
    public AuthorRewardOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * 
     * @return
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     */
    public void setAuthor(AccountName author) {
        this.author = author;
    }

    /**
     * 
     * @return
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * 
     * @param permlink
     */
    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    /**
     * 
     * @return
     */
    public Asset getSbdPayout() {
        return sbdPayout;
    }

    /**
     * 
     * @param sbdPayout
     */
    public void setSbdPayout(Asset sbdPayout) {
        this.sbdPayout = sbdPayout;
    }

    /**
     * 
     * @return
     */
    public Asset getSteemPayout() {
        return steemPayout;
    }

    /**
     * 
     * @param steemPayout
     */
    public void setSteemPayout(Asset steemPayout) {
        this.steemPayout = steemPayout;
    }

    /**
     * 
     * @return
     */
    public Asset getVestingPayout() {
        return vestingPayout;
    }

    /**
     * 
     * @param vestingPayout
     */
    public void setVestingPayout(Asset vestingPayout) {
        this.vestingPayout = vestingPayout;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAuthorRewardOperation = new ByteArrayOutputStream()) {
            serializedAuthorRewardOperation.write(Utils.transformIntToVarIntByteArray(OperationType.AUTHOR_REWARD_OPERATION.ordinal()));
            serializedAuthorRewardOperation.write(this.author.toByteArray());
            serializedAuthorRewardOperation.write(Utils.transformStringToVarIntByteArray(this.permlink));
            serializedAuthorRewardOperation.write(this.sbdPayout.toByteArray());
            serializedAuthorRewardOperation.write(this.steemPayout.toByteArray());
            serializedAuthorRewardOperation.write(this.vestingPayout.toByteArray());

            return serializedAuthorRewardOperation.toByteArray();
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

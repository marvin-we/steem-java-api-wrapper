package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;
import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "comment_options_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperation extends Operation {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("max_accepted_payout")
    private Asset maxAcceptedPayout;
    @JsonProperty("percent_steem_dollars")
    private Short percentSteemDollars;
    @JsonProperty("allow_votes")
    private Boolean allowVotes;
    @JsonProperty("allow_curation_rewards")
    private Boolean allowCurationRewards;
    // TODO: Fix type: comment_options_extensions_type
    @JsonProperty("extensions")
    private List<Object> extensions;

    /**
     * Create a new comment options operation. Use this operation to define
     * additional options for an already existing post or a comment.
     * 
     * Authors of posts may not want all of the benefits that come from creating
     * a post. This operation allows authors to update properties associated
     * with their post.
     *
     * The max_accepted_payout may be decreased, but never increased. The
     * percent_steem_dollars may be decreased, but never increased
     *
     */
    public CommentOptionsOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
        // Set default values:
        Asset maxAcceptedPayout = new Asset();
        maxAcceptedPayout.setAmount(1000000000);
        maxAcceptedPayout.setSymbol(AssetSymbolType.SBD);
        this.setMaxAcceptedPayout(maxAcceptedPayout);
        this.setPercentSteemDollars((short) 10000);
        this.setAllowVotes(true);
        this.setAllowCurationRewards(true);
    }

    /**
     * Get the author of the comment.
     * 
     * @return The account name of the author.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * Set the author of the comment.
     * 
     * @param author
     *            The account name of the author.
     */
    public void setAuthor(AccountName author) {
        this.author = author;
    }

    /**
     * Get the permanent link of this comment.
     * 
     * @return The permanent link.
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * Set the permanent link of this comment.
     * 
     * @param permlink
     *            The permanent link.
     */
    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    /**
     * Get the SBD value of the maximum payout this post will receive.
     * 
     * @return The SBD value of the maximum payout this post will receive.
     */
    public Asset getMaxAcceptedPayout() {
        return maxAcceptedPayout;
    }

    /**
     * Set the SBD value of the maximum payout this post will receive.
     * 
     * @param maxAcceptedPayout
     *            The SBD value of the maximum payout this post will receive.
     */
    public void setMaxAcceptedPayout(Asset maxAcceptedPayout) {
        this.maxAcceptedPayout = maxAcceptedPayout;
    }

    /**
     * Get the information if votes have been allowed on this post or comment.
     * 
     * @return True if votes are allowed or false if not.
     */
    public Boolean getAllowVotes() {
        return allowVotes;
    }

    /**
     * Define if votes have been allowed on this post or comment.
     * 
     * @param allowVotes
     *            The information if votes have been allowed on this post or
     *            comment.
     */
    public void setAllowVotes(Boolean allowVotes) {
        this.allowVotes = allowVotes;
    }

    /**
     * Get the information if voters are allowed to receive curation rewards.
     * 
     * @return True if votes curation rewards are paid or false if not.
     */
    public Boolean getAllowCurationRewards() {
        return allowCurationRewards;
    }

    /**
     * Define if voters are allowed to receive curation rewards.
     * 
     * @param allowCurationRewards
     *            True if votes curation rewards are paid or false if not.
     */
    public void setAllowCurationRewards(Boolean allowCurationRewards) {
        this.allowCurationRewards = allowCurationRewards;
    }

    /**
     * Get the percent of Steem Dollars to keep, unkept amounts will be received
     * as Steem Power. The default value is 10000 which is equal to 100%.
     * 
     * @return The percent of Steem Dollars.
     */
    public int getPercentSteemDollars() {
        return (int) percentSteemDollars;
    }

    /**
     * 
     * @param percentSteemDollars
     *            The percent of Steem Dollars.
     */
    public void setPercentSteemDollars(Short percentSteemDollars) {
        this.percentSteemDollars = percentSteemDollars;
    }

    /**
     * Get the list of configured extensions.
     * 
     * @return All extensions.
     */
    public List<Object> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }

        return extensions;
    }

    /**
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     *            Define a list of extensions.
     */
    public void setExtensions(List<Object> extensions) {
        this.extensions = extensions;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentOptionsOperation = new ByteArrayOutputStream()) {
            serializedCommentOptionsOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.COMMENT_OPTIONS_OPERATION.ordinal()));
            serializedCommentOptionsOperation.write(this.getAuthor().toByteArray());
            serializedCommentOptionsOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getPermlink()));
            serializedCommentOptionsOperation.write(this.getMaxAcceptedPayout().toByteArray());
            serializedCommentOptionsOperation
                    .write(SteemJUtils.transformShortToByteArray(this.getPercentSteemDollars()));
            serializedCommentOptionsOperation.write(SteemJUtils.transformBooleanToByteArray(this.getAllowVotes()));
            serializedCommentOptionsOperation
                    .write(SteemJUtils.transformBooleanToByteArray(this.getAllowCurationRewards()));
            // TODO: Handle Extensions.For now we just append an empty byte.
            byte[] extension = { 0x00 };
            serializedCommentOptionsOperation.write(extension);

            return serializedCommentOptionsOperation.toByteArray();
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

package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.annotations.SignatureRequired;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "comment_options_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperation extends Operation {
    @SignatureRequired(type = PrivateKeyType.POSTING)
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;
    @JsonProperty("max_accepted_payout")
    private Asset maxAcceptedPayout;
    @JsonProperty("percent_steem_dollars")
    private Short percentSteemDollars;
    @JsonProperty("allow_votes")
    private Boolean allowVotes;
    @JsonProperty("allow_curation_rewards")
    private Boolean allowCurationRewards;
    // Original type is "comment_options_extensions_type" which is a list of
    // "comment_options_extension".
    @JsonProperty("extensions")
    private List<CommentOptionsExtension> extensions;

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
        super(false);
        // Set default values:
        Asset localMaxAcceptedPayout = new Asset();
        localMaxAcceptedPayout.setAmount(1000000000);
        localMaxAcceptedPayout.setSymbol(AssetSymbolType.SBD);

        this.setMaxAcceptedPayout(localMaxAcceptedPayout);
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
     * Set the author of the comment. <b>Notice:</b> The private posting key of
     * this account needs to be stored in the key storage.
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
    public Permlink getPermlink() {
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
    public List<CommentOptionsExtension> getExtensions() {
        if (extensions == null || extensions.isEmpty()) {
            // Create a new ArrayList to avoid a NullPointerException.
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
    public void setExtensions(List<CommentOptionsExtension> extensions) {
        this.extensions = extensions;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentOptionsOperation = new ByteArrayOutputStream()) {
            serializedCommentOptionsOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.COMMENT_OPTIONS_OPERATION.ordinal()));
            serializedCommentOptionsOperation.write(this.getAuthor().toByteArray());
            serializedCommentOptionsOperation.write(this.getPermlink().toByteArray());
            serializedCommentOptionsOperation.write(this.getMaxAcceptedPayout().toByteArray());
            serializedCommentOptionsOperation
                    .write(SteemJUtils.transformShortToByteArray(this.getPercentSteemDollars()));
            serializedCommentOptionsOperation.write(SteemJUtils.transformBooleanToByteArray(this.getAllowVotes()));
            serializedCommentOptionsOperation
                    .write(SteemJUtils.transformBooleanToByteArray(this.getAllowCurationRewards()));

            serializedCommentOptionsOperation
                    .write(SteemJUtils.transformLongToVarIntByteArray(this.getExtensions().size()));

            for (CommentOptionsExtension commentOptionsExtension : this.getExtensions()) {
                serializedCommentOptionsOperation.write(commentOptionsExtension.toByteArray());
            }

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

    @Override
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getOwner(), PrivateKeyType.ACTIVE);
    }
}

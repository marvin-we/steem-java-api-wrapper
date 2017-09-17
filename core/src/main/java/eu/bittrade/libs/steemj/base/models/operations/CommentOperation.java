package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "comment_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOperation extends Operation {
    @JsonProperty("parent_author")
    private AccountName parentAuthor;
    @JsonProperty("parent_permlink")
    private String parentPermlink;
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private String permlink;
    @JsonProperty("title")
    private String title;
    @JsonProperty("body")
    private String body;
    @JsonProperty("json_metadata")
    private String jsonMetadata;

    /**
     * Create a new and empty comment operation.
     */
    public CommentOperation() {
        super(false);
    }

    /**
     * Get the author of the parent article you want to comment on.
     * 
     * @return The author of the parent article you want to comment on.
     */
    public AccountName getParentAuthor() {
        return parentAuthor;
    }

    /**
     * Set the author of the parent article you want to comment on.
     *
     * 
     * @param parentAuthor
     *            The author of the parent article you want to comment on.
     */
    public void setParentAuthor(AccountName parentAuthor) {
        this.parentAuthor = parentAuthor;
    }

    /**
     * Get the account name that will publish the comment.
     * 
     * @return The account name that will publish the comment.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * Set the account name that will publish the comment. <b>Notice:</b> The
     * private posting key of this account needs to be stored in the key
     * storage.
     * 
     * @param author
     *            The account name that will publish the comment.
     */
    public void setAuthor(AccountName author) {
        this.author = author;
    }

    /**
     * Get the permanent link of this comment.
     * 
     * @return The permanent link of this comment.
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * Set the permanent link of this comment.
     * 
     * @param permlink
     *            The permanent link of this comment.
     */
    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    /**
     * Get the permanent link of the parent comment.
     * 
     * @return The permanent link of the parent comment.
     */
    public String getParentPermlink() {
        return parentPermlink;
    }

    /**
     * Set the permanent link of the parent comment.
     * 
     * @param parentPermlink
     *            The permanent link of the parent comment.
     */
    public void setParentPermlink(String parentPermlink) {
        this.parentPermlink = parentPermlink;
    }

    /**
     * Get the title of this comment.
     * 
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title for this comment.
     * 
     * @param title
     *            The title of this comment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the content of this comment.
     * 
     * @return The content of this comment.
     */
    public String getBody() {
        return body;
    }

    /**
     * Set the content of this comment.
     * 
     * @param body
     *            The content of this comment.
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Get the additional Json metadata of this comment.
     * 
     * @return The additional Json metadata
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * Set the additional Json metadata of this comment.
     * 
     * @param jsonMetadata
     *            the additional Json metadata
     */
    public void setJsonMetadata(String jsonMetadata) {
        this.jsonMetadata = jsonMetadata;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedCommentOperation = new ByteArrayOutputStream()) {
            serializedCommentOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.COMMENT_OPERATION.ordinal()));
            serializedCommentOperation.write(this.getParentAuthor().toByteArray());
            serializedCommentOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getParentPermlink()));
            serializedCommentOperation.write(this.getAuthor().toByteArray());
            serializedCommentOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getPermlink()));
            serializedCommentOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getTitle()));
            serializedCommentOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getBody()));
            serializedCommentOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));

            return serializedCommentOperation.toByteArray();
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
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAuthor(), PrivateKeyType.POSTING);
    }
}

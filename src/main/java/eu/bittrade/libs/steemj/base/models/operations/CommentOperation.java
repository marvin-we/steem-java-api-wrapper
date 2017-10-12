package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
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
        // Define the required key type for this operation.
        super(false);
    }

    /**
     * Create a new and empty comment operation. This operation can be used to:
     * <ul>
     * <li>Create a post</li>
     * <li>Update a post</li>
     * <li>Write a comment to a post</li>
     * <li>Update a comment</li>
     * </ul>
     * 
     * @param parentAuthor
     *            Define the parent author in case of a comment or set this to
     *            an empty account name in case of a new post (see
     *            {@link #setParentAuthor(AccountName)}).
     * @param parentPermlink
     *            Define the parent permlink. In case of a new post, this field
     *            will be used to define the main tag of the post (see
     *            {@link #setParentAuthor(AccountName)}).
     * @param author
     *            Set the author of the comment/post (see
     *            {@link #setAuthor(AccountName)}).
     * @param permlink
     *            Define the permlink of this comment/post (see
     *            {@link #setPermlink(Permlink)}).
     * @param title
     *            Define the title of this comment/post (see
     *            {@link #setTitle(String)}).
     * @param body
     *            Define the body of this comment/post (see
     *            {@link #setBody(String)}).
     * @param jsonMetadata
     *            Define additional JSON meta data like additional tags (see
     *            {@link #setJsonMetadata(String)}).
     * @throws InvalidParameterException
     *             If one of the parameters does not fulfill the requirements.
     */
    @JsonCreator
    public CommentOperation(@JsonProperty("parent_author") AccountName parentAuthor,
            @JsonProperty("parent_permlink") String parentPermlink, @JsonProperty("author") AccountName author,
            @JsonProperty("permlink") String permlink, @JsonProperty("title") String title,
            @JsonProperty("body") String body, @JsonProperty("json_metadata") String jsonMetadata) {
        super(false);

        this.setParentAuthor(parentAuthor);
        this.setParentPermlink(parentPermlink);
        this.setAuthor(author);
        this.setPermlink(permlink);
        this.setTitle(title);
        this.setBody(body);
        this.setJsonMetadata(jsonMetadata);
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

        // Update the List of required private key types.
        addRequiredPrivateKeyType(author, PrivateKeyType.POSTING);
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
}

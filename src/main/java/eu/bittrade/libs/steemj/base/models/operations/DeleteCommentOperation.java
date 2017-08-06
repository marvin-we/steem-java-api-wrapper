package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "delete_comment_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeleteCommentOperation extends Operation {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private String permlink;

    /**
     * Create a new and empty delete comment operation. User this operation to
     * delete a comment.
     */
    public DeleteCommentOperation() {
        // Define the required key type for this operation.
        super(false);
    }

    /**
     * Get the account name of the account whose comment has been deleted.
     * 
     * @return The account name of the author whose comment should be deleted.
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * Set the account name of the author whose comment should be deleted.
     * <b>Notice:</b> The private posting key of this account needs to be stored
     * in the key storage.
     * 
     * @param author
     *            The account name of the author whose comment should be
     *            deleted.
     */
    public void setAuthor(AccountName author) {
        this.author = author;

        // Update the List of required private key types.
        addRequiredPrivateKeyType(author, PrivateKeyType.POSTING);
    }

    /**
     * Get the permanent link of the comment to delete.
     * 
     * @return The permanent link of the comment to delete.
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * Set the permanent link of the comment to delete.
     * 
     * @param permlink
     *            The permanent link of the comment to delete.
     */
    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedDeleteCommentOperation = new ByteArrayOutputStream()) {
            serializedDeleteCommentOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.DELETE_COMMENT_OPERATION.ordinal()));
            serializedDeleteCommentOperation.write(this.getAuthor().toByteArray());
            serializedDeleteCommentOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getPermlink()));

            return serializedDeleteCommentOperation.toByteArray();
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

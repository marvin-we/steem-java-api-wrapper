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
 * This class represents the Steem "delete_comment_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeleteCommentOperation extends Operation {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;

    /**
     * Create a new and empty delete comment operation. User this operation to
     * delete a comment.
     * 
     * @param author
     *            Set the author of the post to delete (see
     *            {@link #setAuthor(AccountName)}).
     * @param permlink
     *            Set the permlink of the post to delete (see
     *            {@link #setPermlink(Permlink)}).
     * @throws InvalidParameterException
     *             If one of the arguemnts does not fulfill the requirements.
     */
    @JsonCreator
    public DeleteCommentOperation(@JsonProperty("author") AccountName author,
            @JsonProperty("permlink") Permlink permlink) {
        super(false);

        this.setAuthor(author);
        this.setPermlink(permlink);
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
     * @throws InvalidParameterException
     *             If the <code>author</code> is null.
     */
    public void setAuthor(AccountName author) {
        this.author = setIfNotNull(author, "The author can't be null.");
    }

    /**
     * Get the permanent link of the comment to delete.
     * 
     * @return The permanent link of the comment to delete.
     */
    public Permlink getPermlink() {
        return permlink;
    }

    /**
     * Set the permanent link of the comment to delete.
     * 
     * @param permlink
     *            The permanent link of the comment to delete.
     * @throws InvalidParameterException
     *             If the <code>permlink</code> is null.
     */
    public void setPermlink(Permlink permlink) {
        this.permlink = setIfNotNull(permlink, "The permlink can't be null.");
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedDeleteCommentOperation = new ByteArrayOutputStream()) {
            serializedDeleteCommentOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.DELETE_COMMENT_OPERATION.getOrderId()));
            serializedDeleteCommentOperation.write(this.getAuthor().toByteArray());
            serializedDeleteCommentOperation.write(this.getPermlink().toByteArray());

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

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAuthor(), PrivateKeyType.POSTING);
    }

    @Override
    public void validate(ValidationType validationType) {
        return;
    }
}

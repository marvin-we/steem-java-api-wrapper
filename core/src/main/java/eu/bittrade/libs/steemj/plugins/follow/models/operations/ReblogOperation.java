package eu.bittrade.libs.steemj.plugins.follow.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "reblog_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ReblogOperation extends Operation {
    private AccountName account;
    private AccountName author;
    private String permlink;

    /**
     * Create a new reblog operation to reblog a comment a post. *
     * <b>Attention</b> This operation is currently (HF 0.19) disabled. Please
     * use a
     * {@link eu.bittrade.libs.steemj.base.models.operations.CustomJsonOperation
     * CustomJsonOperation] instead.}
     */
    public ReblogOperation() {
        super(false);
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Define the account whose want to reblog the post. <b>Notice:</b> The
     * private posting key of this account needs to be stored in the key
     * storage.
     * 
     * @param account
     *            The account who wants to reblog the post.
     */
    public void setAccount(AccountName account) {
        this.account = account;

        // Update the List of required private key types.
        addRequiredPrivateKeyType(account, PrivateKeyType.POSTING);
    }

    /**
     * @return the author
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(AccountName author) {
        this.author = author;
    }

    /**
     * Get the permanent link of the post or comment that has been resteemed.
     *
     * @return The permanent link of the post or comment that has been
     *         resteemed.
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * Set the permanent link of the post or comment that should be resteemed.
     * 
     * @param permlink
     *            The permanent link of the post or comment that should be
     *            resteened.
     */
    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedVoteOperation = new ByteArrayOutputStream()) {
            serializedVoteOperation.write(this.getAccount().toByteArray());
            serializedVoteOperation.write(this.getAuthor().toByteArray());
            serializedVoteOperation.write(SteemJUtils.transformStringToVarIntByteArray(this.getPermlink()));

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

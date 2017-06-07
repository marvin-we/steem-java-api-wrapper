package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class represents the Steem "decline_voting_rights_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeclineVotingRightsOperation extends Operation {
    private AccountName account;
    private Boolean decline;

    /**
     * Create a new decline voting rights operation. Use this operation with
     * care as it is used to <b>permanently</b> revoke its voting rights after a
     * 7 day waiting period.
     */
    public DeclineVotingRightsOperation() {
        super(PrivateKeyType.OWNER);
    }

    /**
     * Get the account which updated its voting rights.
     * 
     * @return The account which updated its voting rights.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account which voting rights should be updated.
     * 
     * @param account
     *            The account which updated its voting rights.
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * Get the information if the {@link #account account} decline its voting
     * rights or not.
     * 
     * @return The information if the {@link #account account} decline its
     *         voting rights or not.
     */
    public Boolean getDecline() {
        return decline;
    }

    /**
     * Define if the {@link #account account} decline its voting rights or not.
     * 
     * @param decline
     *            The information if the {@link #account account} decline its
     *            voting rights or not.
     */
    public void setDecline(Boolean decline) {
        this.decline = decline;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedDeclineVotingRightsOperation = new ByteArrayOutputStream()) {
            serializedDeclineVotingRightsOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.DECLINE_VOTING_RIGHTS_OPERATION.ordinal()));
            serializedDeclineVotingRightsOperation.write(this.getAccount().toByteArray());
            serializedDeclineVotingRightsOperation.write(SteemJUtils.transformBooleanToByteArray(this.getDecline()));

            return serializedDeclineVotingRightsOperation.toByteArray();
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

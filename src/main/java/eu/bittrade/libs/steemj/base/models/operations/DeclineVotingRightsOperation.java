package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

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
        super(false);
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
     * Set the account which voting rights should be updated. <b>Notice:</b> The
     * private owner key of this account needs to be stored in the key storage.
     * 
     * @param account
     *            The account which updated its voting rights.
     */
    public void setAccount(AccountName account) {
        this.account = account;

        // Update the List of required private key types.
        addRequiredPrivateKeyType(account, PrivateKeyType.OWNER);
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

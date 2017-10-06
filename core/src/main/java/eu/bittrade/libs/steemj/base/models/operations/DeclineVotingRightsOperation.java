package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
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
 * This class represents the Steem "decline_voting_rights_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DeclineVotingRightsOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("decline")
    private Boolean decline;

    /**
     * Create a new decline voting rights operation. Use this operation with
     * care as it is used to <b>permanently</b> revoke its voting rights after a
     * 7 day waiting period.
     * 
     * @param account
     *            Set the account to decline the voting rights for (see
     *            {@link #setAccount(AccountName)}).
     * @param decline
     *            Define if the voting rights should be declined or not (see
     *            {@link #setDecline(Boolean)}).
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public DeclineVotingRightsOperation(@JsonProperty("account") AccountName account,
            @JsonProperty("decline") Boolean decline) {
        super(false);

        this.setAccount(account);
        this.setDecline(decline);
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
     * @throws InvalidParameterException
     *             If the <code>account</code> is null.
     */
    public void setAccount(AccountName account) {
        if (account == null) {
            throw new InvalidParameterException("The account can't be null.");
        }
        this.account = account;
    }

    /**
     * Get the information if the {@link #getAccount() account} decline its
     * voting rights or not.
     * 
     * @return The information if the {@link #getAccount() account} decline its
     *         voting rights or not.
     */
    public Boolean getDecline() {
        return decline;
    }

    /**
     * Define if the {@link #getAccount() account} decline its voting rights or
     * not.
     * 
     * @param decline
     *            The information if the {@link #getAccount() account} decline
     *            its voting rights or not.
     */
    public void setDecline(Boolean decline) {
        if (decline == null) {
            this.decline = false;
        } else {
            this.decline = decline;
        }
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

    @Override
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.OWNER);
    }
}

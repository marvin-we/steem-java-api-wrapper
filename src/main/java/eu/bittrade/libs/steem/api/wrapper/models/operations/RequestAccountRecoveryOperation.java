package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RequestAccountRecoveryOperation extends Operation {
    @JsonProperty("recovery_account")
    private String recoveryAccount;
    @JsonProperty("account_to_recover")
    private String accountToRecover;
    @JsonProperty("new_owner_authority")
    private Authority newOwnerAuthority;

    public RequestAccountRecoveryOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getRecoveryAccount() {
        return recoveryAccount;
    }

    public String getAccountToRecover() {
        return accountToRecover;
    }

    public Authority getNewOwnerAuthority() {
        return newOwnerAuthority;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}

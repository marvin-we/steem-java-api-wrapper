package eu.bittrade.libs.steem.api.wrapper.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class RequestAccountRecoveryOperation extends Operation {
    @JsonProperty("recovery_account")
    private String recoveryAccount;
    @JsonProperty("account_to_recover")
    private String accountToRecover;
    @JsonProperty("new_owner_authority")
    private Key newOwnerAuthority;

    public String getRecoveryAccount() {
        return recoveryAccount;
    }

    public String getAccountToRecover() {
        return accountToRecover;
    }

    public Key getNewOwnerAuthority() {
        return newOwnerAuthority;
    }
	
	@Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}

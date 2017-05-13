package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Authority;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountUpdateOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("owner")
    private Authority owner;
    @JsonProperty("active")
    private Authority active;
    @JsonProperty("posting")
    private Authority posting;
    @JsonProperty("memo_key")
    private PublicKey memoKey;
    @JsonProperty("json_metadata")
    private String jsonMetadata;

    public AccountUpdateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * @return the owner
     */
    public Authority getOwner() {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(Authority owner) {
        this.owner = owner;
    }

    /**
     * @return the active
     */
    public Authority getActive() {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(Authority active) {
        this.active = active;
    }

    /**
     * @return the posting
     */
    public Authority getPosting() {
        return posting;
    }

    /**
     * @param posting
     *            the posting to set
     */
    public void setPosting(Authority posting) {
        this.posting = posting;
    }

    /**
     * @return the memoKey
     */
    public PublicKey getMemoKey() {
        return memoKey;
    }

    /**
     * @param memoKey
     *            the memoKey to set
     */
    public void setMemoKey(PublicKey memoKey) {
        this.memoKey = memoKey;
    }

    /**
     * @return the jsonMetadata
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * @param jsonMetadata
     *            the jsonMetadata to set
     */
    public void setJsonMetadata(String jsonMetadata) {
        this.jsonMetadata = jsonMetadata;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

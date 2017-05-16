package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CustomOperation extends Operation {
    // Original type is flat_set< account_name_type >.
    @JsonProperty("required_auths")
    private List<AccountName> requiredAuths;
    // Original type is uint16_t so we use int here.
    @JsonProperty("id")
    private int id;
    // Original type is vector< char >.
    @JsonProperty("data")
    private String data;

    public CustomOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the requiredAuths
     */
    public List<AccountName> getRequiredAuths() {
        return requiredAuths;
    }

    /**
     * @param requiredAuths
     *            the requiredAuths to set
     */
    public void setRequiredAuths(List<AccountName> requiredAuths) {
        this.requiredAuths = requiredAuths;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the data that this operation contains. <b>Notice</b> that the
     * original type of this field is "vector< char >" and that its returned as
     * a String.
     * 
     * @return the data The data transfered with this operation.
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
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

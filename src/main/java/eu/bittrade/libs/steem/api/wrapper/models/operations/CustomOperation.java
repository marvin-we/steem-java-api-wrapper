package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.nio.charset.Charset;
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
    private Charset data;

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
     * @return the data
     */
    public Charset getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Charset data) {
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

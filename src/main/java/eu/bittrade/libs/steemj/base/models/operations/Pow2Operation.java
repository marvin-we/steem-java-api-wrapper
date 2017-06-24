package eu.bittrade.libs.steemj.base.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class represents the Steem "pow2_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Pow2Operation extends Operation {
    // TODO: Fix type of work
    // pow2_work --> typedef fc::static_variant< pow2, equihash_pow > pow2_work;
    @JsonProperty("work")
    private Object[] work;
    // TODO: Fix type --> optional< public_key_type > new_owner_key;
    @JsonProperty("new_owner_key")
    private String newOwnerKey;
    @JsonProperty("props")
    private ChainProperties properties;

    public Pow2Operation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the work
     */
    public Object[] getWork() {
        return work;
    }

    /**
     * @param work
     *            the work to set
     */
    public void setWork(Object[] work) {
        this.work = work;
    }

    /**
     * @return the newOwnerKey
     */
    public String getNewOwnerKey() {
        return newOwnerKey;
    }

    /**
     * @param newOwnerKey
     *            the newOwnerKey to set
     */
    public void setNewOwnerKey(String newOwnerKey) {
        this.newOwnerKey = newOwnerKey;
    }

    /**
     * @return the properties
     */
    public ChainProperties getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(ChainProperties properties) {
        this.properties = properties;
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

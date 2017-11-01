package eu.bittrade.libs.steemj.base.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.base.models.deserializer.AccountAuthHashMapDeserializer;
import eu.bittrade.libs.steemj.base.models.deserializer.PublicKeyHashMapDeserializer;
import eu.bittrade.libs.steemj.base.models.serializer.AccountAuthHashMapSerializer;
import eu.bittrade.libs.steemj.base.models.serializer.PublicKeyHashMapSerializer;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is the java implementation of the Steem "authority" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Authority implements ByteTransformable, SignatureObject {
    // Type is uint32 in the original code.
    @JsonProperty("weight_threshold")
    private long weightThreshold;
    /*
     * In the original code the type is "account_authority_map" which looks like
     * this: <p> flat_map< account_name_type, weight_type, string_less > </p>
     */
    @JsonSerialize(using = AccountAuthHashMapSerializer.class)
    @JsonDeserialize(using = AccountAuthHashMapDeserializer.class)
    @JsonProperty("account_auths")
    private Map<AccountName, Integer> accountAuths;
    /*
     * In the original code the type is "key_authority_map" which looks like
     * this: <p> flat_map< public_key_type, weight_type > </p>
     */
    @JsonSerialize(using = PublicKeyHashMapSerializer.class)
    @JsonDeserialize(using = PublicKeyHashMapDeserializer.class)
    @JsonProperty("key_auths")
    private Map<PublicKey, Integer> keyAuths;

    /**
     * Constructor thats set required values to avoid null pointer exceptions.
     */
    public Authority() {
        this.setAccountAuths(new HashMap<AccountName, Integer>());
        this.setKeyAuths(new HashMap<PublicKey, Integer>());
        // Set default values.
        this.setWeightThreshold(0);
    }

    /**
     * 
     * @return the weightThreshold
     */
    public long getWeightThreshold() {
        return weightThreshold;
    }

    /**
     * 
     * @param weightThreshold
     */
    public void setWeightThreshold(long weightThreshold) {
        this.weightThreshold = weightThreshold;
    }

    /**
     * @return A map of stored account names and their threshold.
     */
    public Map<AccountName, Integer> getAccountAuths() {
        return accountAuths;
    }

    /**
     * 
     * @param accountAuths
     */
    public void setAccountAuths(Map<AccountName, Integer> accountAuths) {
        this.accountAuths = accountAuths;
    }

    /**
     * @return A map of stored public keys and their threshold.
     */
    public Map<PublicKey, Integer> getKeyAuths() {
        return keyAuths;
    }

    /**
     * 
     * @param keyAuths
     */
    public void setKeyAuths(Map<PublicKey, Integer> keyAuths) {
        this.keyAuths = keyAuths;
    }

    /**
     * Check if the authority is impossible.
     * 
     * @return <code>true</code> if the authority is impossible, otherwise
     *         <code>false</code>.
     */
    public boolean isImpossible() {
        long authWeights = 0;
        for (int weight : this.getAccountAuths().values()) {
            authWeights += weight;
        }
        for (int weight : this.getKeyAuths().values()) {
            authWeights += weight;
        }

        return authWeights < this.getWeightThreshold();
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAuthority = new ByteArrayOutputStream()) {
            serializedAuthority.write(SteemJUtils.transformIntToByteArray((int) this.getWeightThreshold()));

            serializedAuthority.write(SteemJUtils.transformLongToVarIntByteArray(this.getAccountAuths().size()));

            for (Entry<AccountName, Integer> accountAuth : this.getAccountAuths().entrySet()) {
                serializedAuthority.write(accountAuth.getKey().toByteArray());
                serializedAuthority.write(SteemJUtils.transformShortToByteArray(accountAuth.getValue()));
            }

            serializedAuthority.write(SteemJUtils.transformLongToVarIntByteArray(this.getKeyAuths().size()));

            for (Entry<PublicKey, Integer> keyAuth : this.getKeyAuths().entrySet()) {
                serializedAuthority.write(keyAuth.getKey().toByteArray());
                serializedAuthority.write(SteemJUtils.transformShortToByteArray(keyAuth.getValue()));
            }

            return serializedAuthority.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming an asset into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherAuthority) {
        if (this == otherAuthority)
            return true;
        if (otherAuthority == null || !(otherAuthority instanceof Authority))
            return false;
        Authority other = (Authority) otherAuthority;
        return this.getAccountAuths().equals(other.getAccountAuths()) && this.getKeyAuths().equals(other.getKeyAuths())
                && this.getWeightThreshold() == other.getWeightThreshold();
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getAccountAuths() == null ? 0 : this.getAccountAuths().hashCode());
        hashCode = 31 * hashCode + (this.getKeyAuths() == null ? 0 : this.getKeyAuths().hashCode());
        hashCode = 31 * hashCode + (int) (this.getWeightThreshold() ^ (this.getWeightThreshold() >>> 32));
        return hashCode;
    }

    /**
     * Returns {@code true} if, and only if, the account name has more than
     * {@code 0} characters.
     *
     * @return {@code true} if the account name has more than {@code 0},
     *         otherwise {@code false}
     */
    public boolean isEmpty() {
        return this.getAccountAuths().isEmpty() && this.getKeyAuths().isEmpty();
    }
}
package eu.bittrade.libs.steem.api.wrapper.models;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;
import eu.bittrade.libs.steem.api.wrapper.models.deserializer.AccountAuthHashMapDeserializer;
import eu.bittrade.libs.steem.api.wrapper.models.deserializer.PublicKeyHashMapDeserializer;
import eu.bittrade.libs.steem.api.wrapper.models.serializer.AccountAuthHashMapSerializer;
import eu.bittrade.libs.steem.api.wrapper.models.serializer.PublicKeyHashMapSerializer;
import eu.bittrade.libs.steem.api.wrapper.util.SteemJUtils;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/steem-prerelease-v0.18.2/libraries/protocol/include/steemit/protocol/authority.hpp">Steem
 * authority object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Authority implements ByteTransformable {
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
        this.setAccountAuths(new HashMap<>());
        this.setKeyAuths(new HashMap<>());
        // Set default values.
        this.setWeightThreshold(0);
    }

    /**
     * 
     * @return
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
     * 
     * @return
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
     * 
     * @return
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
}
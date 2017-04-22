package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.bittrade.libs.steem.api.wrapper.interfaces.IByteArray;
import eu.bittrade.libs.steem.api.wrapper.util.StringHashMapDeserializer;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/steem-prerelease-v0.18.2/libraries/protocol/include/steemit/protocol/authority.hpp">Steem
 * authority object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Authority implements IByteArray {
    // Type is uint32 in the original code.
    @JsonProperty("weight_threshold")
    private long weightThreshold;
    /**
     * In the original code the type is "account_authority_map" which looks like
     * this:
     * <p>
     * flat_map< account_name_type, weight_type, string_less >
     * </p>
     */
    @JsonDeserialize(using = StringHashMapDeserializer.class)
    @JsonProperty("account_auths")
    private HashMap<String, Integer> accountAuths;
    /**
     * In the original code the type is "key_authority_map" which looks like
     * this:
     * <p>
     * flat_map< public_key_type, weight_type >
     * </p>
     */
    @JsonDeserialize(using = StringHashMapDeserializer.class)
    @JsonProperty("key_auths")
    private HashMap<String, Integer> keyAuths;

    public long getWeightThreshold() {
        return weightThreshold;
    }

    public HashMap<String, Integer> getAccountAuths() {
        return accountAuths;
    }

    public HashMap<String, Integer> getKeyAuths() {
        return keyAuths;
    }

    @Override
    public byte[] toByteArray() {
        // TODO Auto-generated method stub
        return null;
    }
}
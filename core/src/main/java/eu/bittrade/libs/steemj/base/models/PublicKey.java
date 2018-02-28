package eu.bittrade.libs.steemj.base.models;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.crypto.digests.RIPEMD160Digest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.primitives.Bytes;

import eu.bittrade.crypto.core.AddressFormatException;
import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.base58.Base58;
import eu.bittrade.libs.steemj.base.models.serializer.PublicKeySerializer;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/types.hpp">Steem
 * public_key object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonSerialize(using = PublicKeySerializer.class)
public class PublicKey implements ByteTransformable {
    private static final Logger LOGGER = LoggerFactory.getLogger(PublicKey.class);

    private static final int CHECKSUM_BYTES = 4;

    private ECKey publicKey;
    private String prefix;

    /**
     * Create a new public key by providing an address as String.
     * 
     * @param address
     *            The address in its String representation.
     *            <p>
     *            Example: <br>
     *            STM5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq
     *            </p>
     * @throws AddressFormatException
     *             If the input is not base 58 or the checksum does not
     *             validate.
     */
    @JsonCreator
    public PublicKey(String address) {
        // As this method is also used for parsing different operations where
        // the field could be empty we sadly have to handle "null" cases here.
        if (address != null && !"".equals(address)) {
            if (address.length() != 53) {
                LOGGER.warn("The provided address '{}' has an invalid length and will not be set.", address);
                this.setPublicKey(null);
            } else {
                // We expect the first three chars to be the prefix (STM). The
                // rest
                // of the String contains the base58 encoded public key and its
                // checksum.
                this.prefix = address.substring(0, 3);
                byte[] decodedAddress = Base58.decode(address.substring(3, address.length()));
                // As sha256 is used for Bitcoin and ripemd160 for Steem, we
                // can't
                // use Bitcoinjs Base58.decodeChecked here and have to do all
                // stuff
                // on our own.
                byte[] potentialPublicKey = Arrays.copyOfRange(decodedAddress, 0,
                        decodedAddress.length - CHECKSUM_BYTES);
                byte[] expectedChecksum = Arrays.copyOfRange(decodedAddress, decodedAddress.length - CHECKSUM_BYTES,
                        decodedAddress.length);

                byte[] actualChecksum = calculateChecksum(potentialPublicKey);

                // And compare them.
                for (int i = 0; i < expectedChecksum.length; i++) {
                    if (expectedChecksum[i] != actualChecksum[i]) {
                        throw new AddressFormatException("Checksum does not match.");
                    }
                }

                this.setPublicKey(ECKey.fromPublicOnly(potentialPublicKey));
            }
        } else {
            LOGGER.warn(
                    "An empty address has been provided. This can cause some problems if you plan to broadcast this key.");
            this.setPublicKey(null);
        }
    }

    /**
     * Generate the actual checksum of a Steem public key.
     * 
     * @param publicKey
     *            The public key.
     * @return The actual checksum of a Steem public key.
     */
    private byte[] calculateChecksum(byte[] publicKey) {
        RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
        ripemd160Digest.update(publicKey, 0, publicKey.length);
        byte[] actualChecksum = new byte[ripemd160Digest.getDigestSize()];
        ripemd160Digest.doFinal(actualChecksum, 0);
        return actualChecksum;
    }

    /**
     * Create a new public key by providing a ECKey object containing the public
     * key.
     * 
     * @param publicKey
     *            The public key.
     */
    public PublicKey(ECKey publicKey) {
        this.setPublicKey(publicKey);
        this.prefix = SteemJConfig.getInstance().getAddressPrefix().toString().toUpperCase();
    }

    /**
     * Recreate the address from the public key.
     * 
     * @return The address.
     */
    @JsonIgnore
    public String getAddressFromPublicKey() {
        try {
            // Recreate the address from the public key.
            return this.prefix + Base58.encode(Bytes.concat(this.toByteArray(),
                    Arrays.copyOfRange(calculateChecksum(this.toByteArray()), 0, CHECKSUM_BYTES)));
        } catch (SteemInvalidTransactionException | NullPointerException e) {
            LOGGER.debug("An error occured while generating an address from a public key.", e);
            return "";
        }
    }

    /**
     * Get the public key stored in this object.
     * 
     * @return The public key.
     */
    @JsonIgnore()
    public ECKey getPublicKey() {
        return publicKey;
    }

    /**
     * Set the public key that should be stored in this object.
     * 
     * @param publicKey
     *            The public key.
     */
    private void setPublicKey(ECKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        if (this.getPublicKey().isCompressed()) {
            return this.getPublicKey().getPubKey();
        } else {
            return ECKey.fromPublicOnly(ECKey.compressPoint(this.getPublicKey().getPubKeyPoint())).getPubKey();
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherPublicKey) {
        if (this == otherPublicKey)
            return true;
        if (otherPublicKey == null || !(otherPublicKey instanceof PublicKey))
            return false;
        PublicKey otherKey = (PublicKey) otherPublicKey;
        return this.getPublicKey().equals(otherKey.getPublicKey());
    }

    @Override
    public int hashCode() {
        return this.getPublicKey().hashCode();
    }
}

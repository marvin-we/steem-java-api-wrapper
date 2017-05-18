package eu.bittrade.libs.steem.api.wrapper.models;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.spongycastle.crypto.digests.RIPEMD160Digest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.primitives.Bytes;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;
import eu.bittrade.libs.steem.api.wrapper.models.deserializer.PublicKeyDeserializer;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/types.hpp">Steem
 * public_key object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize(using = PublicKeyDeserializer.class)
public class PublicKey implements ByteTransformable {
    private static final Logger LOGGER = LogManager.getLogger(PublicKey.class);

    private static final int CHECKSUM_BYTES = 4;
    private static final String STEEM_DEFAULT_PREFIX = "STM";

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
    public PublicKey(String address) throws AddressFormatException {
        // As this method is also used for parsing different operations where
        // the field could be empty we sadly have to handle "null" cases here.
        if (address != null && !"".equals(address)) {
            // We expect the first three chars to be the prefix (STM). The rest
            // of the String contains the base58 encoded public key and its
            // checksum.
            this.prefix = address.substring(0, 3);
            byte[] decodedAddress = Base58.decode(address.substring(3, address.length()));
            // As sha256 is used for Bitcoin and ripemd160 for Steem, we can't
            // use Bitcoinjs Base58.decodeChecked here and have to do all stuff
            // on our own.
            byte[] publicKey = Arrays.copyOfRange(decodedAddress, 0, decodedAddress.length - CHECKSUM_BYTES);
            byte[] expectedChecksum = Arrays.copyOfRange(decodedAddress, decodedAddress.length - CHECKSUM_BYTES,
                    decodedAddress.length);

            byte[] actualChecksum = calculateChecksum(publicKey);

            // And compare them.
            for (int i = 0; i < expectedChecksum.length; i++) {
                if (expectedChecksum[i] != actualChecksum[i]) {
                    throw new AddressFormatException("Checksum does not match.");
                }
            }

            this.setPublicKey(ECKey.fromPublicOnly(publicKey));
        } else {
            LOGGER.debug(
                    "An empty address has been provided. This can cause some problems if you plan to broadcast this key.");
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
     * Create a new public key by provding a ECKey object containg the public
     * key.
     * 
     * @param publicKey
     *            The public key.
     */
    public PublicKey(ECKey publicKey) {
        this.setPublicKey(publicKey);
        this.prefix = STEEM_DEFAULT_PREFIX;
    }

    /**
     * Recreate the address from the public key.
     * 
     * @return The address.
     */
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
}

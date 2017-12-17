package eu.bittrade.libs.steemj.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemFatalErrorException;
import eu.bittrade.libs.steemj.exceptions.SteemKeyHandlingException;

/**
 * This class can be used to generate a new public, private and brain key. It is
 * more or less the Java implementation of the original graphene implementation
 * that can be found <a href=
 * "https://github.com/cryptonomex/graphene/blob/master/libraries/wallet/wallet.cpp">on
 * GitHub</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class KeyGenerator {
    private static final int BRAIN_KEY_WORD_COUNT = 16;

    private String brainKey;
    private ECKey privateKey;

    /**
     * Like {@link #KeyGenerator(String, int) KeyGenerator(String, int)} but
     * automatically suggests a new brain key and uses the sequence 0.
     * 
     * @throws SteemKeyHandlingException
     *             If the an algorithm used by the random generator and to
     *             generate a private key is not supported on your platform.
     */
    public KeyGenerator() throws SteemKeyHandlingException {
        try {
            setBrainKey(suggestBrainKey());
        } catch (SteemKeyHandlingException e) {
            throw new SteemFatalErrorException("The generated brain key was not valid - This should never happen.", e);
        }

        setPrivateKey(this.getBrainKey(), 0);
    }

    /**
     * Create a private and a public key based on the given brain key and the
     * given sequence.
     * 
     * <b>Notice</b> that this method uses the SecureRandom.getInstanceStrong()
     * method to generate random numbers. The algorithm used by this method can
     * be changed by configuring the {@code
     * securerandom.strongAlgorithms} {@link Security} property.
     * 
     * @param brainKey
     *            The brain key used to generate a new key pair.
     * @param sequence
     *            The sequence.
     * @throws SteemKeyHandlingException
     *             If the an algorithm used by the random generator and to
     *             generate a private key is not supported on your platform.
     */
    public KeyGenerator(String brainKey, int sequence) throws SteemKeyHandlingException {
        setBrainKey(brainKey);
        setPrivateKey(this.getBrainKey(), sequence);
    }

    /**
     * Get the generated private key.
     * 
     * @return The generated private key.
     */
    public ECKey getPrivateKey() {
        return privateKey;
    }

    /**
     * Get the uncompressed private key in a WI-Format.
     * 
     * @return The uncompressed private key in a WI-Format.
     */
    public String getPrivateKeyAsWIF() {
        return SteemJUtils.privateKeyToWIF(this.getPrivateKey());
    }

    /**
     * This method implements the Steem "derive_private_key" of the wallet.cpp
     * to generate a new private key based on the given brain key and the given
     * sequence.
     * 
     * @param brainKey
     *            The brain key used to generate a new key pair.
     * @param sequence
     *            The sequence.
     * @throws SteemKeyHandlingException
     *             If the an algorithm used by the random generator and to
     *             generate a private key is not supported on your platform.
     */
    private void setPrivateKey(String brainKey, int sequence) throws SteemKeyHandlingException {
        String brainKeyAndSquence = brainKey + " " + sequence;

        try {
            MessageDigest messageDigest512 = MessageDigest.getInstance("SHA-512");
            MessageDigest messageDigest256 = MessageDigest.getInstance("SHA-256");

            byte[] hashedBrainKeyAndSequence = messageDigest512
                    .digest(brainKeyAndSquence.getBytes(SteemJConfig.getInstance().getEncodingCharset()));
            this.privateKey = ECKey.fromPrivate(messageDigest256.digest(hashedBrainKeyAndSequence));
        } catch (NoSuchAlgorithmException e) {
            throw new SteemKeyHandlingException(
                    "The algorithm used to generate a private key is not supported by your system.", e);
        }
    }

    /**
     * Get the generated public key.
     * 
     * @return The generated public key.
     */
    public PublicKey getPublicKey() {
        return new PublicKey(ECKey.fromPrivate(this.getPrivateKey().getPrivKeyBytes()));
    }

    /**
     * Get the generated brain key.
     * 
     * @return The generated brain key.
     */
    public String getBrainKey() {
        return this.brainKey;
    }

    /**
     * Generate a new brain key.
     * 
     * <b>Notice</b> that this method uses the SecureRandom.getInstanceStrong()
     * method to generate random numbers. The algorithm used by this method can
     * be changed by configuring the {@code
     * securerandom.strongAlgorithms} {@link Security} property.
     * 
     * @return The generated brain key.
     * @throws SteemKeyHandlingException
     *             If the algorithm used by the random generator is not
     *             supported on your platform.
     */
    public static String suggestBrainKey() throws SteemKeyHandlingException {
        ArrayList<String> brainKeyParts = new ArrayList<>();

        try {
            for (int i = 0; i < BRAIN_KEY_WORD_COUNT; i++) {
                brainKeyParts.add(BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary()[SecureRandom
                        .getInstance("SHA1PRNG", "SUN")
                        .nextInt(BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary().length - 1)]
                                .toUpperCase());
            }
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new SteemKeyHandlingException(
                    "The algorithm used to provide a strong random number is not available on your system.", e);
        }

        return StringUtils.join(brainKeyParts, " ");
    }

    /**
     * Verify and set the brain key of this instance.
     * 
     * @param brainKey
     *            The brain key in its String representation.
     * @throws SteemKeyHandlingException
     */
    private void setBrainKey(String brainKey) throws SteemKeyHandlingException {
        if (brainKey.split(" ").length != BRAIN_KEY_WORD_COUNT) {
            throw new SteemKeyHandlingException(
                    "The provided brain key has to be a space separated list of " + BRAIN_KEY_WORD_COUNT + " words.");
        }
        this.brainKey = brainKey;
    }
}

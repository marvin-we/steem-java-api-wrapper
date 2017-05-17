package eu.bittrade.libs.steem.api.wrapper.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;

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

    /**
     * Generate a new brain key.
     * 
     * <b>Notice</b> that this method uses the SecureRandom.getInstanceStrong()
     * method to generate random numbers. The algorithm used by this method can
     * be changed by configuring the {@code
     * securerandom.strongAlgorithms} {@link Security} property.
     * 
     * @return The generated brain key.
     * @throws NoSuchAlgorithmException
     *             If the algorithm used by the random generator is not
     *             supported on your platform.
     */
    public String suggestBrainKey() throws NoSuchAlgorithmException {
        ArrayList<String> brainkeyParts = new ArrayList<>();

        for (int i = 0; i < BRAIN_KEY_WORD_COUNT; i++) {
            brainkeyParts.add(
                    BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary()[SecureRandom.getInstanceStrong()
                            .nextInt(BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary().length - 1)]
                                    .toUpperCase());
        }

        return String.join(" ", brainkeyParts);
    }
}

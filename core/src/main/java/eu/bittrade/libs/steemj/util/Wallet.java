package eu.bittrade.libs.steemj.util;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.PublicKey;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * This class is the Java implementation of the Steem "wallet" class.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Wallet {

    /** Add a private constructor to hide the implicit public one. */
    private Wallet() {
    }

    /**
     * Get the private and public key of a given type for the given
     * <code>account</code>
     * 
     * @param account
     *            The account name to generate the passwords for.
     * @param role
     *            The key type that should be generated.
     * @param steemPassword
     *            The password of the <code>account</code> valid for the Steem
     *            blockchain.
     * @return The requested key pair.
     */
    public static ImmutablePair<PublicKey, String> getPrivateKeyFromPassword(AccountName account, PrivateKeyType role,
            String steemPassword) {
        String seed = account.getAccountName() + role.name().toLowerCase() + steemPassword;
        ECKey keyPair = ECKey.fromPrivate(Sha256Hash.hash(seed.getBytes(), 0, seed.length()));

        return new ImmutablePair<>(new PublicKey(keyPair), SteemJUtils.privateKeyToWIF(keyPair));
    }
}

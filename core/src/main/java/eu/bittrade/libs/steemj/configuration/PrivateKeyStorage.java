package eu.bittrade.libs.steemj.configuration;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import eu.bittrade.crypto.core.DumpedPrivateKey;
import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.base58.Sha256ChecksumProvider;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * This class is used to store the private keys of multiple users in a single
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PrivateKeyStorage {
    private HashMap<AccountName, List<ImmutablePair<PrivateKeyType, ECKey>>> privateKeysPerAccounts = new HashMap<>();

    /**
     * Get a private key of the given private key type for the given account
     * name.
     * 
     * @param privateKeyType
     *            The type of the key to request.
     * @param accountName
     *            The account to request the key for.
     * @throws InvalidParameterException
     *             If no key could be find for the given account name.
     * @return The requested private key.
     */
    public ECKey getKeyForAccount(PrivateKeyType privateKeyType, AccountName accountName) {
        List<ImmutablePair<PrivateKeyType, ECKey>> privateKeysForAccount = this.getPrivateKeysPerAccounts()
                .get(accountName);

        if (privateKeysForAccount == null) {
            throw new InvalidParameterException(privateKeyType.name() + " for the account '" + accountName
                    + "' has not been added to the PrivateKeyStore.");
        }

        for (ImmutablePair<PrivateKeyType, ECKey> privateKey : privateKeysForAccount) {
            if (privateKey != null && privateKey.getLeft().equals(privateKeyType)) {
                return privateKey.getRight();
            }
        }

        throw new InvalidParameterException(privateKeyType.name() + " for the account '" + accountName
                + "' has not been added to the PrivateKeyStore.");
    }

    /**
     * Add a new account to the key storage without providing private keys.
     * 
     * @param accountName
     *            The account to be added to the key storage.
     */
    public void addAccount(AccountName accountName) {
        this.getPrivateKeysPerAccounts().put(accountName, new ArrayList<ImmutablePair<PrivateKeyType, ECKey>>());
    }

    /**
     * Add a new account and a list of its private keys to the key storage. This
     * methods expects a List of Pairs while each Pair consists of the private
     * key type and the convenient private key in its WIF representation (e.g.
     * "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3").
     * 
     * @param accountName
     *            The account to be added to the key storage.
     * @param privateKeys
     *            The private keys to be added to the key storage.
     */
    public void addAccount(AccountName accountName, List<ImmutablePair<PrivateKeyType, String>> privateKeys) {
        this.addAccount(accountName);
        for (ImmutablePair<PrivateKeyType, String> privateKey : privateKeys) {
            addPrivateKeyToAccount(accountName, privateKey);
        }
    }

    /**
     * 
     * Add a private key to an already existing account. This methods expects a
     * List of Pairs while each Pair consists of the private key type and the
     * convenient private key in its WIF representation (e.g.
     * "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3").
     * 
     * 
     * <p>
     * Example:
     * </p>
     * 
     * <p>
     * addPrivateKeyToAccount(new AccountName("dez1337"), new
     * ImmutablePair&lt;PrivateKeyType, String&gt;(PrivateKeyType.OWNER,
     * "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3");
     * </p>
     * 
     * @param accountName
     *            The account to add the keys for.
     * @param privateKey
     *            The private key in its WIF representation and its type.
     */
    public void addPrivateKeyToAccount(AccountName accountName, ImmutablePair<PrivateKeyType, String> privateKey) {
        this.getPrivateKeysPerAccounts().get(accountName).add(convertWifToECKeyPair(privateKey));
    }

    /**
     * Removes the given Account and all its stored private keys from the key
     * storage.
     *
     * @param accountName
     *            The account name to remove from the key store.
     * @return The previous value associated with the <tt>account name</tt>, or
     *         <tt>null</tt> if there was no mapping for the
     *         <tt>account name</tt>. (A <tt>null</tt> return can also indicate
     *         that the key previously associated <tt>null</tt> with
     *         <tt>key</tt>.)
     */
    public List<ImmutablePair<PrivateKeyType, ECKey>> removeAccount(AccountName accountName) {
        return this.getPrivateKeysPerAccounts().remove(accountName);
    }

    /**
     * Get the private key store.
     * 
     * @return The private key store.
     */
    public Map<AccountName, List<ImmutablePair<PrivateKeyType, ECKey>>> getPrivateKeysPerAccounts() {
        return privateKeysPerAccounts;
    }

    /**
     * Get a list of account names for which private keys have been stored.
     * 
     * @return A list of account names for which private keys have been stored.
     */
    public List<AccountName> getAccounts() {
        ArrayList<AccountName> storedAccounts = new ArrayList<>();
        storedAccounts.addAll(this.getPrivateKeysPerAccounts().keySet());
        return storedAccounts;
    }

    /**
     * Internal method to convert a WIF private key into an ECKey object.
     * 
     * @param wifPrivateKey
     *            The key pair to convert.
     * @return The converted key pair.
     */
    private ImmutablePair<PrivateKeyType, ECKey> convertWifToECKeyPair(
            ImmutablePair<PrivateKeyType, String> wifPrivateKey) {
        return new ImmutablePair<>(wifPrivateKey.getLeft(),
                DumpedPrivateKey.fromBase58(null, wifPrivateKey.getRight(), new Sha256ChecksumProvider()).getKey());
    }
}

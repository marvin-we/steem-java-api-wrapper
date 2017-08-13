package eu.bittrade.libs.steemj.base.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.bitcoinj.core.Utils;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class is a wrapper for ripemd160 hashes.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class Ripemd160 implements ByteTransformable {
    /**
     * Contains the ripemd160.
     */
    private byte[] hashValue;

    /**
     * Create a new wrapper for the given ripemd160 hash.
     * 
     * @param hashValue
     *            The hash to wrap.
     */
    public Ripemd160(String hashValue) {
        this.setHashValue(hashValue);
    }

    /**
     * Convert the first four bytes of the hash into a number.
     * 
     * @return The number.
     */
    public int getNumberFromHash() {
        byte[] fourBytesByte = new byte[4];
        System.arraycopy(hashValue, 0, fourBytesByte, 0, 4);
        return ByteBuffer.wrap(fourBytesByte).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    /**
     * Get the wrapped hash value in its decoded form.
     * 
     * @return The wrapped hash value in its decoded form.
     */
    public String getHashValue() {
        return new String(hashValue);
    }

    /**
     * Set the hash value by providing its decoded byte representation.
     * 
     * @param hashValue
     *            The hash to wrap.
     */
    public void setHashValue(byte[] hashValue) {
        this.hashValue = hashValue;
    }

    /**
     * Set the hash value by providing its encoded String representation.
     * 
     * @param hashValue
     *            The hash to wrap.
     */
    public void setHashValue(String hashValue) {
        this.hashValue = Utils.HEX.decode(hashValue);
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return this.getHashValue();
    }
}

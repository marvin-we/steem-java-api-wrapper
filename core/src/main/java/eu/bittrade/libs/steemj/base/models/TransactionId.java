package eu.bittrade.libs.steemj.base.models;

import java.io.Serializable;

/**
 * This class is the java implementation of the Steem "transaction_id_type"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransactionId extends Ripemd160 implements Serializable {
    /** Generated serial version uid. */
    private static final long serialVersionUID = -8046859919278042955L;

    /**
     * Create a new wrapper for the given ripemd160 hash.
     * 
     * @param hashValue
     *            The hash to wrap.
     */
    public TransactionId(String hashValue) {
        super(hashValue);
    }
}

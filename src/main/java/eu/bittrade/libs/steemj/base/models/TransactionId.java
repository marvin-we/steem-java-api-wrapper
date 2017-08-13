package eu.bittrade.libs.steemj.base.models;

/**
 * This class is the java implementation of the Steem "transaction_id_type"
 * object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransactionId extends Ripemd160 {
    public TransactionId(String hashValue) {
        super(hashValue);
    }
}

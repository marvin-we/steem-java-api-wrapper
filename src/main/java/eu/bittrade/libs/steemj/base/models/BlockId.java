package eu.bittrade.libs.steemj.base.models;

/**
 * This class is the java implementation of the Steem "block_id_type" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockId extends Ripemd160 {
    public BlockId(String hashValue) {
        super(hashValue);
    }
}

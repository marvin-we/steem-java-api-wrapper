package eu.bittrade.libs.steemj.base.models;

/**
 * This class is the java implementation of the Steem "block_id_type" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockId extends Ripemd160 {
    /** Generated serial version uid. */
    private static final long serialVersionUID = 5819705609731889206L;

    /**
     * @param hashValue
     *            The ripemd160 hash.
     */
    public BlockId(String hashValue) {
        super(hashValue);
    }
}

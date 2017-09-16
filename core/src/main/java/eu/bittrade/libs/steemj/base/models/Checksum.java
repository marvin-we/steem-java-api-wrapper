package eu.bittrade.libs.steemj.base.models;

/**
 * This class is the java implementation of the Steem "checksum_type" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Checksum extends Ripemd160 {
    /** Generated serial version uid. */
    private static final long serialVersionUID = 4420141835144717006L;

    /**
     * @param hashValue
     *            The ripemd160 hash.
     */
    public Checksum(String hashValue) {
        super(hashValue);
    }
}

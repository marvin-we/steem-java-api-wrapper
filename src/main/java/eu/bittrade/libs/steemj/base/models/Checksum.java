package eu.bittrade.libs.steemj.base.models;

/**
 * This class is the java implementation of the Steem "checksum_type" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Checksum extends Ripemd160 {
    public Checksum(String hashValue) {
        super(hashValue);
    }
}

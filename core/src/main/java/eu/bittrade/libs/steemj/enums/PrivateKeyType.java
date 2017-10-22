package eu.bittrade.libs.steemj.enums;

/**
 * An enumeration for all existing private key types.
 * 
 * <b>Attention</b>
 * 
 * Changing the order of the private key types will result in an unexpected
 * behavior.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum PrivateKeyType {
    /** The owner key type */
    OWNER,
    /** The active key type */
    ACTIVE,
    /** The memo key type */
    MEMO,
    /** The posting key type */
    POSTING,
    /**
     * The 'OTHER' key type is no real key type - It is only used to indicate
     * that an authority needs to be provided.
     */
    OTHER
}

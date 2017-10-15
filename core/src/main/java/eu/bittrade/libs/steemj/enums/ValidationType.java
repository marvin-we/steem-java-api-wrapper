package eu.bittrade.libs.steemj.enums;

/**
 * An enumeration for all existing validation types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum ValidationType {
    /**
     * Indicates that all validation methods (except of null pointer checks)
     * should be skipped.
     */
    SKIP_VALIDATION,
    /** Indicates that the validation of assets should be skipped. */
    SKIP_ASSET_VALIDATION,
    /** Indicates that all fields should be validated. */
    ALL
}

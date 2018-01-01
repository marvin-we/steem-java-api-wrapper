package eu.bittrade.libs.steemj.plugins.apis.witness.enums;

/**
 * An enumeration for all existing vandwidth types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum BandwidthType {
    /** Rate limiting posting reward eligibility over time. */
    POST,
    /** Rate limiting for all forum related actins. */
    FORUM,
    /** Rate limiting for all other actions. */
    MARKET
}

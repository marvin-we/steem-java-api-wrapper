package eu.bittrade.libs.steemj.enums;

/**
 * The "block_header_exntesions" type is a variant, which means that it can
 * cover several different types. This enum stores the known core types.
 * 
 * Different types are identifies by their order: The first type has the id 0,
 * the next one the id 1 and so on. Therefore <b>do not change the order</b> of
 * this enum.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum BlockHeaderExtentsionsType {
    /** NA */
    VOID_T,
    /** Normal witness version reporting, for diagnostics and voting. */
    VERSION,
    /** NA */
    HARDFORK_VERSION_VOTE
}

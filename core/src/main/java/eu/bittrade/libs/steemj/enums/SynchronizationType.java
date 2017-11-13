package eu.bittrade.libs.steemj.enums;

/**
 * An enumeration for all existing synchronization types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum SynchronizationType {
    /** Synchronize everything. */
    FULL,
    /** Only synchronize available APIs. */
    APIS_ONLY,
    /** Only synchronize properties. */
    PROPERTIES_ONLY,
    /** Do not synchronize values with the connected Node. */
    NONE
}

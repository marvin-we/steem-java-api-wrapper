package eu.bittrade.libs.steemj.base.models;

import com.fasterxml.jackson.annotation.JsonCreator;

import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class is the java implementation of the Steem "hardfork_version" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class HardforkVersion extends Version implements ByteTransformable {
    /**
     * This class represents a hardfork version.
     * 
     * @param majorVersion
     *            The major version to set.
     * @param hardforkVersion
     *            The hardfork version to set.
     */
    public HardforkVersion(byte majorVersion, byte hardforkVersion) {
        super(majorVersion, hardforkVersion, (short) 0);
    }

    /**
     * Like {@link #HardforkVersion(byte, byte)}, but expects a Version object.
     * 
     * @param hardforkVersion
     *            The hardfork version to set.
     */
    public HardforkVersion(Version hardforkVersion) {
        super(hardforkVersion.toString());
    }

    /**
     * Like {@link #HardforkVersion(byte, byte)}, but expects the hardfork
     * version in its String representation.
     * 
     * @param hardforkVersion
     *            The hardfork version to set.
     */
    @JsonCreator
    public HardforkVersion(String hardforkVersion) {
        super(hardforkVersion);
    }
}

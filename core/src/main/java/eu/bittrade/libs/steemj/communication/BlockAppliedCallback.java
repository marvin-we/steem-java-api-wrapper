package eu.bittrade.libs.steemj.communication;

import java.util.Random;

import eu.bittrade.libs.steemj.base.models.SignedBlockHeader;

/**
 * This abstract class is used as a standard wrapper for a block applied
 * callback. It is needed to handle callbacks of the
 * {@link eu.bittrade.libs.steemj.SteemJ#setBlockAppliedCallback(BlockAppliedCallback)}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BlockAppliedCallback {
    private final int uuid = new Random().nextInt(Integer.MAX_VALUE);

    /**
     * Get the uuid of this instance.
     * 
     * @return The uuid of this instance.
     */
    public int getUuid() {
        return uuid;
    }

    /**
     * This method will be called when a new block has been applied.
     * 
     * @param signedBlockHeader
     *            The block header of the applied block.
     */
    public abstract void onNewBlock(SignedBlockHeader signedBlockHeader);
}

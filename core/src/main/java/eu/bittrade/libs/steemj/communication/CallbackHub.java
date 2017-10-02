package eu.bittrade.libs.steemj.communication;

import java.util.ArrayList;

/**
 * This class is used to manage all callback instances.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CallbackHub {
    private static CallbackHub callbackHubInstance;

    private ArrayList<BlockAppliedCallback> blockAppliedCallbacks = new ArrayList<>();

    /**
     * Lock instance creation by making the constructor private.
     */
    private CallbackHub() {
    }

    /**
     * Get the registered callback by providing its uuid.
     * 
     * @param uuid
     *            The uuid of the callback instance to search for.
     * @return The callback instance if a callback with the given uuid has been
     *         found, otherwise null.
     */
    public BlockAppliedCallback getCallbackByUuid(int uuid) {
        for (BlockAppliedCallback blockAppliedCallback : blockAppliedCallbacks) {
            if (blockAppliedCallback.getUuid() == uuid)
                return blockAppliedCallback;
        }

        return null;
    }

    /**
     * Add a <code>blockAppliedCallback</code> instance that should be called on
     * notifications.
     * 
     * @param blockAppliedCallback
     *            The instance to call.
     */
    public void addCallback(BlockAppliedCallback blockAppliedCallback) {
        this.blockAppliedCallbacks.add(blockAppliedCallback);
    }

    /**
     * Receive a {@link eu.bittrade.libs.steemj.communication.CallbackHub
     * CallbackHub} instance.
     * 
     * @return A CallbackHub instance.
     */
    public static CallbackHub getInstance() {
        if (callbackHubInstance == null) {
            callbackHubInstance = new CallbackHub();
        }

        return callbackHubInstance;
    }
}

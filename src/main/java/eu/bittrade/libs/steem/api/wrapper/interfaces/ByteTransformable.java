package eu.bittrade.libs.steem.api.wrapper.interfaces;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;

@FunctionalInterface
public interface ByteTransformable {
    /**
     * Covert the operation into a byte array.
     * 
     * @return The operation as a byte array.
     * @throws SteemInvalidTransactionException
     *             If there was a problem while transforming the transaction
     *             into a byte array.
     */
    byte[] toByteArray() throws SteemInvalidTransactionException;
}

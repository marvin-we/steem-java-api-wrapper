package eu.bittrade.libs.steemj.interfaces;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This interface is used to make sure each operation implements a method to get
 * its byte representation.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
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

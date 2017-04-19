package eu.bittrade.libs.steem.api.wrapper.interfaces;

import java.io.UnsupportedEncodingException;

@FunctionalInterface
public interface IByteArray {
    /**
     * Covert the operation into a byte array.
     * 
     * @return The operation as a byte array.
     */
    byte[] toByteArray() throws UnsupportedEncodingException;
}

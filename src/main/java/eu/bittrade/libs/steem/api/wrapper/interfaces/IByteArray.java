package eu.bittrade.libs.steem.api.wrapper.interfaces;

import java.io.UnsupportedEncodingException;

@FunctionalInterface
public interface IByteArray {
    /**
     * Covert the operation into a byte array.
     * 
     * @return The operation as a byte array.
     * @throws UnsupportedEncodingException
     *             If the required encoding is not present on your machine.
     *             (Default encoding is US-ASCII, see
     *             {@link eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig
     *             SteemApiWrapperConfig} for further information.
     */
    byte[] toByteArray() throws UnsupportedEncodingException;
}

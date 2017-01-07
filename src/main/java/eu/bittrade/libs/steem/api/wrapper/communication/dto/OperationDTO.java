package eu.bittrade.libs.steem.api.wrapper.communication.dto;

import java.io.UnsupportedEncodingException;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class OperationDTO {
    abstract byte[] serializeOperation() throws UnsupportedEncodingException;
}

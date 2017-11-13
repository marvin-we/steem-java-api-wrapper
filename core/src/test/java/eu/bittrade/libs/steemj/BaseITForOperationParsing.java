package eu.bittrade.libs.steemj;

import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseITForOperationParsing extends BaseIT {
    /**
     * Test if a JSON String received from a Steem Node is correctly parsed into
     * a POJO.
     * 
     * @throws SteemCommunicationException
     *             If something went wrong.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    public abstract void testOperationParsing() throws SteemCommunicationException, SteemResponseException;
}

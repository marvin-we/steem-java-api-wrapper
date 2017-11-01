package eu.bittrade.libs.steemj;

import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseOperationParsingIT extends BaseIntegrationTest {
    /**
     * Test if a JSON String received from a Steem Node is correctly parsed into
     * a POJO.
     * 
     * @throws SteemCommunicationException
     *             If something went wrong.
     */
    public abstract void testOperationParsing() throws SteemCommunicationException, SteemResponseException;
}

/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.account.by.key;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.account.by.key.models.GetKeyReferencesArgs;
import eu.bittrade.libs.steemj.plugins.apis.account.by.key.models.GetKeyReferencesReturn;

/**
 * This class implements the account by key api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountByKeyApi {
    /** Add a private constructor to hide the implicit public one. */
    private AccountByKeyApi() {
    }

    /**
     * Search for users under the use of their public key(s).
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param publicKeys
     *            An array containing one or more public keys.
     * @return A list of arrays containing the matching account names.
     * @throws SteemCommunicationException
     *             <ul>
     *             <li>If the server was not able to answer the request in the
     *             given time (see
     *             {@link eu.bittrade.libs.steemj.configuration.SteemJConfig#setResponseTimeout(int)
     *             setResponseTimeout}).</li>
     *             <li>If there is a connection problem.</li>
     *             </ul>
     * @throws SteemResponseException
     *             <ul>
     *             <li>If the SteemJ is unable to transform the JSON response
     *             into a Java object.</li>
     *             <li>If the Server returned an error object.</li>
     *             </ul>
     */
    public static GetKeyReferencesReturn getKeyReferences(CommunicationHandler communicationHandler,
            GetKeyReferencesArgs publicKeys) throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.ACCOUNT_BY_KEY_API,
                RequestMethod.GET_KEY_REFERENCES, publicKeys);

        return communicationHandler.performRequest(requestObject, GetKeyReferencesReturn.class).get(0);
    }
}

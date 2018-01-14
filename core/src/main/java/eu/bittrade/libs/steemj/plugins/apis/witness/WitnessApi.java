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
package eu.bittrade.libs.steemj.plugins.apis.witness;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthReturn;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.ReserveRatioObject;

/**
 * This class implements the witness api.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessApi {
    /** Add a private constructor to hide the implicit public one. */
    private WitnessApi() {
    }

    /**
     * 
     * @param communicationHandler
     * @param getAccountBandwidthArgs
     * @return
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static GetAccountBandwidthReturn getAccountBandwidth(CommunicationHandler communicationHandler,
            GetAccountBandwidthArgs getAccountBandwidthArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.WITNESS_API, RequestMethod.GET_ACCOUNT_BANDWIDTH,
                getAccountBandwidthArgs);

        return communicationHandler.performRequest(requestObject, GetAccountBandwidthReturn.class).get(0);
    }

    /**
     * 
     * @param communicationHandler
     * @return
     * @throws SteemCommunicationException
     * @throws SteemResponseException
     */
    public static ReserveRatioObject getReserveRatio(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest(SteemApiType.WITNESS_API, RequestMethod.GET_REVERSE_RATIO,
                null);

        return communicationHandler.performRequest(requestObject, ReserveRatioObject.class).get(0);
    }
}

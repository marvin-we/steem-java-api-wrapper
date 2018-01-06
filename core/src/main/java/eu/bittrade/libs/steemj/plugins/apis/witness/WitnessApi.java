package eu.bittrade.libs.steemj.plugins.apis.witness;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethods;
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

    public static GetAccountBandwidthReturn getAccountBandwidth(CommunicationHandler communicationHandler,
            GetAccountBandwidthArgs getAccountBandwidthArgs)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_ACCOUNT_BANDWIDTH);
        requestObject.setSteemApi(SteemApiType.WITNESS_API);
        requestObject.setAdditionalParameters(getAccountBandwidthArgs);

        return communicationHandler.performRequest(requestObject, GetAccountBandwidthReturn.class).get(0);
    }

    public static ReserveRatioObject getReserveRatio(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_REVERSE_RATIO);
        requestObject.setSteemApi(SteemApiType.WITNESS_API);
        // requestObject.setAdditionalParameters("");

        return communicationHandler.performRequest(requestObject, ReserveRatioObject.class).get(0);
    }
}

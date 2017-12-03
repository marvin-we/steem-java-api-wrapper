package eu.bittrade.libs.steemj.apis.login;

import java.util.List;

import eu.bittrade.libs.steemj.apis.login.models.SteemVersionInfo;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.communication.jrpc.JsonRPCRequest;
import eu.bittrade.libs.steemj.enums.RequestMethods;
import eu.bittrade.libs.steemj.enums.SteemApiType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * This class implements the "login_api".
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LoginApi {
    /** Add a private constructor to hide the implicit public one. */
    private LoginApi() {
    }

    /**
     * Use this method to authenticate to the RPC server.
     * 
     * <p>
     * When setting up a Steem Node the operator has the possibility to protect
     * specific APIs with a user name and a password. Requests to secured
     * API-Endpoints require a login before being accessed. This can be achieved
     * by using this method.
     * </p>
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param accountName
     *            The user name used to login.
     * @param password
     *            The password that belongs to the <code>accountName</code>.
     * @return <code>true</code> if the login was successful, otherwise
     *         <code>false</code>.
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
    public static boolean login(CommunicationHandler communicationHandler, AccountName accountName, String password)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.LOGIN);
        requestObject.setSteemApi(SteemApiType.LOGIN_API);
        String[] parameters = { accountName.getName(), password };
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, Boolean.class).get(0);
    }

    /**
     * Use this method to receive the ID of an API or <code>null</code> if an
     * API with the <code>apiName</code> does not exist or is disabled.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @param apiName
     *            The name of the API.
     * @return The ID for the given API name or <code>null</code>, if the API is
     *         not active or does not exist.
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
    public static Integer getApiByName(CommunicationHandler communicationHandler, String apiName)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_API_BY_NAME);
        requestObject.setSteemApi(SteemApiType.LOGIN_API);
        String[] parameters = { apiName };
        requestObject.setAdditionalParameters(parameters);

        List<Integer> response = communicationHandler.performRequest(requestObject, Integer.class);
        if (!response.isEmpty()) {
            return response.get(0);
        }

        return null;
    }

    /**
     * Use this method to get detailed information about the Steem version of
     * the node SteemJ is connected to.
     * 
     * @param communicationHandler
     *            A
     *            {@link eu.bittrade.libs.steemj.communication.CommunicationHandler
     *            CommunicationHandler} instance that should be used to send the
     *            request.
     * @return A {@link SteemVersionInfo} object which contains detailed
     *         information about the Steem version the node is running.
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
    public static SteemVersionInfo getVersion(CommunicationHandler communicationHandler)
            throws SteemCommunicationException, SteemResponseException {
        JsonRPCRequest requestObject = new JsonRPCRequest();
        requestObject.setApiMethod(RequestMethods.GET_VERSION);
        requestObject.setSteemApi(SteemApiType.LOGIN_API);
        String[] parameters = {};
        requestObject.setAdditionalParameters(parameters);

        return communicationHandler.performRequest(requestObject, SteemVersionInfo.class).get(0);
    }
}

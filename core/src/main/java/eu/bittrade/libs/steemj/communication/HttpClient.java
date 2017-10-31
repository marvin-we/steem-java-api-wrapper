package eu.bittrade.libs.steemj.communication;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.UnsupportedCharsetException;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.communication.dto.JsonRPCRequest;
import eu.bittrade.libs.steemj.communication.dto.JsonRPCResponse;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;

/**
 * This class handles the communication to a Steem Node using the HTTP protocol.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class HttpClient extends AbstractClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
    private static final HttpReconnectHandler RECONNECT_HANDLER = new HttpReconnectHandler();

    @Override
    public JsonRPCResponse invokeAndReadResponse(JsonRPCRequest requestObject, URI endpointUri,
            boolean sslVerificationDisabled) throws SteemCommunicationException {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        // Disable SSL verification if needed
        if (sslVerificationDisabled && endpointUri.getScheme().equals("https")) {
            httpClientBuilder.setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
        }

        RequestConfig reqeustConfig = RequestConfig.custom()
                .setSocketTimeout(SteemJConfig.getInstance().getResponseTimeout())
                .setConnectTimeout(SteemJConfig.getInstance().getIdleTimeout()).build();

        httpClientBuilder.setDefaultRequestConfig(reqeustConfig);

        try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
            String request = requestObject.toJson();
            LOGGER.debug("Sending {}.", request);
            StringEntity requestEntity = new StringEntity(request, ContentType.APPLICATION_JSON);

            HttpPost postMethod = new HttpPost("https://api.steemit.com");
            postMethod.setEntity(requestEntity);
            return new JsonRPCResponse(
                    CommunicationHandler.getObjectMapper().readTree(httpClient.execute(postMethod, RECONNECT_HANDLER)));
        } catch (IOException | UnsupportedCharsetException e) {
            throw new SteemCommunicationException("A problem occured while processing the request.", e);
        }
    }

    @Override
    protected void handleCallback(JsonRPCResponse rawJsonResponse) {
        // See https://github.com/steemit/steem/issues/1197 for further details.
        throw new NotImplementedException("Callbacks are not supported when HTTP is used.");
    }

    @Override
    public void closeConnection() {
        // A connection is used for exactly one request and closed afterwards.
        // Due to that there is no need to close it here.
    }
}

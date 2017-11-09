package eu.bittrade.libs.steemj.communication;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * This class is used to initialize a http request.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class HttpClientRequestInitializer implements HttpRequestInitializer {

    @Override
    public void initialize(HttpRequest request) throws IOException {
        request.setConnectTimeout(SteemJConfig.getInstance().getIdleTimeout());
        request.setReadTimeout(SteemJConfig.getInstance().getResponseTimeout());
        request.setNumberOfRetries(0);
    }
}

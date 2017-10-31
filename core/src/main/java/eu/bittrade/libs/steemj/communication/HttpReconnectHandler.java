package eu.bittrade.libs.steemj.communication;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

/**
 * This class handles unexpected return codes received from a Steem Node to
 * force a reconnect.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class HttpReconnectHandler implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}

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

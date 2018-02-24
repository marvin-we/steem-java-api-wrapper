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
package eu.bittrade.libs.steemj;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.SynchronizationType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;

/**
 * @author Anthony Martin
 */
public abstract class BaseIT extends BaseTest {
    protected static SteemJConfig config;
    protected static SteemJ steemJ;

    /**
     * Prepare a the environment for standard integration tests.
     */
    protected static void setupIntegrationTestEnvironment() {
        config = SteemJConfig.getNewInstance();
        config.setResponseTimeout(0);
        config.setSynchronizationLevel(SynchronizationType.PROPERTIES_ONLY);

        try {
            steemJ = new SteemJ();
        } catch (SteemCommunicationException | SteemResponseException e) {
            throw new RuntimeException("Could not create a SteemJ instance. - Test execution stopped.", e);
        }
    }

    /**
     * Call this method in case the tests should be fired against a WebSocket
     * endpoint instead of using the default HTTPS endpoint.
     * 
     * @throws URISyntaxException
     *             If the URL is wrong.
     */
    public static void configureSteemWebSocketEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("wss://steemd.steemit.com"), true);

        endpoints.add(webSocketEndpoint);
        config.setEndpointURIs(endpoints);
    }

    /**
     * Call this method in case the tests should be fired against the TestNet
     * endpoint instead of using the default HTTPS endpoint.
     * 
     * @throws URISyntaxException
     *             If the URL is wrong.
     */
    public static void configureTestNetHttpEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("https://testnet.steem.vc"), true);

        endpoints.add(webSocketEndpoint);
        config.setEndpointURIs(endpoints);
    }

    /**
     * Call this method in case the tests should be fired against the TestNet
     * endpoint using the WebSocket protocol..
     * 
     * @throws URISyntaxException
     *             If the URL is wrong.
     */
    public static void configureTestNetWebsocketEndpoint() throws URISyntaxException {
        ArrayList<Pair<URI, Boolean>> endpoints = new ArrayList<>();

        ImmutablePair<URI, Boolean> webSocketEndpoint;
        webSocketEndpoint = new ImmutablePair<>(new URI("wss://testnet.steem.vc"), true);

        endpoints.add(webSocketEndpoint);
        config.setEndpointURIs(endpoints);
    }
}

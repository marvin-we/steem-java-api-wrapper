package eu.bittrade.libs.steem.api.wrapper.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

import javax.websocket.ClientEndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.ECKey;

//TODO: Add value verification in setters.
/**
 * This class stores the configuration that is used for the communication to the
 * defined web socket server.
 * 
 * The setters can be used to override the default values.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemApiWrapperConfig {
    private static final Logger LOGGER = LogManager.getLogger(SteemApiWrapperConfig.class);

    private ClientEndpointConfig clientEndpointConfig;
    private URI websocketEndpointURI;
    private long timeout;
    private SimpleDateFormat dateTimeFormat;
    private String username;
    private char[] password;
    private boolean sslVerificationDisabled;
    private ECKey privatePostingKey;
    private ECKey privateActiveKey;
    private ECKey privateOwnerKey;
    private ECKey privateMemoKey;

    /**
     * Default constructor that will set all default values.
     */
    public SteemApiWrapperConfig() {
        this.clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        try {
            this.websocketEndpointURI = new URI("wss://node.steem.ws");
        } catch (URISyntaxException e) {
            // This can never happen!
            LOGGER.error("The configured default URI has a Syntax error.", e);
            this.websocketEndpointURI = null;
        }
        this.timeout = 1000;
        this.dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.username = "";
        this.password = "".toCharArray();
        this.sslVerificationDisabled = false;

        this.privatePostingKey = null;
        this.privateActiveKey = null;
        this.privateOwnerKey = null;
        this.privateMemoKey = null;
    }

    /**
     * @return Get the configured ClientEndpointConfig instance.
     */
    public ClientEndpointConfig getClientEndpointConfig() {
        return clientEndpointConfig;
    }

    /**
     * Override the default ClientEndpointConfig instance.
     * 
     * @param clientEndpointConfig
     *            The configuration of the client end point.
     */
    public void setClientEndpointConfig(ClientEndpointConfig clientEndpointConfig) {
        this.clientEndpointConfig = clientEndpointConfig;
    }

    /**
     * @return Get the configured websocket endpoint URI.
     */
    public URI getWebsocketEndpointURI() {
        return websocketEndpointURI;
    }

    /**
     * Override the default websocket endpoint URI.
     * 
     * @param websocketEndpointURI
     *            The URI of the node you want to connect to.
     */
    public void setWebsocketEndpointURI(URI websocketEndpointURI) {
        this.websocketEndpointURI = websocketEndpointURI;
    }

    /**
     * Get the configured, maximum time that the wrapper will wait for an answer
     * of the websocket server.
     * 
     * @return Time in milliseconds
     */
    public long getTimeout() {
        return timeout;
    }

    /**
     * Override the default, maximum time that the wrapper will wait for an
     * answer of the websocket server.
     * 
     * @param timeout
     *            Time in milliseconds.
     */
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * The default date format that will be used to map the date fields of the
     * server response
     * 
     * @return The used date time format used for deserialization.
     */
    public SimpleDateFormat getDateTimeFormat() {
        return dateTimeFormat;
    }

    /**
     * Override the default date format that will be used to map the date fields
     * of the server response.
     * 
     * @param dateTimeFormat
     *            The date time format used for deserialization.
     */
    public void setDateTimeFormat(SimpleDateFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     * Set the user name which should be used for methods, that require
     * authentication.
     * 
     * @param username
     *            The user name to use.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password which should be used for methods, that require
     * authentication.
     * 
     * @param password
     *            The password to use.
     */
    public void setPassword(char[] password) {
        this.password = password;
    }

    /**
     * Get the currently configured user name.
     * 
     * @return The currently configured user name.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the currently configured password.
     * 
     * @return The currently configured password.
     */
    public char[] getPassword() {
        return password;
    }

    /**
     * Check if the SSL-Verification should be disabled.
     * 
     * @return True if the SSL-Verification should be disabled or false if not.
     */
    public boolean isSslVerificationDisabled() {
        return sslVerificationDisabled;
    }

    /**
     * Set if the SSL-Verification should be disabled.
     * 
     * @param sslVerificationDisabled
     */
    public void setSslVerificationDisabled(boolean sslVerificationDisabled) {
        this.sslVerificationDisabled = sslVerificationDisabled;
    }

    /**
     * Get the configured private posting key. A posting key is required to
     * vote, post or comment on content.
     * 
     * @return Your private posting key.
     */
    public ECKey getPrivatePostingKey() {
        return privatePostingKey;
    }

    /**
     * Set your private posting key. A posting key is required to vote, post or
     * comment on content.
     * 
     * @param privatePostingKey
     *            Your private posting key.
     */
    public void setPrivatePostingKey(ECKey privatePostingKey) {
        this.privatePostingKey = privatePostingKey;
    }

    /**
     * Get the configured private active key. An active key is required to
     * interact with the market, to change keys and to vote for witnesses.
     * 
     * @return Your private active key.
     */
    public ECKey getPrivateActiveKey() {
        return privateActiveKey;
    }

    /**
     * Set your private active key. An active key is required to interact with
     * the market, to change keys and to vote for witnesses.
     * 
     * @param privateActiveKey
     *            Your private active key.
     */
    public void setPrivateActiveKey(ECKey privateActiveKey) {
        this.privateActiveKey = privateActiveKey;
    }

    /**
     * Get the configured private owner key. An owner key is required to change
     * the owner key.
     * 
     * @return Your private owner key.
     */
    public ECKey getPrivateOwnerKey() {
        return privateOwnerKey;
    }

    /**
     * Set the private owner key. An owner key is required to change the owner
     * key.
     * 
     * @param privateOwnerKey
     *            Your private owner key.
     */
    public void setPrivateOwnerKey(ECKey privateOwnerKey) {
        this.privateOwnerKey = privateOwnerKey;
    }

    /**
     * Get the configured private memo key. A memo key is required to use
     * private messages.
     * 
     * @return Your private memo key.
     */
    public ECKey getPrivateMemoKey() {
        return privateMemoKey;
    }

    /**
     * Set your private memo key. A memo key is required to use private
     * messages.
     * 
     * @param privateMemoKey
     *            Your private memo key.
     */
    public void setPrivateMemoKey(ECKey privateMemoKey) {
        this.privateMemoKey = privateMemoKey;
    }
}

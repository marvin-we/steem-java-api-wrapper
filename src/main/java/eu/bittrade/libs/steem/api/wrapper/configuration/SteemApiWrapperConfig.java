package eu.bittrade.libs.steem.api.wrapper.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.TimeZone;

import javax.websocket.ClientEndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bitcoinj.core.DumpedPrivateKey;
import org.bitcoinj.core.ECKey;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;

/**
 * This class stores the configuration that is used for the communication to the
 * defined web socket server.
 * 
 * The setters can be used to override the default values.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemApiWrapperConfig {
    private static final Logger LOGGER = LogManager.getLogger(SteemApiWrapperConfig.class);

    private static SteemApiWrapperConfig steemApiWrapperConfigInstance;

    private ClientEndpointConfig clientEndpointConfig;
    private URI websocketEndpointURI;
    private long timeout;
    private String dateTimePattern;
    private long maximumExpirationDateOffset;
    private String timeZoneId;
    private String username;
    private char[] password;
    private boolean sslVerificationDisabled;
    private Map<PrivateKeyType, ECKey> privateKeys;

    /**
     * Default constructor that will set all default values.
     */
    private SteemApiWrapperConfig() {
        this.clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        try {
            this.websocketEndpointURI = new URI("wss://node.steem.ws");
        } catch (URISyntaxException e) {
            // This can never happen!
            LOGGER.error("The configured default URI has a Syntax error.", e);
            this.websocketEndpointURI = null;
        }
        this.timeout = 1000;
        this.dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss";
        this.username = "";
        this.password = "".toCharArray();
        this.sslVerificationDisabled = false;
        this.maximumExpirationDateOffset = 3600000L;
        this.timeZoneId = "GMT";

        this.privateKeys = new EnumMap<>(PrivateKeyType.class);
        for (PrivateKeyType privateKeyType : PrivateKeyType.values()) {
            this.privateKeys.put(privateKeyType, null);
        }
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
     * Get the currently configured date time pattern. This date pattern is used
     * to serialize and deserialize JSON/Java objects.
     * 
     * @return The used date time pattern used for
     *         serialization/deserialization.
     */
    public String getDateTimePattern() {
        return dateTimePattern;
    }

    /**
     * Override the default date pattern. This date pattern is used to serialize
     * and deserialize JSON/Java objects.
     * 
     * @param dateTimePattern
     *            The date time pattern used for serialization/deserialization.
     * @param timeZoneId
     *            The time zone id used for serialization/deserialization (e.g.
     *            "UTC").
     */
    public void setDateTime(String dateTimePattern, String timeZoneId) {
        // Create a SimpleDateFormat instance to verify the pattern is valid.
        new SimpleDateFormat(dateTimePattern);
        this.dateTimePattern = dateTimePattern;
        // Try to verify the timeZoneId.
        if (!"GMT".equals(timeZoneId) && "GMT".equals(TimeZone.getTimeZone(timeZoneId).getID())) {
            LOGGER.warn("The timezoneId {} could not be understood - UTC will now be used as a default.", timeZoneId);
            this.timeZoneId = "UTC";
        } else {
            this.timeZoneId = timeZoneId;
        }
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
     *            Defines if the SSL-Verification should be disabled or not.
     */
    public void setSslVerificationDisabled(boolean sslVerificationDisabled) {
        this.sslVerificationDisabled = sslVerificationDisabled;
    }

    /**
     * Add one private key as a ECKey instance to the configuration. The private
     * keys are required to sign transactions.
     * 
     * <ul>
     * <li>A posting key is required to vote, post or comment on content.</li>
     * <li>An active key is required to interact with the market, to change keys
     * and to vote for witnesses.</li>
     * <li>An owner key is required to change the keys.</li>
     * <li>A memo key is required to use private messages.</li>
     * </ul>
     * 
     * @param privateKeyType
     *            The type of the key.
     * @param privateKey
     *            The private key itself as a ECKey instance.
     */
    public void setPrivateKey(PrivateKeyType privateKeyType, ECKey privateKey) {
        this.privateKeys.put(privateKeyType, privateKey);
    }

    /**
     * Add one private key in its String representation to the configuration.
     * The private keys are required to sign transactions.
     *
     * <ul>
     * <li>A posting key is required to vote, post or comment on content.</li>
     * <li>An active key is required to interact with the market, to change keys
     * and to vote for witnesses.</li>
     * <li>An owner key is required to change the keys.</li>
     * <li>A memo key is required to use private messages.</li>
     * </ul>
     * 
     * <p>
     * Example:<br>
     * setPrivateKey(PrivateKeyType.OWNER,
     * "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3")
     * </p>
     * 
     * @param privateKeyType
     *            The type of the key.
     * @param privateKey
     *            The private key itself as a ECKey instance.
     */
    public void setPrivateKey(PrivateKeyType privateKeyType, String privateKey) {
        this.privateKeys.put(privateKeyType, DumpedPrivateKey.fromBase58(null, privateKey).getKey());
    }

    /**
     * Get a specific private key.
     * 
     * @param privateKeyType
     *            The private key type.
     * @return The configured private key of the specified type.
     */
    public ECKey getPrivateKey(PrivateKeyType privateKeyType) {
        return this.privateKeys.get(privateKeyType);
    }

    /**
     * Get all configured keys.
     * 
     * @return A map containing all configured keys. If no key has been
     *         specified, the key will be 'null'.
     */
    public Map<PrivateKeyType, ECKey> getPrivateKeys() {
        return this.privateKeys;
    }

    /**
     * Get the currently configured maximum offset of the expiration date.
     * 
     * @return The maximum offset of the expiration date.
     */
    public long getMaximumExpirationDateOffset() {
        return maximumExpirationDateOffset;
    }

    /**
     * A Steem Node will only accept transactions whose expiration date is not
     * to far in the future.
     * 
     * <p>
     * Example:<br>
     * Time now: 2017-04-20 20:33 Latest allowed expiration date: 2017-04-20
     * 21:24
     * </p>
     * 
     * The difference between $NOW and the $MAXIMAL_ALLOWED_TIME can be
     * configured here.
     * 
     * @param maximumExpirationDateOffset
     *            The offset in milliseconds.
     */
    public void setMaximumExpirationDateOffset(long maximumExpirationDateOffset) {
        this.maximumExpirationDateOffset = maximumExpirationDateOffset;
    }

    /**
     * Get the currently configured time zone id.
     * 
     * @return The time zone id.
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * Receive a
     * {@link eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig
     * SteemApiWrapperConfig} instance.
     * 
     * @return A SteemApiWrapperConfig instance.
     */
    public static SteemApiWrapperConfig getInstance() {
        if (steemApiWrapperConfigInstance == null) {
            steemApiWrapperConfigInstance = new SteemApiWrapperConfig();
        }

        return steemApiWrapperConfigInstance;
    }
}

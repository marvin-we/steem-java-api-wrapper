package eu.bittrade.libs.steemj.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.websocket.ClientEndpointConfig;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.SteemitAddressPrefix;

/**
 * This class stores the configuration that is used for the communication to the
 * defined web socket server.
 * 
 * The setters can be used to override the default values.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJConfig {
    private static final Logger LOGGER = LogManager.getLogger(SteemJConfig.class);
    private static final String DEFAULT_STEEM_NODE_URI = "wss://node.steem.ws";

    private static SteemJConfig steemJConfigInstance;

    /**
     * Receive a {@link eu.bittrade.libs.steemj.configuration.SteemJConfig
     * SteemJConfig} instance.
     * 
     * @return A SteemJConfig instance.
     */
    public static SteemJConfig getInstance() {
        if (steemJConfigInstance == null) {
            steemJConfigInstance = new SteemJConfig();
        }

        return steemJConfigInstance;
    }

    /**
     * Overrides the current
     * {@link eu.bittrade.libs.steemj.configuration.SteemJConfig SteemJConfig}
     * instance and returns a new one.
     * 
     * @return A SteemJConfig instance.
     */
    public static SteemJConfig getNewInstance() {
        steemJConfigInstance = new SteemJConfig();
        return steemJConfigInstance;
    }

    private ClientEndpointConfig clientEndpointConfig;
    private URI websocketEndpointURI;
    private long timeout;
    private String dateTimePattern;
    private long maximumExpirationDateOffset;
    private String timeZoneId;
    private AccountName apiUsername;
    private char[] apiPassword;
    private boolean sslVerificationDisabled;
    private PrivateKeyStorage privateKeyStorage;
    private Charset encodingCharset;

    private SteemitAddressPrefix steemitAddressPrefix;

    private String chainId;

    /**
     * Default constructor that will set all default values.
     */
    private SteemJConfig() {
        this.clientEndpointConfig = ClientEndpointConfig.Builder.create().build();
        try {
            this.setWebsocketEndpointURI(new URI(DEFAULT_STEEM_NODE_URI));
        } catch (URISyntaxException e) {
            // This can never happen!
            LOGGER.error("The configured default URI has a Syntax error.", e);
            this.websocketEndpointURI = null;
        }
        this.timeout = 1000;
        this.dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss";
        this.apiUsername = new AccountName(System.getProperty("steemj.api.username", ""));
        this.apiPassword = System.getProperty("steemj.api.password", "").toCharArray();
        this.maximumExpirationDateOffset = 3600000L;
        this.timeZoneId = "GMT";
        this.encodingCharset = StandardCharsets.UTF_8;
        this.privateKeyStorage = new PrivateKeyStorage();
        this.steemitAddressPrefix = SteemitAddressPrefix.STM;
        this.chainId = "0000000000000000000000000000000000000000000000000000000000000000";

        // Fill the key store with the provided accountName and private keys.
        AccountName primaryAccountName = new AccountName(System.getProperty("steemj.key.accountName", ""));
        if (!primaryAccountName.isEmpty()) {
            privateKeyStorage.addAccount(primaryAccountName);
            for (PrivateKeyType privateKeyType : PrivateKeyType.values()) {
                String wifPrivateKey = System.getProperty("steemj.key." + privateKeyType.name().toLowerCase());
                // Only add keys if they are present.
                if (wifPrivateKey != null && !wifPrivateKey.isEmpty()) {
                    privateKeyStorage.addPrivateKeyToAccount(primaryAccountName,
                            new ImmutablePair<PrivateKeyType, String>(privateKeyType, wifPrivateKey));
                }
            }
        }
    }

    /**
     * Get the currently configured password.
     * 
     * @return The currently configured password.
     */
    public char[] getApiPassword() {
        return apiPassword;
    }

    /**
     * Get the currently configured account name.
     * 
     * @return The currently configured account name.
     */
    public AccountName getApiUsername() {
        return apiUsername;
    }

    /**
     * Get the currently configured chain id used to sign transactions. For the
     * production chain the id is a 56bit long 0 sequence which is configured by
     * default.
     * 
     * @return The currently configured Chain ID.
     */
    public String getChainId() {
        return chainId;
    }

    /**
     * @return Get the configured ClientEndpointConfig instance.
     */
    public ClientEndpointConfig getClientEndpointConfig() {
        return clientEndpointConfig;
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
     * Get the currently configured Charset that will be used to encode Strings.
     * 
     * @return The configured Charset.
     */
    public Charset getEncodingCharset() {
        return encodingCharset;
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
     * Get the private key storage to manage the private keys for one or
     * multiple accounts.
     * 
     * The private keys have been defined by the account creator (e.g.
     * steemit.com) and are required to write data on the blockchain.
     *
     * <ul>
     * <li>A posting key is required to vote, post or comment on content.</li>
     * <li>An active key is required to interact with the market, to change keys
     * and to vote for witnesses.</li>
     * <li>An owner key is required to change the keys.</li>
     * <li>A memo key is required to use private messages.</li>
     * </ul>
     * 
     * @return The privateKeyStorage.
     */
    public PrivateKeyStorage getPrivateKeyStorage() {
        return privateKeyStorage;
    }

    /**
     * Get the currently configured Steemit address prefix. This prefix is used
     * to parse keys in their WIF format.
     * 
     * @return The Steemit address prefix.
     */
    public SteemitAddressPrefix getSteemitAddressPrefix() {
        return steemitAddressPrefix;
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
     * Get the currently configured time zone id.
     * 
     * @return The time zone id.
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * @return Get the configured websocket endpoint URI.
     */
    public URI getWebsocketEndpointURI() {
        return websocketEndpointURI;
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
     * Set the password which should be used to login to a node. This is not
     * required if the node is not protected.
     * 
     * @param apiPassword
     *            The password to use.
     */
    public void setApiPassword(char[] apiPassword) {
        this.apiPassword = apiPassword;
    }

    /**
     * Set the account name which should be used to login to a node. This is not
     * required if the node is not protected.
     * 
     * @param apiUsername
     *            The account name to use.
     */
    public void setApiUsername(AccountName apiUsername) {
        this.apiUsername = apiUsername;
    }

    /**
     * Set the chain id used to sign transactions. For the production chain the
     * id is a 56bit long 0 sequence which is configured by default.
     * 
     * @param chainId
     *            The chain id to set.
     */
    public void setChainId(String chainId) {
        this.chainId = chainId;
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
     * Define the Charset that should be used to encode Strings.
     * 
     * @param encodingCharset
     *            A Charset instance like StandardCharsets.UTF_8.
     */
    public void setEncodingCharset(Charset encodingCharset) {
        this.encodingCharset = encodingCharset;
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
     * Define if the SSL-Verification should be disabled.
     * 
     * @param sslVerificationDisabled
     *            Defines if the SSL-Verification should be disabled or not.
     */
    public void setSslVerificationDisabled(boolean sslVerificationDisabled) {
        this.sslVerificationDisabled = sslVerificationDisabled;
    }

    /**
     * Set the Steemit address prefix. This prefix is used to parse keys in
     * their WIF format.
     * 
     * @param steemitAddressPrefix
     *            The Steemit address prefix to set.
     */
    public void setSteemitAddressPrefix(SteemitAddressPrefix steemitAddressPrefix) {
        this.steemitAddressPrefix = steemitAddressPrefix;
    }

    /**
     * Override the default, maximum time that SteemJ will wait for an answer of
     * the Steem Node. If set to <code>0</code> the timeout mechanism will be
     * disabled.
     * 
     * @param timeout
     *            Time in milliseconds.
     * @throws IllegalArgumentException
     *             If the value of timeout is negative.
     */
    public void setTimeout(long timeout) {
        if (timeout < 0) {
            throw new IllegalArgumentException("The timeout has to be greater than 0. (0 will disable the timeout).");
        }

        this.timeout = timeout;
    }

    /**
     * This method has the same functionality than
     * {@link #setWebsocketEndpointURI(URI, boolean)
     * setWebsocketEndpointURI(URI, boolean)}, but this method will enable the
     * SSL verification by default.
     * 
     * @param websocketEndpointURI
     *            The URI of the node you want to connect to.
     * @throws URISyntaxException
     *             If the <code>websocketEndpointURI</code> is null.
     */
    public void setWebsocketEndpointURI(URI websocketEndpointURI) throws URISyntaxException {
        setWebsocketEndpointURI(websocketEndpointURI, false);
    }

    /**
     * Configure the connection to the Steem Node by providing the endpoint URI
     * and the SSL verification settings.
     * 
     * @param websocketEndpointURI
     *            The URI of the node you want to connect to.
     * @param sslVerificationDisabled
     *            Define if SteemJ should verify the SSL certificate of the
     *            endpoint. This option will be ignored if the given
     *            <code>websocketEndpointURI</code> is using a non SSL protocol.
     * @throws URISyntaxException
     *             If the <code>websocketEndpointURI</code> is null.
     */
    public void setWebsocketEndpointURI(URI websocketEndpointURI, boolean sslVerificationDisabled)
            throws URISyntaxException {
        if (websocketEndpointURI == null) {
            throw new URISyntaxException("websocketEndpointURI",
                    "The websocketEndpointURI can't be null, because a valid URI to the RPC endpoint of a Steem Node is required.");
        }

        this.websocketEndpointURI = websocketEndpointURI;
        this.sslVerificationDisabled = sslVerificationDisabled;
    }
}

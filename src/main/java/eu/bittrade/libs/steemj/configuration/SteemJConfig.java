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

    private static SteemJConfig steemJConfigInstance;

    private ClientEndpointConfig clientEndpointConfig;
    private URI websocketEndpointURI;
    private long timeout;
    private String dateTimePattern;
    private long maximumExpirationDateOffset;
    private String timeZoneId;
    private AccountName accountName;
    private char[] password;
    private boolean sslVerificationDisabled;
    private PrivateKeyStorage privateKeyStorage;
    private Charset encodingCharset;

    /**
     * Default constructor that will set all default values.
     */
    private SteemJConfig() {
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
        this.accountName = new AccountName(System.getProperty("steemj.api.accountName", ""));
        this.password = System.getProperty("steemj.api.password", "").toCharArray();
        this.sslVerificationDisabled = false;
        this.maximumExpirationDateOffset = 3600000L;
        this.timeZoneId = "GMT";
        this.encodingCharset = StandardCharsets.UTF_8;
        this.privateKeyStorage = new PrivateKeyStorage();

        // Fill the key store with the provided accountName and private keys.
        if (!this.getAccountName().isEmpty()) {
            privateKeyStorage.addAccount(this.getAccountName());
            for (PrivateKeyType privateKeyType : PrivateKeyType.values()) {
                String wifPrivateKey = System.getProperty("steemj.key." + privateKeyType.name().toLowerCase());
                // Only add keys if they are present.
                if (wifPrivateKey != null && !wifPrivateKey.isEmpty()) {
                    privateKeyStorage.addPrivateKeyToAccount(this.getAccountName(),
                            new ImmutablePair<PrivateKeyType, String>(privateKeyType, wifPrivateKey));
                }
            }
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
     * Set the account name which should be used for methods, that require
     * authentication.
     * 
     * @param accountName
     *            The account name to use.
     */
    public void setAccountName(AccountName accountName) {
        this.accountName = accountName;
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
     * Get the currently configured account name.
     * 
     * @return The currently configured account name.
     */
    public AccountName getAccountName() {
        return accountName;
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
     * Get the currently configured Charset that will be used to encode Strings.
     * 
     * @return The configured Charset.
     */
    public Charset getEncodingCharset() {
        return encodingCharset;
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
}

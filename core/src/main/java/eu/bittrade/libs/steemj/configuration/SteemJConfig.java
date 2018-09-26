package eu.bittrade.libs.steemj.configuration;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.websocket.ClientEndpointConfig;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.AddressPrefixType;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.SynchronizationType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemTimeoutException;

/**
 * This class stores the configuration that is used for the communication to the
 * defined server.
 * 
 * The setters can be used to override the default values.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(SteemJConfig.class);
	/** The endpoint URI used by default. */
	private static final String DEFAULT_STEEM_API_URI = "https://api.steemit.com";
	/** The SteemJ account. */
	private static final AccountName STEEMJ_ACCOUNT = new AccountName("steemj");
	/**
	 * The default beneficiary account.
	 */
	private AccountName beneficiaryAccount = STEEMJ_ACCOUNT;
	/** The SteemJ version. */
	private static final String STEEMJ_VERSION = SteemJ.class.getPackage().getImplementationVersion();
	/** The SteemJ App-Name */
	private static final String STEEMJ_NAME = SteemJ.class.getPackage().getImplementationTitle();
	/** The inner {@link SteemJConfig} instance. */
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
	private List<Pair<URI, Boolean>> endpointURIs;
	private int responseTimeout;
	private int idleTimeout;
	private String dateTimePattern;
	private long maximumExpirationDateOffset;
	private String timeZoneId;
	private AccountName apiUsername;
	private char[] apiPassword;
	private AccountName defaultAccount;
	private PrivateKeyStorage privateKeyStorage;
	private Charset encodingCharset;
	private AddressPrefixType addressPrefix;
	private String chainId;
	private short steemJWeight;
	private ValidationType validationLevel;
	private SynchronizationType synchronizationLevel;
	private AssetSymbolType dollarSymbol;
	private AssetSymbolType tokenSymbol;
	private AssetSymbolType vestsSymbol;

	/**
	 * Default constructor that will set all default values.
	 */
	private SteemJConfig() {
		this.clientEndpointConfig = ClientEndpointConfig.Builder.create().build();

		try {
			this.endpointURIs = new ArrayList<>();
			this.addEndpointURI(new URI(DEFAULT_STEEM_API_URI));
		} catch (URISyntaxException e) {
			// This can never happen!
			LOGGER.error("At least one of the configured default URIs has a Syntax error.", e);
		}
		this.responseTimeout = 1000;
		this.idleTimeout = 60000;
		this.dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss";
		this.apiUsername = new AccountName(System.getProperty("steemj.api.username", ""));
		this.apiPassword = System.getProperty("steemj.api.password", "").toCharArray();
		this.maximumExpirationDateOffset = 3600000L;
		this.timeZoneId = "GMT";
		this.encodingCharset = StandardCharsets.UTF_8;
		this.privateKeyStorage = new PrivateKeyStorage();
		this.addressPrefix = AddressPrefixType.STM;
		this.chainId = "0000000000000000000000000000000000000000000000000000000000000000";
		this.steemJWeight = 250;
		this.validationLevel = ValidationType.ALL;
		this.synchronizationLevel = SynchronizationType.FULL;
		this.dollarSymbol = AssetSymbolType.SBD;
		this.tokenSymbol = AssetSymbolType.STEEM;
		this.vestsSymbol = AssetSymbolType.VESTS;

		// Fill the key store with the provided accountName and private keys.
		this.defaultAccount = new AccountName(System.getProperty("steemj.default.account", ""));
		if (!this.defaultAccount.isEmpty()) {
			privateKeyStorage.addAccount(this.defaultAccount);
			for (PrivateKeyType privateKeyType : PrivateKeyType.values()) {
				String wifPrivateKey = System
						.getProperty("steemj.default.account." + privateKeyType.name().toLowerCase() + ".key");
				// Only add keys if they are present.
				if (wifPrivateKey != null && !wifPrivateKey.isEmpty()) {
					privateKeyStorage.addPrivateKeyToAccount(this.defaultAccount,
							new ImmutablePair<PrivateKeyType, String>(privateKeyType, wifPrivateKey));
				}
			}
		}
	}

	/**
	 * Get the currently configured <code>synchronizationLevel</code>.
	 * 
	 * The <code>synchronizationLevel</code> defines which values should be
	 * synchronized between this SteemJ instance and the connected node. By default,
	 * SteemJ will synchronize as much as possible.
	 * 
	 * @return The synchronization level.
	 */
	public SynchronizationType getSynchronizationLevel() {
		return synchronizationLevel;
	}

	/**
	 * Override the currently configured <code>synchronizationLevel</code>.
	 * 
	 * The <code>synchronizationLevel</code> defines which values should be
	 * synchronized between this SteemJ instance and the connected node. By default,
	 * SteemJ will synchronize as much as possible.
	 * 
	 * @param synchronizationLevel
	 *            The synchronization level to set.
	 */
	public void setSynchronizationLevel(SynchronizationType synchronizationLevel) {
		this.synchronizationLevel = synchronizationLevel;
	}

	/**
	 * Get the currently configured password used to login at a Steem Node to access
	 * protected APIs.
	 * 
	 * @return The currently configured password for the API access.
	 */
	public char[] getApiPassword() {
		return apiPassword;
	}

	/**
	 * Get the currently configured account name used to login at a Steem Node to
	 * access protected APIs.
	 * 
	 * @return The currently configured account name for the API access.
	 */
	public AccountName getApiUsername() {
		return apiUsername;
	}

	/**
	 * Get the currently configured default account name. The default account name
	 * is used for operations, if no other account has been provided.
	 * 
	 * @return The configured default account.
	 */
	public AccountName getDefaultAccount() {
		return defaultAccount;
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
	 * Get the configured ClientEndpointConfig instance. Please be aware that the
	 * <code>clientEndpointConfig</code> is only used for WebSocket endpoints.
	 * 
	 * @return The configured ClientEndpointConfig instance.
	 */
	public ClientEndpointConfig getClientEndpointConfig() {
		return clientEndpointConfig;
	}

	/**
	 * Get the currently configured date time pattern. This date pattern is used to
	 * serialize and deserialize JSON/Java objects.
	 * 
	 * @return The used date time pattern used for serialization/deserialization.
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
	 * Get the private key storage to manage the private keys for one or multiple
	 * accounts.
	 * 
	 * The private keys have been defined by the account creator (e.g. steemit.com)
	 * and are required to write data on the blockchain.
	 *
	 * <ul>
	 * <li>A posting key is required to vote, post or comment on content.</li>
	 * <li>An active key is required to interact with the market, to change keys and
	 * to vote for witnesses.</li>
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
	 * Get the currently configured address prefix. This prefix is used to parse
	 * keys in their WIF format.
	 * 
	 * @return The address prefix.
	 */
	public AddressPrefixType getAddressPrefix() {
		return addressPrefix;
	}

	/**
	 * Get the configured, maximum time that SteemJ will wait for an answer of the
	 * endpoint before throwing a {@link SteemTimeoutException} exception.
	 * 
	 * @return Time in milliseconds
	 */
	public int getResponseTimeout() {
		return responseTimeout;
	}

	/**
	 * Get the configured, maximum time that SteemJ will keep an unused connection
	 * open. A value that is 0 or negative indicates the sessions will never timeout
	 * due to inactivity.
	 * 
	 * @return The time in milliseconds a connection should be left intact even when
	 *         no activities are performed.
	 */
	public int getIdleTimeout() {
		return idleTimeout;
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
	 * @return Get all configured endpoint URIs.
	 */
	public List<Pair<URI, Boolean>> getEndpointURIs() {
		return endpointURIs;
	}

	/**
	 * Get one of the configured endpoint URIs by providing a <code>selector</code>.
	 * 
	 * @param selector
	 *            A number used to calculate the next stored endpoint URI from the
	 *            list of configured endpoint URIs.
	 * @return One specific endpoint URI.
	 */
	public Pair<URI, Boolean> getNextEndpointURI(int selector) {
		return endpointURIs.get(((int) (selector % endpointURIs.size())));
	}

	/**
	 * Get the currently configured beneficiary weight.
	 * 
	 * @return The beneficiary weight.
	 */
	public short getSteemJWeight() {
		return steemJWeight;
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
	 * Set the chain id used to sign transactions. For the production chain the id
	 * is a 56bit long 0 sequence which is configured by default.
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
	 * Override the default date pattern. This date pattern is used to serialize and
	 * deserialize JSON/Java objects.
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
	 * A Steem Node will only accept transactions whose expiration date is not to
	 * far in the future.
	 * 
	 * <p>
	 * Example:<br>
	 * Time now: 2017-04-20 20:33 Latest allowed expiration date: 2017-04-20 21:24
	 * </p>
	 * 
	 * The difference between $NOW and the $MAXIMAL_ALLOWED_TIME can be configured
	 * here.
	 * 
	 * @param maximumExpirationDateOffset
	 *            The offset in milliseconds.
	 */
	public void setMaximumExpirationDateOffset(long maximumExpirationDateOffset) {
		this.maximumExpirationDateOffset = maximumExpirationDateOffset;
	}

	/**
	 * Set the address prefix. This prefix is used to parse keys in their WIF
	 * format.
	 * 
	 * @param addressPrefix
	 *            The address prefix to set.
	 */
	public void setAddressPrefix(AddressPrefixType addressPrefix) {
		this.addressPrefix = addressPrefix;
	}

	/**
	 * Override the default, maximum time that SteemJ will wait for an answer of the
	 * Steem Node. If set to <code>0</code> the timeout mechanism will be disabled.
	 * 
	 * @param responseTimeout
	 *            Time in milliseconds.
	 * @throws IllegalArgumentException
	 *             If the value of timeout is negative.
	 */
	public void setResponseTimeout(int responseTimeout) {
		if (responseTimeout < 0) {
			throw new IllegalArgumentException("The timeout has to be greater than 0. (0 will disable the timeout).");
		}

		this.responseTimeout = responseTimeout;
	}

	/**
	 * Override the default validation level that SteemJ will use to validate if an
	 * Object contains valid information before broadcasting it to the Steem Node.
	 * By default SteemJ will validate as much as possible.
	 * 
	 * @param validationLevel
	 *            The validation level to set.
	 */
	public void setValidationLevel(ValidationType validationLevel) {
		this.validationLevel = validationLevel;
	}

	/**
	 * Override the default, maximum time that SteemJ will keep an unused connection
	 * open.A value that is 0 or negative indicates the sessions will never timeout
	 * due to inactivity.
	 *
	 * @param idleTimeout
	 *            The time in milliseconds a connection should be left intact even
	 *            when no activities are performed.
	 */
	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	/**
	 * Override the currently configured <code>endpointURIs</code>.
	 * 
	 * @param endpointURIs
	 *            A list of endpoints to connect to.
	 */
	public void setEndpointURIs(List<Pair<URI, Boolean>> endpointURIs) {
		this.endpointURIs = endpointURIs;
	}

	/**
	 * This method has the same functionality than
	 * {@link #addEndpointURI(URI, boolean) setEndpointURI(URI, boolean)}, but this
	 * method will enable the SSL verification by default.
	 * 
	 * @param endpointURI
	 *            The URI of the node you want to connect to.
	 * @throws URISyntaxException
	 *             If the <code>endpointURI</code> is null.
	 */
	public void addEndpointURI(URI endpointURI) throws URISyntaxException {
		addEndpointURI(endpointURI, false);
	}

	/**
	 * Configure the connection to the Steem Node by providing the endpoint URI and
	 * the SSL verification settings.
	 * 
	 * @param endpointURI
	 *            The URI of the node you want to connect to.
	 * @param sslVerificationDisabled
	 *            Define if SteemJ should verify the SSL certificate of the
	 *            endpoint. This option will be ignored if the given
	 *            <code>endpointURI</code> is using a non SSL protocol.
	 * @throws URISyntaxException
	 *             If the <code>endpointURI</code> is null.
	 */
	public void addEndpointURI(URI endpointURI, boolean sslVerificationDisabled) throws URISyntaxException {
		if (endpointURI == null) {
			throw new URISyntaxException("endpointURI", "The endpointURI can't be null.");
		}

		this.endpointURIs.add(new ImmutablePair<URI, Boolean>(endpointURI, sslVerificationDisabled));
	}

	/**
	 * Set the default account used for simplified operations.
	 * 
	 * @param defaultAccount
	 *            The account to set.
	 */
	public void setDefaultAccount(AccountName defaultAccount) {
		this.defaultAccount = defaultAccount;
	}

	/**
	 * Set the currently configured beneficiary weight.
	 * 
	 * @param steemJWeight
	 *            The beneficiary weight for SteemJ.
	 */
	public void setSteemJWeight(short steemJWeight) {
		this.steemJWeight = steemJWeight;
	}

	/**
	 * Get the currently configured validation level.
	 * 
	 * @return The currently configured validation level.
	 */
	public ValidationType getValidationLevel() {
		return validationLevel;
	}

	/**
	 * Get the currently configured {@link AssetSymbolType} for dollars (e.g. SBD).
	 * The configured symbol type is used to validate VESTS fields of operations.
	 * 
	 * @return The currently configured {@link AssetSymbolType} for dollars.
	 */
	public AssetSymbolType getDollarSymbol() {
		return dollarSymbol;
	}

	/**
	 * Override the default <code>dollarSymbol</code>. The configured symbol type is
	 * used to validate VESTS fields of operations.
	 * 
	 * @param dollarSymbol
	 *            The {@link AssetSymbolType} for dollars to set.
	 */
	public void setDollarSymbol(AssetSymbolType dollarSymbol) {
		this.dollarSymbol = dollarSymbol;
	}

	/**
	 * Get the currently configured {@link AssetSymbolType} for tokens (e.g. STEEM).
	 * The configured symbol type is used to validate VESTS fields of operations.
	 * 
	 * @return The currently configured {@link AssetSymbolType} for tokens.
	 */
	public AssetSymbolType getTokenSymbol() {
		return tokenSymbol;
	}

	/**
	 * Override the default <code>tokenSymbol</code>. The configured symbol type is
	 * used to validate token fields of operations.
	 * 
	 * @param tokenSymbol
	 *            The {@link AssetSymbolType} for tokens to set.
	 */
	public void setTokenSymbol(AssetSymbolType tokenSymbol) {
		this.tokenSymbol = tokenSymbol;
	}

	/**
	 * Get the currently configured {@link AssetSymbolType} for VESTS. The
	 * configured symbol type is used to validate VESTS fields of operations.
	 * 
	 * @return The currently configured {@link AssetSymbolType} for VESTS.
	 */
	public AssetSymbolType getVestsSymbol() {
		return vestsSymbol;
	}

	/**
	 * Override the default <code>vestsSymbol</code>. The configured symbol type is
	 * used to validate VESTS fields of operations.
	 * 
	 * @param vestsSymbol
	 *            The {@link AssetSymbolType} for VESTS to set.
	 */
	public void setVestsSymbol(AssetSymbolType vestsSymbol) {
		this.vestsSymbol = vestsSymbol;
	}

	/**
	 * @return The official SteemJ account name.
	 */
	public static AccountName getSteemJAccount() {
		return STEEMJ_ACCOUNT;
	}

	/**
	 * Return the current default beneficiary account. (Defaults to
	 * {@link #getSteemJAccount})
	 * 
	 * @return
	 */
	public AccountName getBeneficiaryAccount() {
		return beneficiaryAccount;
	}

	/**
	 * Set a new default beneficiary account. If you don't want any beneficiary
	 * being set, use {@link #setSteemJWeight} and set the weight to 0}.
	 * 
	 * @param beneficiaryAccount
	 */
	public void setBeneficiaryAccount(AccountName beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}	

	/**
	 * Get the version of SteemJ that is currently used. This parameter is set
	 * during the build and can't be changed.
	 * 
	 * @return The SteemJ version that is currently used.
	 */
	public static String getSteemJVersion() {
		return STEEMJ_VERSION;
	}

	/**
	 * Get the application name of SteemJ. This parameter is set during the build
	 * and can't be changed.
	 * 
	 * @return The application name of SteemJ.
	 */
	public static String getSteemJAppName() {
		return STEEMJ_NAME;
	}
}

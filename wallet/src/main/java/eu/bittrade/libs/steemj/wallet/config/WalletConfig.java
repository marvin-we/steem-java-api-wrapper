package eu.bittrade.libs.steemj.wallet.config;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;

/**
 * This class stores the configuration that is used for the communication to the
 * defined web socket server.
 * 
 * The setters can be used to override the default values.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WalletConfig {
    private final static SteemJConfig STEEMJ_CONFIG = SteemJConfig.getInstance();

    private short beneficialRewardPercentage;
}

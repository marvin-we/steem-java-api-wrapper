package eu.bittrade.libs.steemj.plugins.apis.witness.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi;
import eu.bittrade.libs.steemj.plugins.apis.witness.enums.BandwidthType;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class implements the Steem "get_account_bandwidth_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetAccountBandwidthArgs {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("type")
    private BandwidthType type;

    /**
     * Create a new {@link GetAccountBandwidthArgs} instance to be passed to the
     * {@link WitnessApi#getAccountBandwidth(CommunicationHandler, GetAccountBandwidthArgs)}
     * method.
     * 
     * @param account
     *            The account name request the bandwidth for.
     * @param type
     *            The {@link BandwidthType} to request.
     */
    @JsonCreator()
    public GetAccountBandwidthArgs(@JsonProperty("account") AccountName account,
            @JsonProperty("type") BandwidthType type) {
        this.setAccount(account);
        this.setType(type);
    }

    /**
     * @return the account
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * @param account
     *            the account to set
     */
    public void setAccount(AccountName account) {
        this.account = account;
    }

    /**
     * @return the type
     */
    public BandwidthType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(BandwidthType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

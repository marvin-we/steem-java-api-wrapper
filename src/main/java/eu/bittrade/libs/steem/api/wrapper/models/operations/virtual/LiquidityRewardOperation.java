package eu.bittrade.libs.steem.api.wrapper.models.operations.virtual;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;

/**
 * This class represents the Steem "liquidity_reward_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LiquidityRewardOperation extends Operation {
    private AccountName owner;
    private Asset payout;

    /**
     * @return the owner
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * @return the payout
     */
    public Asset getPayout() {
        return payout;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // The byte representation is not needed for virtual operations as we
        // can't broadcast them.
        return new byte[0];
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

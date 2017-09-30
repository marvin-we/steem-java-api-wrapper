package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "claim_reward_balance_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ClaimRewardBalanceOperation extends Operation {
    @JsonProperty("account")
    private AccountName account;
    @JsonProperty("reward_steem")
    private Asset rewardSteem;
    @JsonProperty("reward_sbd")
    private Asset rewardSbd;
    @JsonProperty("reward_vests")
    private Asset rewardVests;

    /**
     * Create a new and empty claim reward balance operation.
     * 
     * @param account
     *            The account to claim the rewards for (see
     *            {@link #setAccount(AccountName)}).
     * @param rewardSteem
     *            The amount of Steem to claim (see
     *            {@link #setRewardSteem(Asset)}).
     * @param rewardSbd
     *            The amount of SBD to claim (see {@link #setRewardSbd(Asset)}).
     * @param rewardVests
     *            The amount of VESTS to claim (see
     *            {@link #setRewardVests(Asset)}).
     * @throws InvalidParameterException
     *             If one of the parameters does not fulfill the requirements.
     */
    @JsonCreator
    public ClaimRewardBalanceOperation(@JsonProperty("account") AccountName account,
            @JsonProperty("reward_steem") Asset rewardSteem, @JsonProperty("reward_sbd") Asset rewardSbd,
            @JsonProperty("reward_vests") Asset rewardVests) {
        super(false);

        this.setAccount(account);
        this.setRewardSbd(rewardSbd);
        this.setRewardSteem(rewardSteem);
        this.setRewardVests(rewardVests);
    }

    /**
     * Get the account the reward should be collected for.
     * 
     * @return The account name.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Set the account the reward should be collected for. <b>Notice:</b> The
     * private posting key of this account needs to be stored in the key
     * storage.
     * 
     * @param account
     *            The account name.
     * @throws InvalidParameterException
     *             If the <code>account</code> is null.
     */
    public void setAccount(AccountName account) {
        if (account == null) {
            throw new InvalidParameterException("The account can't be null.");
        }

        this.account = account;
    }

    /**
     * Get the amount of Steem that should be collected.
     * 
     * @return The amount of Steem.
     */
    public Asset getRewardSteem() {
        return rewardSteem;
    }

    /**
     * Set the amount of Steem that should be collected. Please note that it is
     * not possible to collect more than that what is available. You can check
     * the available amount by requesting the Account information using
     * {@link eu.bittrade.libs.steemj.steemJ#getAccounts(java.util.List)
     * getAccounts(List)} method.
     * 
     * @param rewardSteem
     *            The amount of Steem to collect.
     * @throws InvalidParameterException
     *             If the provided <code>rewardSteem</code> is null, does not
     *             have the symbol type STEEM or the amount to claim is
     *             negative.
     */
    public void setRewardSteem(Asset rewardSteem) {
        if (rewardSteem == null) {
            throw new InvalidParameterException("The STEEM reward can't be null.");
        } else if (!rewardSteem.getSymbol().equals(AssetSymbolType.VESTS)) {
            throw new InvalidParameterException("The STEEM reward must be of symbol type STEEM.");
        } else if (rewardSteem.getAmount() < 0) {
            throw new InvalidParameterException("Cannot claim a negative STEEM amount");
        }

        this.rewardSteem = rewardSteem;
    }

    /**
     * Get the amount of Steem Doller that should be collected.
     * 
     * @return The amount of Steem Doller.
     */
    public Asset getRewardSbd() {
        return rewardSbd;
    }

    /**
     * Set the amount of Steem Dollers that should be collected. Please note
     * that it is not possible to collect more than that what is available. You
     * can check the available amount by requesting the Account information
     * using {@link eu.bittrade.libs.steemj.steemJ#getAccounts(java.util.List)
     * getAccounts(List)} method.
     * 
     * @param rewardSbd
     *            The amount of Steem Dollers to collect.
     * @throws InvalidParameterException
     *             If the provided <code>rewardSbd</code> is null, does not have
     *             the symbol type SBD or the amount to claim is negative.
     */
    public void setRewardSbd(Asset rewardSbd) {
        if (rewardSbd == null) {
            throw new InvalidParameterException("The SBD reward can't be null.");
        } else if (!rewardSbd.getSymbol().equals(AssetSymbolType.SBD)) {
            throw new InvalidParameterException("The SBD reward must be of symbol type SBD.");
        } else if (rewardSbd.getAmount() < 0) {
            throw new InvalidParameterException("Cannot claim a negative SBD amount");
        }

        this.rewardSbd = rewardSbd;
    }

    /**
     * Get the amount of Vests that should be collected.
     * 
     * @return The amount of Vests.
     */
    public Asset getRewardVests() {
        return rewardVests;
    }

    /**
     * Set the amount of Vests that should be collected. Please note that it is
     * not possible to collect more than that what is available. You can check
     * the available amount by requesting the Account information using
     * {@link eu.bittrade.libs.steemj.steemJ#getAccounts(java.util.List)
     * getAccounts(List)} method.
     * 
     * @param rewardVests
     *            The amount of Vests to collect.
     * @throws InvalidParameterException
     *             If the provided <code>rewardVests</code> is null, does not
     *             have the symbol type VESTS or the amount to claim is
     *             negative.
     */
    public void setRewardVests(Asset rewardVests) {
        if (rewardVests == null) {
            throw new InvalidParameterException("The VESTS reward can't be null.");
        } else if (!rewardVests.getSymbol().equals(AssetSymbolType.VESTS)) {
            throw new InvalidParameterException("The VESTS reward must be of symbol type VESTS.");
        } else if (rewardVests.getAmount() < 0) {
            throw new InvalidParameterException("Cannot claim a negative VESTS amount");
        }

        this.rewardVests = rewardVests;
    }

    /**
     * TODO: Validate all parameter of this Operation type.
     */
    public void validate() {
        /*
         * validate_account_name(account); FC_ASSERT(is_asset_type(reward_steem,
         * STEEM_SYMBOL), "Reward Steem must be STEEM");
         * FC_ASSERT(is_asset_type(reward_sbd, SBD_SYMBOL),
         * "Reward Steem must be SBD"); FC_ASSERT(is_asset_type(reward_vests,
         * VESTS_SYMBOL), "Reward Steem must be VESTS");
         * FC_ASSERT(reward_steem.amount >= 0,
         * "Cannot claim a negative amount"); FC_ASSERT(reward_sbd.amount >= 0,
         * "Cannot claim a negative amount"); FC_ASSERT(reward_vests.amount >=
         * 0, "Cannot claim a negative amount"); FC_ASSERT(reward_steem.amount >
         * 0 || reward_sbd.amount > 0 || reward_vests.amount > 0,
         * "Must claim something.");
         */
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedClaimRewardOperation = new ByteArrayOutputStream()) {
            serializedClaimRewardOperation.write(
                    SteemJUtils.transformIntToVarIntByteArray(OperationType.CLAIM_REWARD_BALANCE_OPERATION.ordinal()));
            serializedClaimRewardOperation.write(this.getAccount().toByteArray());
            serializedClaimRewardOperation.write(this.getRewardSteem().toByteArray());
            serializedClaimRewardOperation.write(this.getRewardSbd().toByteArray());
            serializedClaimRewardOperation.write(this.getRewardVests().toByteArray());

            return serializedClaimRewardOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getAccount(), PrivateKeyType.POSTING);
    }
}

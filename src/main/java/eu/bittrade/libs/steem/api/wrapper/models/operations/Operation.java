package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.interfaces.ByteTransformable;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.AuthorRewardOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.CommentBenefactorRewardOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.CommentPayoutUpdateOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.CommentRewardOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.CurationRewardOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.FillConvertRequestOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.FillOrderOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.FillTransferFromSavingsOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.FillVestingWithdrawOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.HardforkOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.InterestOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.LiquidityRewardOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.ReturnVestingDelegationOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.virtual.ShutdownWitnessOpeartion;

/**
 * This class is a wrapper for the different kinds of operations that an user
 * can perform.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY)
@JsonSubTypes({ @Type(value = VoteOperation.class, name = "vote"),
        @Type(value = CommentOperation.class, name = "comment"),
        @Type(value = ClaimRewardBalanceOperation.class, name = "claim_reward_balance"),
        @Type(value = ConvertOperation.class, name = "convert"),
        @Type(value = CustomJsonOperation.class, name = "custom_json"),
        @Type(value = CustomOperation.class, name = "custom"),
        @Type(value = AccountWitnessVoteOperation.class, name = "account_witness_vote"),
        @Type(value = TransferToVestingOperation.class, name = "transfer_to_vesting"),
        @Type(value = TransferOperation.class, name = "transfer"),
        @Type(value = LimitOrderCreateOperation.class, name = "limit_order_create"),
        @Type(value = LimitOrderCreate2Operation.class, name = "limit_order_create2"),
        @Type(value = LimitOrderCancelOperation.class, name = "limit_order_cancel"),
        @Type(value = CommentOptionsOperation.class, name = "comment_options"),
        @Type(value = PowOperation.class, name = "pow"), 
        @Type(value = Pow2Operation.class, name = "pow2"),
        @Type(value = FeedPublishOperation.class, name = "feed_publish"),
        @Type(value = DeleteCommentOperation.class, name = "delete_comment"),
        @Type(value = WithdrawVestingOperation.class, name = "withdraw_vesting"),
        @Type(value = RequestAccountRecoveryOperation.class, name = "request_account_recovery"),
        @Type(value = SetWithdrawVestingRouteOperation.class, name = "set_withdraw_vesting_route"),
        @Type(value = AccountWitnessProxyOperation.class, name = "account_witness_proxy"),
        @Type(value = AccountUpdateOperation.class, name = "account_update"),
        @Type(value = WitnessUpdateOperation.class, name = "witness_update"),
        @Type(value = AccountCreateOperation.class, name = "account_create"),
        // Virtual Operations
        @Type(value = AuthorRewardOperation.class, name = "author_reward"),
        @Type(value = CommentBenefactorRewardOperation.class, name = "comment_benefactor_reward"),
        @Type(value = CommentPayoutUpdateOperation.class, name = "comment_payout_update"),
        @Type(value = CommentRewardOperation.class, name = "comment_reward"),
        @Type(value = CurationRewardOperation.class, name = "curation_reward"),
        @Type(value = FillConvertRequestOperation.class, name = "fill_convert_request"),
        @Type(value = FillOrderOperation.class, name = "fill_order"),
        @Type(value = FillTransferFromSavingsOperation.class, name = "fill_transfer_from_savings"),
        @Type(value = FillVestingWithdrawOperation.class, name = "fill_vesting_withdraw"),
        @Type(value = HardforkOperation.class, name = "hardfork"),
        @Type(value = InterestOperation.class, name = "interest"),
        @Type(value = LiquidityRewardOperation.class, name = "liquidity_reward"),
        @Type(value = ReturnVestingDelegationOperation.class, name = "return_Vesting_delegation"),
        @Type(value = ShutdownWitnessOpeartion.class, name = "shutdown_witness") })
public abstract class Operation implements ByteTransformable {
    /**
     * This field contains the private key type that is required for this
     * specific operation.
     */
    @JsonIgnore
    protected PrivateKeyType requiredPrivateKeyType;

    /**
     * Constructor used to hide the public one and also to force the sub classes
     * to define the required private key type.
     * 
     * @param requiredPrivateKeyType
     *            The required private key type for this operation.
     */
    protected Operation(PrivateKeyType requiredPrivateKeyType) {
        this.requiredPrivateKeyType = requiredPrivateKeyType;
    }

    /**
     * Default constructor which will not set the required private key type.
     * This constructor should only be used for virtual operations.
     */
    protected Operation() {

    }

    /**
     * Get the private key type that is required for this operation.
     * 
     * @return The required private key type for this operation.
     */
    public PrivateKeyType getRequiredPrivateKeyType() {
        return requiredPrivateKeyType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
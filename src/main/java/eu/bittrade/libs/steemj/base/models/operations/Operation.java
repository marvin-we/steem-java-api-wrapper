package eu.bittrade.libs.steemj.base.models.operations;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.operations.virtual.AuthorRewardOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.CommentBenefactorRewardOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.CommentPayoutUpdateOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.CommentRewardOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.CurationRewardOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.FillConvertRequestOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.FillOrderOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.FillTransferFromSavingsOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.FillVestingWithdrawOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.HardforkOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.InterestOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.LiquidityRewardOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.ReturnVestingDelegationOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.ShutdownWitnessOpeartion;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.plugins.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.plugins.follow.models.operations.ReblogOperation;

/**
 * This class is a wrapper for the different kinds of operations that an user
 * can perform.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY)
@JsonSubTypes({ @Type(value = VoteOperation.class, name = "vote"),
        @Type(value = CommentOperation.class, name = "comment"),
        @Type(value = TransferOperation.class, name = "transfer"),
        @Type(value = TransferToVestingOperation.class, name = "transfer_to_vesting"),
        @Type(value = WithdrawVestingOperation.class, name = "withdraw_vesting"),
        @Type(value = LimitOrderCreateOperation.class, name = "limit_order_create"),
        @Type(value = LimitOrderCancelOperation.class, name = "limit_order_cancel"),
        @Type(value = FeedPublishOperation.class, name = "feed_publish"),
        @Type(value = ConvertOperation.class, name = "convert"),
        @Type(value = AccountCreateOperation.class, name = "account_create"),
        @Type(value = AccountUpdateOperation.class, name = "account_update"),
        @Type(value = WitnessUpdateOperation.class, name = "witness_update"),
        @Type(value = AccountWitnessVoteOperation.class, name = "account_witness_vote"),
        @Type(value = AccountWitnessProxyOperation.class, name = "account_witness_proxy"),
        @Type(value = PowOperation.class, name = "pow"), @Type(value = CustomOperation.class, name = "custom"),
        @Type(value = ReportOverProductionOperation.class, name = "report_over_production"),
        @Type(value = DeleteCommentOperation.class, name = "delete_comment"),
        @Type(value = CustomJsonOperation.class, name = "custom_json"),
        @Type(value = CommentOptionsOperation.class, name = "comment_options"),
        @Type(value = SetWithdrawVestingRouteOperation.class, name = "set_withdraw_vesting_route"),
        @Type(value = LimitOrderCreate2Operation.class, name = "limit_order_create2"),
        @Type(value = ChallengeAuthorityOperation.class, name = "challenge_authority"),
        @Type(value = ProveAuthorityOperation.class, name = "prove_authority"),
        @Type(value = RequestAccountRecoveryOperation.class, name = "request_account_recovery"),
        @Type(value = RecoverAccountOperation.class, name = "recover_account"),
        @Type(value = ChangeRecoveryAccountOperation.class, name = "change_recovery_account"),
        @Type(value = EscrowTransferOperation.class, name = "escrow_transfer"),
        @Type(value = EscrowDisputeOperation.class, name = "escrow_dispute"),
        @Type(value = EscrowReleaseOperation.class, name = "escrow_release"),
        @Type(value = Pow2Operation.class, name = "pow2"),
        @Type(value = EscrowApproveOperation.class, name = "escrow_approve"),
        @Type(value = TransferToSavingsOperation.class, name = "transfer_to_savings"),
        @Type(value = TransferFromSavingsOperation.class, name = "transfer_from_savings"),
        @Type(value = CancelTransferFromSavingsOperation.class, name = "cancel_transfer_from_savings"),
        @Type(value = CustomBinaryOperation.class, name = "custom_binary"),
        @Type(value = DeclineVotingRightsOperation.class, name = "decline_voting_rights"),
        @Type(value = ResetAccountOperation.class, name = "reset_account"),
        @Type(value = SetResetAccountOperation.class, name = "set_reset_account"),
        @Type(value = ClaimRewardBalanceOperation.class, name = "claim_reward_balance"),
        @Type(value = DelegateVestingSharesOperation.class, name = "delegate_vesting_shares"),
        @Type(value = AccountCreateWithDelegationOperation.class, name = "account_create_with_delegation"),
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
        @Type(value = ReturnVestingDelegationOperation.class, name = "return_vesting_delegation"),
        @Type(value = ShutdownWitnessOpeartion.class, name = "shutdown_witness"),
        // Follow Plugin Operations
        @Type(value = ReblogOperation.class, name = "reblog_operation"),
        @Type(value = FollowOperation.class, name = "follow_operation") })
public abstract class Operation implements ByteTransformable {
    /**
     * This field contains the private key types required for this specific
     * operation.
     */
    @JsonIgnore
    protected List<ImmutablePair<AccountName, PrivateKeyType>> requiredPrivateKeyTypes = new ArrayList<>();
    /**
     * This field is used to store the operation type.
     */
    @JsonIgnore
    private boolean virtual;

    /**
     * Create a new Operation object by providing the operation type.
     */
    protected Operation(boolean virtual) {
        this.virtual = virtual;
    }

    /**
     * Define the required private key types required to sign this operation.
     * 
     * @param requiredPrivateKeyTypes
     *            A list of private key types.
     */
    protected void setRequiredPrivateKeyTypes(
            List<ImmutablePair<AccountName, PrivateKeyType>> requiredPrivateKeyTypes) {
        this.requiredPrivateKeyTypes = requiredPrivateKeyTypes;
    }

    /**
     * Get the list of required private key types to sign the transaction with.
     * 
     * @return The required private key types for this operation.
     */
    public List<ImmutablePair<AccountName, PrivateKeyType>> getRequiredPrivateKeyTypes() {
        return requiredPrivateKeyTypes;
    }

    /**
     * Returns {@code true} if, and only if, the operation is a virtual
     * operation.
     *
     * @return {@code true} if the operation is a virtual operation, otherwise
     *         {@code false}
     */
    public boolean isVirtual() {
        return virtual;
    }

    /**
     * Override the current list of required private key types with a new single
     * value.
     * 
     * @param accountName
     *            The account name whose private key is required.
     * @param privateKeyType
     *            The type of the required private key.
     */
    protected void addRequiredPrivateKeyType(AccountName accountName, PrivateKeyType privateKeyType) {
        // Reset the existing list.
        requiredPrivateKeyTypes = new ArrayList<>();
        // And add a new value.
        requiredPrivateKeyTypes.add(new ImmutablePair<>(accountName, privateKeyType));
        this.setRequiredPrivateKeyTypes(requiredPrivateKeyTypes);
    }

    /**
     * TODO
     * 
     * @param requiredPrivateKeyType
     */
    protected void addRequiredPrivateKeyType(List<ImmutablePair<AccountName, PrivateKeyType>> requiredPrivateKeyType) {
        // TODO
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
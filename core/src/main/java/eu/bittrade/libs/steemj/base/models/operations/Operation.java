package eu.bittrade.libs.steemj.base.models.operations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

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
import eu.bittrade.libs.steemj.base.models.operations.virtual.ProducerRewardOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.ReturnVestingDelegationOperation;
import eu.bittrade.libs.steemj.base.models.operations.virtual.ShutdownWitnessOpeartion;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.interfaces.Validatable;

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
        @Type(value = ProducerRewardOperation.class, name = "producer_reward") })
public abstract class Operation extends BaseOperation implements ByteTransformable, Validatable {
    /**
     * This field is used to store the operation type.
     */
    @JsonIgnore
    private boolean virtual;

    /**
     * Create a new Operation object by providing the operation type.
     * 
     * @param virtual
     *            Define if the operation instance is a virtual
     *            (<code>true</code>) or a market operation
     *            (<code>false</code>).
     */
    protected Operation(boolean virtual) {
        this.virtual = virtual;
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
     * Add the authorities which are required to sign this operation to an
     * existing map.
     * 
     * @param requiredAuthoritiesBase
     *            A map to which the required authorities of this operation
     *            should be added to.
     * 
     * @return A map of required authorities.
     */
    public abstract Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase);

    /**
     * Use this helper method to merge a single <code>accountName</code> into
     * the <code>requiredAuthoritiesBase</code>.
     * 
     * @param requiredAuthoritiesBase
     *            A map to which the required authorities of this operation
     *            should be added to.
     * @param signatureObject
     *            The signature object (e.g. an account names) to merge into the
     *            list.
     * @param privateKeyType
     *            The required key type.
     * @return The merged set of signature objects and required private key
     *         types.
     */
    protected Map<SignatureObject, PrivateKeyType> mergeRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase, SignatureObject signatureObject,
            PrivateKeyType privateKeyType) {
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = requiredAuthoritiesBase;
        if (requiredAuthorities == null) {
            requiredAuthorities = new HashMap<>();
        } else if (requiredAuthorities.containsKey(signatureObject)
                && requiredAuthorities.get(signatureObject) != null) {
            // Only the most powerful key is needed to sign the transaction, so
            // if there is already the same key type or a higher key type there
            // is no need to change it.
            if (requiredAuthorities.get(signatureObject).ordinal() > privateKeyType.ordinal()) {
                requiredAuthorities.put(signatureObject, privateKeyType);
            }
        } else {
            requiredAuthorities.put(signatureObject, privateKeyType);
        }

        return requiredAuthorities;
    }

    /**
     * Use this helper method to merge a a list of account names into the
     * <code>requiredAuthoritiesBase</code>.
     * 
     * @param requiredAuthoritiesBase
     *            A map to which the required authorities of this operation
     *            should be added to.
     * @param signatureObjects
     *            The signature objects (e.g. a list of account names) to merge
     *            into the list.
     * @param privateKeyType
     *            The required key type.
     * @return The merged set of signature objects and required private key
     *         types.
     */
    protected Map<SignatureObject, PrivateKeyType> mergeRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase,
            List<? extends SignatureObject> signatureObjects, PrivateKeyType privateKeyType) {
        Map<SignatureObject, PrivateKeyType> requiredAuthorities = requiredAuthoritiesBase;

        for (SignatureObject signatureObject : signatureObjects) {
            requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, signatureObject, privateKeyType);
        }

        return requiredAuthorities;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
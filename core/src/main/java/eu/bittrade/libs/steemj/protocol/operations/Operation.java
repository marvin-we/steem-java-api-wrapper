/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.protocol.operations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.interfaces.Validatable;
import eu.bittrade.libs.steemj.protocol.operations.virtual.AuthorRewardOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.CommentBenefactorRewardOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.CommentPayoutUpdateOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.CommentRewardOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.CurationRewardOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.FillConvertRequestOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.FillOrderOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.FillTransferFromSavingsOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.FillVestingWithdrawOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.HardforkOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.InterestOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.LiquidityRewardOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.ProducerRewardOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.ReturnVestingDelegationOperation;
import eu.bittrade.libs.steemj.protocol.operations.virtual.ShutdownWitnessOpeartion;

/**
 * This class is a wrapper for the different kinds of operations that an user
 * can perform.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = VoteOperation.class, name = "vote_operation"),
        @Type(value = CommentOperation.class, name = "comment_operation"),
        @Type(value = TransferOperation.class, name = "transfer_operation"),
        @Type(value = TransferToVestingOperation.class, name = "transfer_to_vesting_operation"),
        @Type(value = WithdrawVestingOperation.class, name = "withdraw_vesting_operation"),
        @Type(value = LimitOrderCreateOperation.class, name = "limit_order_create_operation"),
        @Type(value = LimitOrderCancelOperation.class, name = "limit_order_cancel_operation"),
        @Type(value = FeedPublishOperation.class, name = "feed_publish_operation"),
        @Type(value = ConvertOperation.class, name = "convert_operation"),
        @Type(value = AccountCreateOperation.class, name = "account_create_operation"),
        @Type(value = AccountUpdateOperation.class, name = "account_update_operation"),
        @Type(value = WitnessUpdateOperation.class, name = "witness_update_operation"),
        @Type(value = WitnessSetPropertiesOperation.class, name = "witness_set_properties_operation"),
        @Type(value = AccountWitnessVoteOperation.class, name = "account_witness_vote_operation"),
        @Type(value = AccountWitnessProxyOperation.class, name = "account_witness_proxy_operation"),
        @Type(value = PowOperation.class, name = "pow"),
        @Type(value = CustomOperation.class, name = "custom_operation"),
        @Type(value = ReportOverProductionOperation.class, name = "report_over_production_operation"),
        @Type(value = DeleteCommentOperation.class, name = "delete_comment_operation"),
        @Type(value = CustomJsonOperation.class, name = "custom_json_operation"),
        @Type(value = CommentOptionsOperation.class, name = "comment_options_operation"),
        @Type(value = SetWithdrawVestingRouteOperation.class, name = "set_withdraw_vesting_route_operation"),
        @Type(value = LimitOrderCreate2Operation.class, name = "limit_order_create2_operation"),
        @Type(value = ChallengeAuthorityOperation.class, name = "challenge_authority_operation"),
        @Type(value = ProveAuthorityOperation.class, name = "prove_authority_operation"),
        @Type(value = RequestAccountRecoveryOperation.class, name = "request_account_recovery_operation"),
        @Type(value = RecoverAccountOperation.class, name = "recover_account_operation"),
        @Type(value = ChangeRecoveryAccountOperation.class, name = "change_recovery_account_operation"),
        @Type(value = EscrowTransferOperation.class, name = "escrow_transfer_operation"),
        @Type(value = EscrowDisputeOperation.class, name = "escrow_dispute_operation"),
        @Type(value = EscrowReleaseOperation.class, name = "escrow_release_operation"),
        @Type(value = Pow2Operation.class, name = "pow2_operation"),
        @Type(value = EscrowApproveOperation.class, name = "escrow_approve_operation"),
        @Type(value = TransferToSavingsOperation.class, name = "transfer_to_savings_operation"),
        @Type(value = TransferFromSavingsOperation.class, name = "transfer_from_savings_operation"),
        @Type(value = CancelTransferFromSavingsOperation.class, name = "cancel_transfer_from_savings_operation"),
        @Type(value = CustomBinaryOperation.class, name = "custom_binary_operation"),
        @Type(value = DeclineVotingRightsOperation.class, name = "decline_voting_rights_operation"),
        @Type(value = ResetAccountOperation.class, name = "reset_account_operation"),
        @Type(value = SetResetAccountOperation.class, name = "set_reset_account_operation"),
        @Type(value = ClaimRewardBalanceOperation.class, name = "claim_reward_balance_operation"),
        @Type(value = DelegateVestingSharesOperation.class, name = "delegate_vesting_shares_operation"),
        @Type(value = AccountCreateWithDelegationOperation.class, name = "account_create_with_delegation_operation"),
        @Type(value = ClaimAccountOperation.class, name = "claim_account_operation"),
        @Type(value = CreateClaimedAccountOperation.class, name = "create_claimed_account_operation"),
        // Virtual Operations
        @Type(value = AuthorRewardOperation.class, name = "author_reward_operation"),
        @Type(value = CommentBenefactorRewardOperation.class, name = "comment_benefactor_reward_operation"),
        @Type(value = CommentPayoutUpdateOperation.class, name = "comment_payout_update_operation"),
        @Type(value = CommentRewardOperation.class, name = "comment_reward_operation"),
        @Type(value = CurationRewardOperation.class, name = "curation_reward_operation"),
        @Type(value = FillConvertRequestOperation.class, name = "fill_convert_request_operation"),
        @Type(value = FillOrderOperation.class, name = "fill_order_operation"),
        @Type(value = FillTransferFromSavingsOperation.class, name = "fill_transfer_from_savings_operation"),
        @Type(value = FillVestingWithdrawOperation.class, name = "fill_vesting_withdraw_operation"),
        @Type(value = HardforkOperation.class, name = "hardfork_operation"),
        @Type(value = InterestOperation.class, name = "interest_operation"),
        @Type(value = LiquidityRewardOperation.class, name = "liquidity_reward_operation"),
        @Type(value = ReturnVestingDelegationOperation.class, name = "return_vesting_delegation_operation"),
        @Type(value = ShutdownWitnessOpeartion.class, name = "shutdown_witness_operation"),
        @Type(value = ProducerRewardOperation.class, name = "producer_reward_operation") })
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

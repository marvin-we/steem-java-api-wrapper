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
package eu.bittrade.libs.steemj.plugins.apis.account.history.models.deserializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import eu.bittrade.libs.steemj.protocol.operations.AccountCreateOperation;
import eu.bittrade.libs.steemj.protocol.operations.AccountCreateWithDelegationOperation;
import eu.bittrade.libs.steemj.protocol.operations.AccountUpdateOperation;
import eu.bittrade.libs.steemj.protocol.operations.AccountWitnessProxyOperation;
import eu.bittrade.libs.steemj.protocol.operations.AccountWitnessVoteOperation;
import eu.bittrade.libs.steemj.protocol.operations.CancelTransferFromSavingsOperation;
import eu.bittrade.libs.steemj.protocol.operations.ChallengeAuthorityOperation;
import eu.bittrade.libs.steemj.protocol.operations.ChangeRecoveryAccountOperation;
import eu.bittrade.libs.steemj.protocol.operations.ClaimAccountOperation;
import eu.bittrade.libs.steemj.protocol.operations.ClaimRewardBalanceOperation;
import eu.bittrade.libs.steemj.protocol.operations.CommentOperation;
import eu.bittrade.libs.steemj.protocol.operations.CommentOptionsOperation;
import eu.bittrade.libs.steemj.protocol.operations.ConvertOperation;
import eu.bittrade.libs.steemj.protocol.operations.CreateClaimedAccountOperation;
import eu.bittrade.libs.steemj.protocol.operations.CustomBinaryOperation;
import eu.bittrade.libs.steemj.protocol.operations.CustomJsonOperation;
import eu.bittrade.libs.steemj.protocol.operations.CustomOperation;
import eu.bittrade.libs.steemj.protocol.operations.DeclineVotingRightsOperation;
import eu.bittrade.libs.steemj.protocol.operations.DelegateVestingSharesOperation;
import eu.bittrade.libs.steemj.protocol.operations.DeleteCommentOperation;
import eu.bittrade.libs.steemj.protocol.operations.EscrowApproveOperation;
import eu.bittrade.libs.steemj.protocol.operations.EscrowDisputeOperation;
import eu.bittrade.libs.steemj.protocol.operations.EscrowReleaseOperation;
import eu.bittrade.libs.steemj.protocol.operations.EscrowTransferOperation;
import eu.bittrade.libs.steemj.protocol.operations.FeedPublishOperation;
import eu.bittrade.libs.steemj.protocol.operations.LimitOrderCancelOperation;
import eu.bittrade.libs.steemj.protocol.operations.LimitOrderCreate2Operation;
import eu.bittrade.libs.steemj.protocol.operations.LimitOrderCreateOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;
import eu.bittrade.libs.steemj.protocol.operations.Pow2Operation;
import eu.bittrade.libs.steemj.protocol.operations.PowOperation;
import eu.bittrade.libs.steemj.protocol.operations.ProveAuthorityOperation;
import eu.bittrade.libs.steemj.protocol.operations.RecoverAccountOperation;
import eu.bittrade.libs.steemj.protocol.operations.ReportOverProductionOperation;
import eu.bittrade.libs.steemj.protocol.operations.RequestAccountRecoveryOperation;
import eu.bittrade.libs.steemj.protocol.operations.ResetAccountOperation;
import eu.bittrade.libs.steemj.protocol.operations.SetResetAccountOperation;
import eu.bittrade.libs.steemj.protocol.operations.SetWithdrawVestingRouteOperation;
import eu.bittrade.libs.steemj.protocol.operations.TransferFromSavingsOperation;
import eu.bittrade.libs.steemj.protocol.operations.TransferOperation;
import eu.bittrade.libs.steemj.protocol.operations.TransferToSavingsOperation;
import eu.bittrade.libs.steemj.protocol.operations.TransferToVestingOperation;
import eu.bittrade.libs.steemj.protocol.operations.VoteOperation;
import eu.bittrade.libs.steemj.protocol.operations.WithdrawVestingOperation;
import eu.bittrade.libs.steemj.protocol.operations.WitnessSetPropertiesOperation;
import eu.bittrade.libs.steemj.protocol.operations.WitnessUpdateOperation;
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
 * With Steem 0.22.0, the JSON structure for an applied operation changed. It
 * now returns an object with a 'type' and a 'value' field as shown below:
 * 
 * "op":{"type":"example_operation","value":{"fieldA": ...}}
 * 
 * As there does not seem to be an out of the box way to solve this properly,
 * this wrapper object has been created. It simply wraps the 'type' and 'value'
 * field of the response and uses the Jackson 'EXTERNAL_PROPERTY' configuration
 * for deserialization.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class OperationWrapper {
    @JsonProperty("type")
    private String originalSteemOperationName;
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
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
    @JsonProperty("value")
    private Operation operation;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private OperationWrapper() {
    }

    /**
     * @return Get the name of the operation provided by the Steem node.
     */
    public String getOriginalSteemOperationName() {
        return originalSteemOperationName;
    }

    /**
     * @return The operation itself.
     */
    public Operation getOperation() {
        return operation;
    }
}

package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import eu.bittrade.libs.steem.api.wrapper.interfaces.IByteArray;

/**
 * This class is a wrapper for the different kinds of operations that an user
 * can perform.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY)
@JsonSubTypes({ @Type(value = VoteOperation.class, name = "vote"),
        @Type(value = CommentOperation.class, name = "comment"),
        @Type(value = AuthorRewardOperation.class, name = "author_reward"),
        @Type(value = ConvertOperation.class, name = "convert"),
        @Type(value = InterestOperation.class, name = "interest"),
        @Type(value = CustomJsonOperation.class, name = "custom_json"),
        @Type(value = AccountWitnessVoteOperation.class, name = "account_witness_vote"),
        @Type(value = FillConvertRequestOperation.class, name = "fill_convert_request"),
        @Type(value = TransferToVestingOperation.class, name = "transfer_to_vesting"),
        @Type(value = CurationRewardOperation.class, name = "curation_reward"),
        @Type(value = TransferOperation.class, name = "transfer"),
        @Type(value = LimitOrderCreateOperation.class, name = "limit_order_create"),
        @Type(value = LimitOrderCancelOperation.class, name = "limit_order_cancel"),
        @Type(value = FillOrderOperation.class, name = "fill_order"),
        @Type(value = CommentOptionsOperation.class, name = "comment_options"),
        @Type(value = PowOperation.class, name = "pow"), @Type(value = Pow2Operation.class, name = "pow2"),
        @Type(value = FeedPublishOperation.class, name = "feed_publish"),
        @Type(value = DeleteCommentOperation.class, name = "delete_comment"),
        @Type(value = WithdrawVestingOperation.class, name = "withdraw_vesting"),
        @Type(value = RequestAccountRecoveryOperation.class, name = "request_account_recovery"),
        @Type(value = SetWithdrawVestingRouteOperation.class, name = "set_withdraw_vesting_route"),
        @Type(value = AccountWitnessProxyOperation.class, name = "account_witness_proxy"),
        @Type(value = AccountUpdateOperation.class, name = "account_update"),
        @Type(value = WitnessUpdateOperation.class, name = "witness_update"),
        @Type(value = AccountCreateOperation.class, name = "account_create") })
public abstract class Operation implements IByteArray {
    /**
     * Provide a SimpleDateFormat instance which is used when the object gets
     * converted into a byte array.
     */
    private SimpleDateFormat simpleDateFormat;

    public Operation() {
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        this.simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
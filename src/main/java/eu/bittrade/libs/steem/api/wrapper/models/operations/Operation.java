package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This class is a wrapper for the different kinds of operations that an user
 * can perform.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
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
        @Type(value = FillOrderOperation.class, name = "fill_order"),
        @Type(value = PowOperation.class, name = "pow"),
        @Type(value = AccountUpdateOperation.class, name = "account_update"),
        @Type(value = AccountCreateOperation.class, name = "account_create") })
public abstract class Operation {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
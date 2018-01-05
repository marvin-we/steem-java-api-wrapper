package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "get_replies_by_last_update_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetRepliesByLastUpdateArgs {
    @JsonProperty("start_parent_author")
    private AccountName startParentAuthor;
    @JsonProperty("start_permlink")
    private Permlink startPermlink;
    @JsonProperty("limit")
    private UInteger limit;

    /**
     * 
     * @param startParentAuthor
     * @param startPermlink
     * @param limit
     */
    @JsonCreator
    public GetRepliesByLastUpdateArgs(@JsonProperty("start_parent_author") AccountName startParentAuthor,
            @JsonProperty("start_permlink") Permlink startPermlink, @Nullable @JsonProperty("limit") UInteger limit) {
        this.setStartParentAuthor(startParentAuthor);
        this.setStartPermlink(startPermlink);
        this.setLimit(limit);
    }

    /**
     * @return the startParentAuthor
     */
    public AccountName getStartParentAuthor() {
        return startParentAuthor;
    }

    /**
     * @param startParentAuthor
     *            the startParentAuthor to set
     */
    public void setStartParentAuthor(AccountName startParentAuthor) {
        this.startParentAuthor = SteemJUtils.setIfNotNull(startParentAuthor,
                "The startParentAuthor needs to be provided.");
    }

    /**
     * @return the startPermlink
     */
    public Permlink getStartPermlink() {
        return startPermlink;
    }

    /**
     * @param startPermlink
     *            the startPermlink to set
     */
    public void setStartPermlink(Permlink startPermlink) {
        this.startPermlink = SteemJUtils.setIfNotNull(startPermlink, "The startPermlink needs to be provided.");
    }

    /**
     * @return the limit
     */
    public UInteger getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     */
    public void setLimit(UInteger limit) {
        this.limit = SteemJUtils.setIfNotNull(limit, UInteger.valueOf(100));
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

package eu.bittrade.libs.steemj.plugins.follow.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Comment;

/**
 * This class represents a Steem "comment_feed_entry" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentFeedEntry {
    private Comment comment;
    @JsonProperty("reblog_by")
    private List<AccountName> reblogBy;
    @JsonProperty("reblog_on")
    private Date reblogOn;
    // Original type is uint32_t.
    @JsonProperty("entry_id")
    private int entryId;

    /**
     * @return the comment
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * @return the reblogBy
     */
    public List<AccountName> getReblogBy() {
        return reblogBy;
    }

    /**
     * @return the reblogOn
     */
    public Date getReblogOn() {
        return reblogOn;
    }

    /**
     * @return the entryId
     */
    public int getEntryId() {
        return entryId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

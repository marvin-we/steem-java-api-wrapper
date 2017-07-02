package eu.bittrade.libs.steemj.plugins.follow.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Comment;
import eu.bittrade.libs.steemj.base.models.TimePointSec;

/**
 * This class represents a Steem "comment_blog_entry" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentBlogEntry {
    private Comment comment;
    private AccountName blog;
    @JsonProperty("reblog_on")
    private TimePointSec reblogOn;
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
     * @return the blog
     */
    public AccountName getBlog() {
        return blog;
    }

    /**
     * @return the reblogOn
     */
    public TimePointSec getReblogOn() {
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

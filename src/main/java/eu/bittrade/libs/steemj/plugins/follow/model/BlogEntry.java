package eu.bittrade.libs.steemj.plugins.follow.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * This class represents a Steem "blog_entry" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlogEntry {
    private AccountName author;
    private String permlink;
    private AccountName blog;
    @JsonProperty("reblog_on")
    private Date reblogOn;
    // Original type is uint32_t.
    @JsonProperty("entry_id")
    private int entryId;

    /**
     * @return the author
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * @return the permlink
     */
    public String getPermlink() {
        return permlink;
    }

    /**
     * Get the account name of the blog owner.
     * 
     * @return The account name of the blog owner.
     */
    public AccountName getBlog() {
        return blog;
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

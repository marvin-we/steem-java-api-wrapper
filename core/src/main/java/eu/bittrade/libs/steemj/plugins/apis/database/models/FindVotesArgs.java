package eu.bittrade.libs.steemj.plugins.apis.database.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "find_votes_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindVotesArgs {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;

    /**
     * @return the author
     */
    public AccountName getAuthor() {
        return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(AccountName author) {
        this.author = author;
    }

    /**
     * @return the permlink
     */
    public Permlink getPermlink() {
        return permlink;
    }

    /**
     * @param permlink
     *            the permlink to set
     */
    public void setPermlink(Permlink permlink) {
        this.permlink = permlink;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents a Steem "get_active_votes_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class GetActiveVotesArgs {
    @JsonProperty("author")
    private AccountName author;
    @JsonProperty("permlink")
    private Permlink permlink;

    /**
     * 
     * @param author
     * @param permlink
     */
    @JsonCreator
    public GetActiveVotesArgs(@JsonProperty("author") AccountName author, @JsonProperty("permlink") Permlink permlink) {
        this.setAuthor(author);
        this.setPermlink(permlink);
    }

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
        this.author = SteemJUtils.setIfNotNull(author, "The account needs to be provided.");
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
        this.permlink = SteemJUtils.setIfNotNull(permlink, "The permlink needs to be provided.");
        ;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

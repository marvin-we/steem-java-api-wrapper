package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import java.math.BigInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.Asset;

/**
 * This class represents a Steem "tag_api_obj" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Tag {
    /** The name of the tag. */
    @JsonProperty("name")
    private String name;
    @JsonProperty("total_payouts")
    /** The total payouts for posts and comments using this tag. */
    private Asset totalPayouts;
    @JsonProperty("net_votes")
    /** The number of votes made for posts and comments which used this tag. */
    private Integer netVotes;
    @JsonProperty("top_posts")
    /** The number of posts that made it to a "top post" and used this tag. */
    private UInteger topPosts;
    @JsonProperty("comments")
    private UInteger comments;
    // Original type is "fc::uint128" so we use BigInteger here.
    @JsonProperty("trending")
    private BigInteger trending;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private Tag() {
    }

    /**
     * @return The tag name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The sum of the paid amounts for posts using this tag.
     */
    public Asset getTotalPayouts() {
        return totalPayouts;
    }

    /**
     * @return The number of votes.
     */
    public int getNetVotes() {
        return netVotes;
    }

    /**
     * @return The number of top posts.
     */
    public UInteger getTopPosts() {
        return topPosts;
    }

    /**
     * @return The number of comments.
     */
    public UInteger getComments() {
        return comments;
    }

    /**
     * @return Trending.
     */
    public BigInteger getTrending() {
        return trending;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

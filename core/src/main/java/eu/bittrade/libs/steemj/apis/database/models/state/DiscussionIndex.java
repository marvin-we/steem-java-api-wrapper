package eu.bittrade.libs.steemj.apis.database.models.state;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "discussion_index" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class DiscussionIndex {
    /** The category by which everything is filtered. */
    @JsonProperty("category")
    private String category;
    /** The trending posts over the last 24 hours. */
    @JsonProperty("trending")
    private List<String> trending;
    // TODO: Is that JavaDoc correct?
    /** The pending posts by payout. */
    @JsonProperty("payout")
    private List<String> payout;
    // TODO: Is that JavaDoc correct?
    /** The pending comments by payout. */
    @JsonProperty("payout_comments")
    private List<String> payout_comments;
    // TODO: Is that JavaDoc correct?
    /** The pending lifetime payout. */
    @JsonProperty("trending30")
    private List<String> trending30;
    /** . */
    @JsonProperty("created")
    private List<String> created;
    /** . */
    @JsonProperty("responses")
    private List<String> responses;
    /** . */
    @JsonProperty("updated")
    private List<String> updated;
    /** . */
    @JsonProperty("active")
    private List<String> active;
    /** . */
    @JsonProperty("votes")
    private List<String> votes;
    /** . */
    @JsonProperty("cashout")
    private List<String> cashout;
    /** . */
    @JsonProperty("maturing")
    private List<String> maturing;
    /** . */
    @JsonProperty("best")
    private List<String> best;
    /** . */
    @JsonProperty("hot")
    private List<String> hot;
    /** . */
    @JsonProperty("promoted")
    private List<String> promoted;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected DiscussionIndex() {
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the trending
     */
    public List<String> getTrending() {
        return trending;
    }

    /**
     * @return the payout
     */
    public List<String> getPayout() {
        return payout;
    }

    /**
     * @return the payout_comments
     */
    public List<String> getPayout_comments() {
        return payout_comments;
    }

    /**
     * @return the trending30
     */
    public List<String> getTrending30() {
        return trending30;
    }

    /**
     * @return the created
     */
    public List<String> getCreated() {
        return created;
    }

    /**
     * @return the responses
     */
    public List<String> getResponses() {
        return responses;
    }

    /**
     * @return the updated
     */
    public List<String> getUpdated() {
        return updated;
    }

    /**
     * @return the active
     */
    public List<String> getActive() {
        return active;
    }

    /**
     * @return the votes
     */
    public List<String> getVotes() {
        return votes;
    }

    /**
     * @return the cashout
     */
    public List<String> getCashout() {
        return cashout;
    }

    /**
     * @return the maturing
     */
    public List<String> getMaturing() {
        return maturing;
    }

    /**
     * @return the best
     */
    public List<String> getBest() {
        return best;
    }

    /**
     * @return the hot
     */
    public List<String> getHot() {
        return hot;
    }

    /**
     * @return the promoted
     */
    public List<String> getPromoted() {
        return promoted;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

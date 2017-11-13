package eu.bittrade.libs.steemj.apis.database.models.state;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.DynamicGlobalProperty;
import eu.bittrade.libs.steemj.base.models.ExtendedAccount;
import eu.bittrade.libs.steemj.base.models.Price;
import eu.bittrade.libs.steemj.base.models.Tag;
import eu.bittrade.libs.steemj.base.models.Witness;
import eu.bittrade.libs.steemj.base.models.WitnessSchedule;

/**
 * This class represents a Steem "state" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class State {
    @JsonProperty("current_route")
    private String currentRoute;
    @JsonProperty("props")
    private DynamicGlobalProperty properties;
    @JsonProperty("tag_idx")
    private TagIndex tagIndex;
    /**
     * "" is the global discussion index
     */
    @JsonProperty("discussion_idx")
    Map<String, DiscussionIndex> discussionIndex;
    @JsonProperty("tags")
    Map<String, Tag> tags;

    /**
     * map from account/slug to full nested discussion
     */
    @JsonProperty("content")
    Map<String, Discussion> content;
    @JsonProperty("accounts")
    Map<String, ExtendedAccount> accounts;

    /**
     * The list of miners who are queued to produce work
     */
    @JsonProperty("pow_queue")
    private List<AccountName> powQueue;
    @JsonProperty("witnesses")
    Map<String, Witness> witnesses;
    @JsonProperty("witness_schedule")
    private WitnessSchedule witnessSchedule;
    @JsonProperty("feed_price")
    private Price feedPrice;
    @JsonProperty("error")
    private String error;
    // TODO: This field is optional <>
    @JsonProperty("market_data")
    private Market marketData;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected State() {
    }

    /**
     * @return the currentRoute
     */
    public String getCurrentRoute() {
        return currentRoute;
    }

    /**
     * @return the properties
     */
    public DynamicGlobalProperty getProperties() {
        return properties;
    }

    /**
     * @return the tagIndex
     */
    public TagIndex getTagIndex() {
        return tagIndex;
    }

    /**
     * @return the discussionIndex
     */
    public Map<String, DiscussionIndex> getDiscussionIndex() {
        return discussionIndex;
    }

    /**
     * @return the tags
     */
    public Map<String, Tag> getTags() {
        return tags;
    }

    /**
     * @return the content
     */
    public Map<String, Discussion> getContent() {
        return content;
    }

    /**
     * @return the accounts
     */
    public Map<String, ExtendedAccount> getAccounts() {
        return accounts;
    }

    /**
     * @return the powQueue
     */
    public List<AccountName> getPowQueue() {
        return powQueue;
    }

    /**
     * @return the witnesses
     */
    public Map<String, Witness> getWitnesses() {
        return witnesses;
    }

    /**
     * @return the witnessSchedule
     */
    public WitnessSchedule getWitnessSchedule() {
        return witnessSchedule;
    }

    /**
     * @return the feedPrice
     */
    public Price getFeedPrice() {
        return feedPrice;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @return the marketData
     */
    public Market getMarketData() {
        return marketData;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

package eu.bittrade.libs.steemj.plugins.follow.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.plugins.follow.enums.FollowType;

/**
 * This class represents a Steem "follow_api_object" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FollowApiObject {
    private AccountName follower;
    private AccountName following;
    private List<FollowType> what;

    /**
     * @return the follower
     */
    public AccountName getFollower() {
        return follower;
    }

    /**
     * @return the following
     */
    public AccountName getFollowing() {
        return following;
    }

    /**
     * @return the what
     */
    public List<FollowType> getWhat() {
        return what;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

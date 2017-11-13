package eu.bittrade.libs.steemj.apis.follow.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * This class is used to wrap the C++ "pair" type properly.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class PostsPerAuthorPair {
    private AccountName account;
    private int numberOfPosts;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected PostsPerAuthorPair() {
    }

    /**
     * Private constructor used by Jackson to serialize a PostsPerAuthorPair
     * object.
     * 
     * @param rawPostsPerAuthorPairObject
     *            An array of objects while the first field contains an account
     *            name and the second field contains the number of posts on the
     *            requested blog.
     */
    @JsonCreator
    private PostsPerAuthorPair(@JsonProperty Object[] rawPostsPerAuthorPairObject) {
        this.account = new AccountName(rawPostsPerAuthorPairObject[0].toString());
        this.numberOfPosts = Integer.valueOf(rawPostsPerAuthorPairObject[1].toString());
    }

    /**
     * Get the author which also has posts on the requested blog.
     * 
     * @return The author which also has posts on the requested blog.
     */
    public AccountName getAccount() {
        return account;
    }

    /**
     * Get the number of posts written by the {@link #getAccount() account} on
     * the requested blog.
     * 
     * @return the numberOfPosts
     */
    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

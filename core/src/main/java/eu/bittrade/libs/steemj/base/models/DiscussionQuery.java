package eu.bittrade.libs.steemj.base.models;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class represents the Steem "discussion_query" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonInclude(Include.NON_NULL)
public class DiscussionQuery implements HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    private String tag;
    private int limit;
    @JsonProperty("filter_tags")
    private List<String> filterTags;
    @JsonProperty("select_authors")
    private List<AccountName> selectAuthors;
    @JsonProperty("select_tags")
    private List<AccountName> selectTags;
    // Original type is uint32_t.
    @JsonProperty("truncate_body")
    private long truncateBody;
    @JsonProperty("start_author")
    private AccountName startAuthor;
    @JsonProperty("start_permlink")
    private Permlink startPermlink;
    @JsonProperty("parent_author")
    private AccountName parentAuthor;
    @JsonProperty("parent_permlink")
    private Permlink parentPermlink;

    /**
     * Create a new DiscussionQuery instance to filter for discussions.
     */
    public DiscussionQuery() {
        this.setLimit(0);
        this.setTruncateBody(0);
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag
     *            the tag to set
     */
    public void setTag(String tag) {
        if (this.getFilterTags() != null && this.getFilterTags().indexOf(tag) != this.getFilterTags().size()) {
            throw new InvalidParameterException(
                    "Expect " + this.getTag() + " to be the last element in the filter list.");
        }
        this.tag = tag;
    }

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit
     *            the limit to set
     * 
     * @throws InvalidParameterException
     *             If the given <code>limit</code> is higher than 100.
     */
    public void setLimit(int limit) {
        if (limit > 100) {
            throw new InvalidParameterException("The limit needs to be smaller than 100.");
        }

        this.limit = limit;
    }

    /**
     * @return the filterTags
     */
    public List<String> getFilterTags() {
        return filterTags;
    }

    /**
     * @param filterTags
     *            the filter_tags to set
     */
    public void setFilterTags(List<String> filterTags) {
        this.filterTags = filterTags;
    }

    /**
     * @return the selectAuthors
     */
    public List<AccountName> getSelectAuthors() {
        return selectAuthors;
    }

    /**
     * list of authors to include, posts not by this author are filtered
     * 
     * @param selectAuthors
     *            the selectAuthors to set
     */
    public void setSelectAuthors(List<AccountName> selectAuthors) {
        this.selectAuthors = selectAuthors;
    }

    /**
     * list of tags to include, posts without these tags are filtered
     * 
     * @return the selectTags
     */
    public List<AccountName> getSelectTags() {
        return selectTags;
    }

    /**
     * the number of bytes of the post body to return, 0 for all
     * 
     * @param selectTags
     *            the selectTags to set
     */
    public void setSelectTags(List<AccountName> selectTags) {
        this.selectTags = selectTags;
    }

    /**
     * @return the truncateBody
     */
    public long getTruncateBody() {
        return truncateBody;
    }

    /**
     * @param truncateBody
     *            the truncateBody to set
     */
    public void setTruncateBody(long truncateBody) {
        this.truncateBody = truncateBody;
    }

    /**
     * @return the startAuthor
     */
    public AccountName getStartAuthor() {
        return startAuthor;
    }

    /**
     * @param startAuthor
     *            the startAuthor to set
     */
    public void setStartAuthor(AccountName startAuthor) {
        this.startAuthor = startAuthor;
    }

    /**
     * @return the startPermlink
     */
    public Permlink getStartPermlink() {
        return startPermlink;
    }

    /**
     * @param startPermlink
     *            the startPermlink to set
     */
    public void setStartPermlink(Permlink startPermlink) {
        this.startPermlink = startPermlink;
    }

    /**
     * @return the parentAuthor
     */
    public AccountName getParentAuthor() {
        return parentAuthor;
    }

    /**
     * @param parentAuthor
     *            the parentAuthor to set
     */
    public void setParentAuthor(AccountName parentAuthor) {
        this.parentAuthor = parentAuthor;
    }

    /**
     * @return the parentPermlink
     */
    public Permlink getParentPermlink() {
        return parentPermlink;
    }

    /**
     * @param parentPermlink
     *            the parentPermlink to set
     */
    public void setParentPermlink(Permlink parentPermlink) {
        this.parentPermlink = parentPermlink;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

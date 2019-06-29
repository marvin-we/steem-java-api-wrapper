/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.plugins.apis.tags.models;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * This class represents a Steem "tag_name_type" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TagName {
    /** The maximum number of characters allowed for a tag. */
    private final int MAX_TAG_LENGTH = 32;
    private String tag;

    /**
     * 
     * @param tag
     */
    @JsonCreator()
    public TagName(String tag) {

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
     * @throws InvalidParameterException
     *             If the provided <code>tag</code> has more than 32 characters.
     */
    public void setTag(String tag) {
        if (tag.length() <= MAX_TAG_LENGTH) {
            throw new InvalidParameterException(
                    "The provided tag is too long - Only " + MAX_TAG_LENGTH + " characters are allowed.");
        }

        this.tag = tag;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

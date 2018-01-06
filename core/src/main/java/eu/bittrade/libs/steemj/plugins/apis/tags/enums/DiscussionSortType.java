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
package eu.bittrade.libs.steemj.plugins.apis.tags.enums;

/**
 * An enumeration representing all sort types for discussions.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum DiscussionSortType {
    /** Get trending discussions first. */
    GET_DISCUSSIONS_BY_TRENDING,
    /** Get discussions based on their creation date. */
    GET_DISCUSSIONS_BY_CREATED, GET_DISCUSSIONS_BY_ACTIVE, GET_DISCUSSIONS_BY_CASHOUT, GET_DISCUSSIONS_BY_VOTES, GET_DISCUSSIONS_BY_CHILDREN, GET_DISCUSSIONS_BY_HOT, GET_DISCUSSIONS_BY_FEED, GET_DISCUSSIONS_BY_BLOG, GET_DISCUSSIONS_BY_COMMENTS, GET_DISCUSSIONS_BY_PROMOTED;
}

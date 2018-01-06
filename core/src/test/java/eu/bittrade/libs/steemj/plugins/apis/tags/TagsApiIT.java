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
package eu.bittrade.libs.steemj.plugins.apis.tags;

public class TagsApiIT {
    DECLARE_API(
            (get_trending_tags)
            (get_tags_used_by_author)
            (get_discussion)
            (get_content_replies)
            (get_post_discussions_by_payout)
            (get_comment_discussions_by_payout)
            (get_discussions_by_trending)
            (get_discussions_by_created)
            (get_discussions_by_active)
            (get_discussions_by_cashout)
            (get_discussions_by_votes)
            (get_discussions_by_children)
            (get_discussions_by_hot)
            (get_discussions_by_feed)
            (get_discussions_by_blog)
            (get_discussions_by_comments)
            (get_discussions_by_promoted)
            (get_replies_by_last_update)
            (get_discussions_by_author_before_date)
            (get_active_votes)
         )
}

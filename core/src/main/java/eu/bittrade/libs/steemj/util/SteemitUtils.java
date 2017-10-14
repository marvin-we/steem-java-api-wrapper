package eu.bittrade.libs.steemj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains several utility methods required for
 * http://www.steemit.com functionalities.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemitUtils {
    /** Add a private constructor to hide the implicit public one. */
    private SteemitUtils() {
    }

    /**
     * Get a list of links that the given <code>content</code> contains.
     * 
     * @param content
     *            The content to extract the links from.
     * @return A list of links.
     */
    public static List<String> extractLinksFromContent(String content) {
        List<String> containedUrls = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "\\b((https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|])",
                Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(content);

        while (urlMatcher.find()) {
            containedUrls.add(content.substring(urlMatcher.start(0), urlMatcher.end(0)));
        }

        return containedUrls;
    }

    /**
     * Get a list of user names that the given <code>content</code> contains.
     * 
     * @param content
     *            The content to extract the user names from.
     * @return A list of user names.
     */
    public static List<String> extractUsersFromContent(String content) {
        List<String> containedUrls = new ArrayList<>();
        Pattern pattern = Pattern.compile("(@{1})([a-z0-9\\.-]{3,16})", Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(content);

        while (urlMatcher.find()) {
            containedUrls.add(content.substring(urlMatcher.start(2), urlMatcher.end(2)));
        }

        return containedUrls;
    }
}

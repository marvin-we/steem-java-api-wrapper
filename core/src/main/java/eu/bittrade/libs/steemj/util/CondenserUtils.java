package eu.bittrade.libs.steemj.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class contains several utility methods required for Condenser
 * functionalities.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CondenserUtils {
	private static final String TAGS_FIELD_NAME = "tags";
	private static final String USERS_FIELD_NAME = "users";
	private static final String IMAGES_FIELD_NAME = "images";
	private static final String LINKS_FIELD_NAME = "links";
	private static final String APP_FIELD_NAME = "app";
	private static final String FORMAT_FIELD_NAME = "format";

	/** Add a private constructor to hide the implicit public one. */
	private CondenserUtils() {
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

	/**
	 * Use this method to generate the json metadata for new comments or posts
	 * required by Condenser.
	 * 
	 * @param content
	 *            The body of the comment or post.
	 * @param tags
	 *            The used tags for this comment or post.
	 * @param app
	 *            The app name that publishes this comment or post.
	 * @param format
	 *            The format used by the comment or post.
	 * @param extraMetadata
	 *            <blockquote>A map of extra metadata to pack into the json metadata. Any values
	 *            here will <strong>overwrite</strong> any calculated values with
	 *            the same key!</blockquote>
	 * @return The json metadata in its String presentation.
	 */
	public static String generateSteemitMetadata(String content, String[] tags, String app, String format,
			Map<String, Object> extraMetadata) {
		Map<String, Object> metadata = new LinkedHashMap<>();

		if (tags != null) {
			metadata.put(TAGS_FIELD_NAME, tags);
		}

		List<String> users = extractUsersFromContent(content);
		if (users != null && !users.isEmpty()) {
			metadata.put(USERS_FIELD_NAME, users);
		}

		List<String> imageArray = new ArrayList<>();
		List<String> linksArray = new ArrayList<>();
		List<String> links = extractLinksFromContent(content);

		for (String link : links) {
			String lcLink = link.toLowerCase();
			if (lcLink.endsWith("jpg") || lcLink.endsWith("jpeg") || lcLink.endsWith("png") || lcLink.endsWith("gif")) {
				imageArray.add(link);
			} else {
				linksArray.add(link);
			}
		}

		if (imageArray.size() > 0) {
			metadata.put(IMAGES_FIELD_NAME, imageArray);
		}

		if (linksArray.size() > 0) {
			metadata.put(LINKS_FIELD_NAME, linksArray);
		}

		metadata.put(APP_FIELD_NAME, app);
		metadata.put(FORMAT_FIELD_NAME, format);

		if (extraMetadata != null) {
			for (String key : extraMetadata.keySet()) {
				key = key.trim();
				if (key.isEmpty()) {
					continue;
				}
				metadata.put(key, extraMetadata.get(key));
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(metadata);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Unable to create JSON metadata string", e);
		}
	}
}

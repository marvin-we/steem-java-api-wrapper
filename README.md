# steem-java-api-wrapper
A Java API-Wrapper for the Steem websocket API. 

This project allows you to easily access data stored in the Steem blockchain. The project has been initialized by <a href="https://steemit.com/@dez1337">dez1337 on steemit.com</a>.

# Contributors
- [inertia](https://steemit.com/@inertia) provided a bunch of unit tests to this project.
- An article from [Kyle](https://steemit.com/@klye) has been used to improve the documentation of the methods.
- The [guide](https://steemit.com/steem/@xeroc/steem-transaction-signing-in-a-nutshell) from [xeroc](https://steemit.com/@xeroc) shows how to create and sign transactions.

# How to build the project
The project requires Maven and Java to be installed on your machine. It can be build with the default maven command:
>mvn clean package

The resulting JAR can be found in the target directory as usual.

# Example
The following code is a small example on how to use this Wrapper.

```Java
package eu.bittrade.libs.steem.api.wrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemConnectionException;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemResponseError;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemTimeoutException;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemTransformationException;
import eu.bittrade.libs.steem.api.wrapper.models.ActiveVote;
import eu.bittrade.libs.steem.api.wrapper.models.Vote;

public class SteemAPIUsageExample {
	private static final Logger LOGGER = LogManager.getLogger(SteemAPIUsageExample.class);

	public static void main(String args[]) {
		// Create a config object that already provides the default settings.
		SteemApiWrapperConfig myConfig = new SteemApiWrapperConfig();

		// Change the default settings if needed.
		try {
			myConfig.setWebsocketEndpointURI(new URI("wss://steemit.com/wspa"));
		} catch (URISyntaxException e) {
			throw new RuntimeException("The given URI is not valid.", e);
		}

		try {
			// Create a new apiWrapper with your config object.
			SteemApiWrapper steemApiWrapper = new SteemApiWrapper(myConfig);

			// Get the votes done by the specified account:
			List<Vote> votes = steemApiWrapper.getAccountVotes("dez1337");
			LOGGER.info("The user dez1337 has done {} votes so far.", votes.size());
			LOGGER.info("His last vote has been done on {}.", votes.get(votes.size() - 1).getTime());
			int numberOfAccounts = steemApiWrapper.getAccountCount();
			int numberOfWitnesses = steemApiWrapper.getWitnessCount();
			LOGGER.info(
					"dez1337 is one of {} accounts on steemit and may increase the number witnesses to {} in the near future.",
					numberOfAccounts, numberOfWitnesses + 1);

			List<ActiveVote> activeVotesForArticle = steemApiWrapper.getActiveVotes("dez1337",
					"steem-api-wrapper-for-java-update1");
			LOGGER.info("The last vote for a article of dez1337 has been done from {}.",
					activeVotesForArticle.get(activeVotesForArticle.size() - 1).getVoter());
			LOGGER.info(
					"You may also want to vote for some posts to generate some Steem which is currently worth about {}.",
					steemApiWrapper.getCurrentMedianHistoryPrice().getBase());
			// Force an error response:
			steemApiWrapper.getAccountVotes("thisAcountDoesNotExistYet");
		} catch (SteemConnectionException | SteemTimeoutException | SteemTransformationException e) {
			LOGGER.error("Error!", e);
		} catch (SteemResponseError e) {
			// The SteemResponseError contains the error response.
			LOGGER.error("An error with code {} occured with the following message {}.", e.getError().getSteemErrorDetails().getData().getCode(), e.getError().getSteemErrorDetails().getMessage());
		}
	}
}
```

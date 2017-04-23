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

# How to incldude the steem-java-api-wrapper to your project
The steem-java-api-wrapper releases are pushed into the maven central repo. Due to that you can simply add a depedency to your project.

## Maven
```Xml
	<dependency>
            <groupId>eu.bittrade.libs</groupId>
            <artifactId>steem-api-wrapper</artifactId>
            <version>0.2.0</version>
	</dependency>
```

## Gradle
File: <i>build.gradle</i>
```Xml
allprojects {
    repositories {
        jcenter()
    }
}
```

```Xml
	dependencies {
    		compile 'eu.bittrade.libs:steem-api-wrapper:0.2.0'
	}
```

# Example
The following code is a small example showing how to use Version 0.2.0 of the API Wrapper.

```Java
package eu.bittrade.libs.steem.api.wrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.activity.InvalidActivityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steem.api.wrapper.configuration.SteemApiWrapperConfig;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemResponseError;
import eu.bittrade.libs.steem.api.wrapper.models.AccountActivity;
import eu.bittrade.libs.steem.api.wrapper.models.ActiveVote;
import eu.bittrade.libs.steem.api.wrapper.models.GlobalProperties;
import eu.bittrade.libs.steem.api.wrapper.models.Transaction;
import eu.bittrade.libs.steem.api.wrapper.models.Vote;
import eu.bittrade.libs.steem.api.wrapper.models.operations.AccountCreateOperation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.Operation;
import eu.bittrade.libs.steem.api.wrapper.models.operations.VoteOperation;

public class SteemAPIUsageExample {
    private static final Logger LOGGER = LogManager.getLogger(SteemAPIUsageExample.class);

    public static void main(String args[]) {
        // Change the default settings if needed.
        SteemApiWrapperConfig myConfig = SteemApiWrapperConfig.getInstance();

        myConfig.setTimeout(100000L);
        try {
            myConfig.setWebsocketEndpointURI(new URI("wss://this.piston.rocks"));
            myConfig.setSslVerificationDisabled(true);
        } catch (URISyntaxException e) {
            throw new RuntimeException("The given URI is not valid.", e);
        }
        myConfig.setPrivateKey(PrivateKeyType.POSTING, "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3");
        myConfig.setPrivateKey(PrivateKeyType.ACTIVE, "5KQasdf7ASD8weASdW37FSSsadfAImkwASd732QzDeyXtP79zk");

        try {
            // Create a new apiWrapper with your config object.
            SteemApiWrapper steemApiWrapper = new SteemApiWrapper();

            // Let's have a look at the account history of dez1337
            Map<Integer, AccountActivity> accountHistory = steemApiWrapper.getAccountHistory("dez1337", 100, 100);
            if (accountHistory.get(0).getOperations() instanceof AccountCreateOperation) {
                AccountCreateOperation accountCreateOperation = (AccountCreateOperation) (accountHistory.get(0)
                        .getOperations());
                LOGGER.info("The account {} has been created by {}.", "dez1337", accountCreateOperation.getCreator());
            }

            // Perform a transaction
            VoteOperation voteOperation = new VoteOperation();
            voteOperation.setAuthor("dez1337");
            voteOperation.setPermlink("steem-java-api-learned-to-speak-graphene-update-5");
            voteOperation.setVoter("dez1337");
            try {
            voteOperation.setWeight((short) 10000);
            } catch (InvalidActivityException e) {
                LOGGER.error("Weight was to high.", e);
            }

            Operation[] operations = { voteOperation };

            // Get the current RefBlockNum and RefBlockPrefix from the global properties.
            GlobalProperties globalProperties = steemApiWrapper.getDynamicGlobalProperties();
            int refBlockNum = (globalProperties.getHeadBlockNumber() & 0xFFFF);

            Transaction transaction = new Transaction();
            transaction.setRefBlockNum(refBlockNum);
            transaction.setRefBlockPrefix(globalProperties.getHeadBlockId());
            transaction.setOperations(operations);

            try {
                transaction.sign();
            } catch (SteemInvalidTransactionException e) {
                LOGGER.error("A propblem occured while signing your Transaction.", e);
            }
            steemApiWrapper.broadcastTransaction(transaction);
            
            LOGGER.info("The HEX representation of this transaction it {}.", steemApiWrapper.getTransactionHex(transaction));


            // Get the current Price
            LOGGER.info("The current price in the internal market is {}.", steemApiWrapper.getCurrentMedianHistoryPrice().getBase().getAmount());

            // Get votes
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
            steemApiWrapper.getPotentialSignatures();

            // Force an error response:
            steemApiWrapper.getAccountVotes("thisAcountDoesNotExistYet");
        } catch (SteemResponseError e) {
            // The SteemResponseError contains the error response.
            LOGGER.error("An error with code {} occured with the following message {}.",
                    e.getError().getSteemErrorDetails().getData().getCode(),
                    e.getError().getSteemErrorDetails().getMessage());
        } catch (SteemCommunicationException e) {
            LOGGER.error("Error!", e);
        }
    }
}
```

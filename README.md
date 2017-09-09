![SteemJ Logo](https://camo.githubusercontent.com/a325dd7ebceee15b8ca3fd57383c4e8330cc0425/687474703a2f2f696d6775722e636f6d2f784a4c514e31752e706e67)

This project allows you to easily access data stored in the Steem blockchain. The project has been initialized by <a href="https://steemit.com/@dez1337">dez1337 on steemit.com</a>.

# Full Documentation
- Please have a look at the [Wiki](https://github.com/marvin-we/steem-java-api-wrapper/wiki) for full documentation, examples, operational details and other information.
- Or have a look at the JavaDoc.

# Communication
- Please contact me on [Steemit.com](https://steemit.com/@dez1337)
- Or create an [Issue](https://github.com/marvin-we/steem-java-api-wrapper/issues) here on GitHub

# Contributors
- [inertia](https://steemit.com/@inertia) provided a bunch of unit tests to this project.
- An article from [Kyle](https://steemit.com/@klye) has been used to improve the documentation of the methods.
- The [guide](https://steemit.com/steem/@xeroc/steem-transaction-signing-in-a-nutshell) from [xeroc](https://steemit.com/@xeroc) shows how to create and sign transactions.

# Binaries
SteemJ binaries are pushed into the maven central repository and can be integrated with a bunch of build management tools like Maven.

## Maven
File: <i>pom.xml</i>
```Xml
	<dependency>
            <groupId>eu.bittrade.libs</groupId>
            <artifactId>steem-api-wrapper</artifactId>
            <version>0.3.2</version>
	</dependency>
```

Please have a look at the [Wiki](https://github.com/marvin-we/steem-java-api-wrapper/wiki/How-to-add-SteemJ-to-your-project) to find examples for Maven, Ivy, Gradle and others.

# How to build the project
The project requires Maven and Java to be installed on your machine. It can be build with the default maven command:

>mvn clean package

The resulting JAR can be found in the target directory as usual. Please notice that some integration tests require different private keys. Please provide them as -D parameter or use the properties file ( *src/test/resources/accountDetailsUsedDuringTests.properties* ) to define them. If you do not want to execute tests at all add *"-Dmaven.test.skip"* to the mvn call which skips the test execution during the build.

# Bugs and Feedback
For bugs or feature requests please create a [GitHub Issue](https://github.com/marvin-we/steem-java-api-wrapper/issues). For general discussions or questions you can also reply to one of the SteemJ update posts on [Steemit.com](https://steemit.com/@dez1337).

# Example
The following code is a small example showing how to use Version 0.3.2 of the API Wrapper.

<b>Attention!</b> The private keys provided in this sample a actually not correct and will result in an error if not changed.

```Java
package eu.bittrade.libs.steemj;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activity.InvalidActivityException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.GlobalProperties;
import eu.bittrade.libs.steemj.base.models.Transaction;
import eu.bittrade.libs.steemj.base.models.Vote;
import eu.bittrade.libs.steemj.base.models.VoteState;
import eu.bittrade.libs.steemj.base.models.operations.AccountCreateOperation;
import eu.bittrade.libs.steemj.base.models.operations.Operation;
import eu.bittrade.libs.steemj.base.models.operations.VoteOperation;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseError;

public class SteemJExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(SteemJExample.class);

   public static void main(String args[]) {
        // Change the default settings if needed.
        SteemJConfig myConfig = SteemJConfig.getInstance();

        myConfig.setTimeout(100000L);
        try {
            myConfig.setWebSocketEndpointURI(new URI("wss://this.piston.rocks"));
            myConfig.setSslVerificationDisabled(true);
        } catch (URISyntaxException e) {
            throw new RuntimeException("The given URI is not valid.", e);
        }

        List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();

        privateKeys.add(
                new ImmutablePair<>(PrivateKeyType.POSTING, "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3"));
        privateKeys
                .add(new ImmutablePair<>(PrivateKeyType.ACTIVE, "5KQasdf7ASD8weASdW37FSSsadfAImkwASd732QzDeyXtP79zk"));

        myConfig.getPrivateKeyStorage().addAccount(new AccountName("dez1337"), privateKeys);

        try {
            // Create a new apiWrapper with your config object.
            Steemj steemJ = new Steemj();

            // Let's have a look at the account history of dez1337
            Map<Integer, AppliedOperation> accountHistory = steemJ.getAccountHistory("dez1337", 100, 100);
            if (accountHistory.get(0).getOp() instanceof AccountCreateOperation) {
                AccountCreateOperation accountCreateOperation = (AccountCreateOperation) (accountHistory.get(0)
                        .getOp());
                LOGGER.info("The account {} has been created by {}.", "dez1337", accountCreateOperation.getCreator());
            }

            // Perform a transaction
            VoteOperation voteOperation = new VoteOperation();
            voteOperation.setAuthor(new AccountName("dez1337"));
            voteOperation.setPermlink("steem-java-api-learned-to-speak-graphene-update-5");
            voteOperation.setVoter(new AccountName("dez1337"));
            try {
                voteOperation.setWeight((short) 10000);
            } catch (InvalidActivityException e) {
                LOGGER.error("Weight was to high.", e);
            }

            ArrayList<Operation> operations = new ArrayList<>();
            operations.add(voteOperation);

            // Get the current RefBlockNum and RefBlockPrefix from the global
            // properties.
            GlobalProperties globalProperties = steemJ.getDynamicGlobalProperties();

            Transaction transaction = new Transaction();

            transaction.setRefBlockPrefix(globalProperties.getHeadBlockId().getHashValue());
            transaction.setRefBlockNum(globalProperties.getHeadBlockId().getNumberFromHash());
            transaction.setOperations(operations);

            try {
                transaction.sign();
            } catch (SteemInvalidTransactionException e) {
                LOGGER.error("A propblem occured while signing your Transaction.", e);
            }
            steemJ.broadcastTransaction(transaction);

            LOGGER.info("The HEX representation of this transaction it {}.",
                    steemJ.getTransactionHex(transaction));

            // Get the current Price
            LOGGER.info("The current price in the internal market is {}.",
                    steemJ.getCurrentMedianHistoryPrice().getBase().getAmount());

            // Get votes
            List<Vote> votes = steemJ.getAccountVotes("dez1337");
            LOGGER.info("The user dez1337 has done {} votes so far.", votes.size());
            LOGGER.info("His last vote has been done on {}.", votes.get(votes.size() - 1).getTime());
            int numberOfAccounts = steemJ.getAccountCount();
            int numberOfWitnesses = steemJ.getWitnessCount();
            LOGGER.info(
                    "dez1337 is one of {} accounts on steemit and may increase the number witnesses to {} in the near future.",
                    numberOfAccounts, numberOfWitnesses + 1);

            List<VoteState> activeVotesForArticle = steemJ.getActiveVotes("dez1337",
                    "steem-api-wrapper-for-java-update1");
            LOGGER.info("The last vote for a article of dez1337 has been done from {}.",
                    activeVotesForArticle.get(activeVotesForArticle.size() - 1).getVoter());
            LOGGER.info(
                    "You may also want to vote for some posts to generate some Steem which is currently worth about {}.",
                    steemJ.getCurrentMedianHistoryPrice().getBase());

            // Force an error response:
            steemJ.getAccountVotes("thisAcountDoesNotExistYet");
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

package my.sample.project;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.bittrade.libs.steemj.SteemJ;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.AppliedOperation;
import eu.bittrade.libs.steemj.base.models.GlobalProperties;
import eu.bittrade.libs.steemj.base.models.Permlink;
import eu.bittrade.libs.steemj.base.models.SignedTransaction;
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

/**
 * This class provides shows some common SteemJ commands.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemJUsageExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(SteemJUsageExample.class);

    /**
     * Called at startup.
     * 
     * @param args
     *            The arguments to set.
     */
    public static void main(String args[]) {
        // Change the default settings if needed.
        SteemJConfig myConfig = SteemJConfig.getInstance();
        myConfig.setTimeout(100000L);
        try {
            myConfig.setWebSocketEndpointURI(new URI("wss://seed.bitcoiner.me"), false);
        } catch (URISyntaxException e) {
            throw new RuntimeException("The given URI is not valid.", e);
        }

        try {
            // Create a new apiWrapper with your config object.
            SteemJ steemJ = new SteemJ();

            List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();
            privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING, "YOURPRIVATEPOSTINGKEY"));
            privateKeys.add(new ImmutablePair<>(PrivateKeyType.ACTIVE, "YOURPRIVATEACTIVEKEY"));

            myConfig.getPrivateKeyStorage().addAccount(new AccountName("dez1337"), privateKeys);

            // Let's have a look at the account history of dez1337
            Map<Integer, AppliedOperation> accountHistory = steemJ.getAccountHistory(new AccountName("dez1337"), 100,
                    100);
            if (accountHistory.get(0).getOp() instanceof AccountCreateOperation) {
                AccountCreateOperation accountCreateOperation = (AccountCreateOperation) (accountHistory.get(0)
                        .getOp());
                LOGGER.info("The account {} has been created by {}.", "dez1337", accountCreateOperation.getCreator());
            }

            // Perform a transaction
            AccountName voter = new AccountName("dez1337");
            AccountName author = new AccountName("dez1337");
            Permlink permlinkOfAPost = new Permlink("steem-java-api-learned-to-speak-graphene-update-5");
            Short votingWeight = 10000;
            VoteOperation voteOperation = new VoteOperation(voter, author, permlinkOfAPost, votingWeight);

            ArrayList<Operation> operations = new ArrayList<>();
            operations.add(voteOperation);

            // Get the current RefBlockNum and RefBlockPrefix from the global
            // properties.
            GlobalProperties globalProperties = steemJ.getDynamicGlobalProperties();

            SignedTransaction signedTransaction = new SignedTransaction(globalProperties.getHeadBlockId(), operations,
                    null);

            try {
                signedTransaction.sign();
            } catch (SteemInvalidTransactionException e) {
                LOGGER.error("A propblem occured while signing your Transaction.", e);
            }
            steemJ.broadcastTransaction(signedTransaction);

            LOGGER.info("The HEX representation of this transaction it {}.",
                    steemJ.getTransactionHex(signedTransaction));

            // Get the current Price
            LOGGER.info("The current price in the internal market is {}.",
                    steemJ.getCurrentMedianHistoryPrice().getBase().getAmount());

            // Get votes
            List<Vote> votes = steemJ.getAccountVotes(new AccountName("dez1337"));
            LOGGER.info("The user dez1337 has done {} votes so far.", votes.size());
            LOGGER.info("His last vote has been done on {}.", votes.get(votes.size() - 1).getTime());
            int numberOfAccounts = steemJ.getAccountCount();
            int numberOfWitnesses = steemJ.getWitnessCount();
            LOGGER.info(
                    "dez1337 is one of {} accounts on steemit and may increase the number witnesses to {} in the near future.",
                    numberOfAccounts, numberOfWitnesses + 1);

            List<VoteState> activeVotesForArticle = steemJ.getActiveVotes(new AccountName("dez1337"),
                    new Permlink("steem-api-wrapper-for-java-update1"));
            LOGGER.info("The last vote for a article of dez1337 has been done from {}.",
                    activeVotesForArticle.get(activeVotesForArticle.size() - 1).getVoter());
            LOGGER.info(
                    "You may also want to vote for some posts to generate some Steem which is currently worth about {}.",
                    steemJ.getCurrentMedianHistoryPrice().getBase());

            // Force an error response:
            steemJ.getAccountVotes(new AccountName("thisAcountDoesNotExistYet"));
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

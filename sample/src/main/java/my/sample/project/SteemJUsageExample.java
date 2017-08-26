package my.sample.project;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.activity.InvalidActivityException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.bittrade.libs.steemj.SteemJ;
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

public class SteemJUsageExample {
    private static final Logger LOGGER = LogManager.getLogger(SteemJUsageExample.class);

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
            SteemJ steemJ = new SteemJ();

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

            //transaction.setRefBlockPrefix(globalProperties.getHeadBlockId().getHashValue());
            //transaction.setRefBlockNum(globalProperties.getHeadBlockId().getNumberFromHash());
            transaction.setOperations(operations);

            try {
                transaction.sign();
            } catch (SteemInvalidTransactionException e) {
                LOGGER.error("A propblem occured while signing your Transaction.", e);
            }
            steemJ.broadcastTransaction(transaction);

            LOGGER.info("The HEX representation of this transaction it {}.", steemJ.getTransactionHex(transaction));

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
            steemJ.getPotentialSignatures();

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

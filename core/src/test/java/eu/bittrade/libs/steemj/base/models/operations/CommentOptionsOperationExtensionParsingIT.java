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
package eu.bittrade.libs.steemj.base.models.operations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseITForOperationParsing;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;
import eu.bittrade.libs.steemj.protocol.operations.CommentOptionsOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * This class tests if the {@link CommentOptionsOperation} can be parsed
 * successfully even when an extension has been added.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsOperationExtensionParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION_WITH_EXTENSION = 12615224;
    private static final int TRANSACTION_INDEX_WITH_EXTENSION = 9;
    private static final int OPERATION_INDEX_WITH_EXTENSION = 1;
    private static final String EXPECTED_AUTHOR_WITH_EXTENSION = "malay11";
    private static final String EXPECTED_PERMANENT_LINK_WITH_EXTENSION = "re-bart2305-201767t204737942z";
    private static final boolean EXPECTED_VOTES_ALLOWED_WITH_EXTENSION = true;
    private static final int BENEFICIARIES_ID = 0;
    private static final String EXPECTED_BENEFICIARY_ACCOUNT = "esteemapp";
    private static final short EXPECTED_BENEFICIARY_WEIGHT = 500;

    /**
     * Prepare all required fields used by this test class.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupIntegrationTestEnvironment();
    }

    @Override
    @Test
    @Category({ IntegrationTest.class })
    public void testOperationParsing() throws SteemCommunicationException, SteemResponseException {
        ExtendedSignedBlock blockContainingCommentOptionsOperation = steemJ
                .getBlock(BLOCK_NUMBER_CONTAINING_OPERATION_WITH_EXTENSION).get();

        Operation commentOptionsOperation = blockContainingCommentOptionsOperation.getTransactions()
                .get(TRANSACTION_INDEX_WITH_EXTENSION).getOperations().get(OPERATION_INDEX_WITH_EXTENSION);

        assertThat(commentOptionsOperation, instanceOf(CommentOptionsOperation.class));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAuthor().getName(),
                equalTo(EXPECTED_AUTHOR_WITH_EXTENSION));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getAllowVotes(),
                equalTo(EXPECTED_VOTES_ALLOWED_WITH_EXTENSION));
        assertThat(((CommentOptionsOperation) commentOptionsOperation).getPermlink().getLink(),
                equalTo(EXPECTED_PERMANENT_LINK_WITH_EXTENSION));

        assertThat(((CommentOptionsOperation) commentOptionsOperation).getExtensions().get(0),
                instanceOf(CommentPayoutBeneficiaries.class));
        assertThat(((CommentPayoutBeneficiaries) ((CommentOptionsOperation) commentOptionsOperation).getExtensions()
                .get(0)).getBeneficiaries().size(), equalTo(1));
        assertThat(
                ((CommentPayoutBeneficiaries) ((CommentOptionsOperation) commentOptionsOperation).getExtensions()
                        .get(0)).getBeneficiaries().get(BENEFICIARIES_ID).getAccount().getName(),
                equalTo(EXPECTED_BENEFICIARY_ACCOUNT));
        assertThat(
                ((CommentPayoutBeneficiaries) ((CommentOptionsOperation) commentOptionsOperation).getExtensions()
                        .get(0)).getBeneficiaries().get(BENEFICIARIES_ID).getWeight(),
                equalTo(EXPECTED_BENEFICIARY_WEIGHT));
    }
}

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
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.block.models.ExtendedSignedBlock;
import eu.bittrade.libs.steemj.protocol.PublicKey;
import eu.bittrade.libs.steemj.protocol.operations.AccountCreateOperation;
import eu.bittrade.libs.steemj.protocol.operations.Operation;

/**
 * This class tests if the {@link AccountCreateOperation} can be parsed
 * successfully.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperationParsingIT extends BaseITForOperationParsing {
    private static final long BLOCK_NUMBER_CONTAINING_OPERATION = 5726699;
    private static final int TRANSACTION_INDEX = 6;
    private static final int OPERATION_INDEX = 0;
    private static final String EXPECTED_CREATOR = "steem";
    private static final PublicKey EXPECTED_OWNER_KEY = new PublicKey(
            SteemJConfig.getInstance().getAddressPrefix().name().toUpperCase()
                    + "7LkKo3FU1w7m6ce4W5rfhdNUKd7CprHrZXnmNeU3SX2ggSikdw");

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
        ExtendedSignedBlock blockContainingAccountCreateOperation = steemJ.getBlock(BLOCK_NUMBER_CONTAINING_OPERATION)
                .get();

        Operation accountCreateOperation = blockContainingAccountCreateOperation.getTransactions()
                .get(TRANSACTION_INDEX).getOperations().get(OPERATION_INDEX);

        assertThat(accountCreateOperation, instanceOf(AccountCreateOperation.class));
        assertThat(((AccountCreateOperation) accountCreateOperation).getCreator().getName(), equalTo(EXPECTED_CREATOR));
        assertThat(((AccountCreateOperation) accountCreateOperation).getOwner().getKeyAuths()
                .containsKey(EXPECTED_OWNER_KEY), equalTo(true));
    }

}

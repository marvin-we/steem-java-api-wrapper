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
package eu.bittrade.libs.steemj;

import java.io.UnsupportedEncodingException;

import eu.bittrade.libs.steemj.chain.SignedTransaction;
import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This class defines which tests should at least be performed for an operation
 * and prepares a transaction object so that it does not need to be created in
 * each sub test case.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseTransactionalUT extends BaseUT {
    protected static SignedTransaction signedTransaction;

    /**
     * Setup the test environment for transaction related unit tests.
     */
    protected static void setupUnitTestEnvironmentForTransactionalTests() {
        setupUnitTestEnvironment();

        SteemJConfig.getInstance().setValidationLevel(ValidationType.SKIP_VALIDATION);
    }

    /**
     * Test if the toByteArray method returns the expected byte array.
     * 
     * @throws UnsupportedEncodingException
     *             If something went wrong.
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    public abstract void testOperationToByteArray()
            throws UnsupportedEncodingException, SteemInvalidTransactionException;

    /**
     * Test if the SHA256 Hex of a transaction including the operation is the
     * expected hash.
     * 
     * @throws UnsupportedEncodingException
     *             If something went wrong.
     * @throws SteemInvalidTransactionException
     *             If something went wrong.
     */
    public abstract void testTransactionWithOperationToHex()
            throws UnsupportedEncodingException, SteemInvalidTransactionException;
}

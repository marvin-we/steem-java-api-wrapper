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
package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.security.InvalidParameterException;

import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;
import eu.bittrade.libs.steemj.protocol.enums.AssetSymbolType;

/**
 * Test the ChainProperties object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class ChainPropertiesTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "881300000000000003535445454d0000000001000000";

    /**
     * Test if the toByte method returns the expected byte representation.
     * 
     * @throws Exception
     */
    @Test
    public void testSteemChainPropertiesToByteArray() throws Exception {
        Asset accountCreationFee = new Asset(5000, AssetSymbolType.STEEM);

        long maximumBlockSize = 65536;
        int sbdInterestRate = 0;

        ChainProperties chainProperties = new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);

        assertThat("Expect that the asset object has the given byte representation.",
                CryptoUtils.HEX.encode(chainProperties.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));

    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.ChainProperties} object.
     */
    @Test(expected = InvalidParameterException.class)
    public void testChainPropertiesValidationInterestRate() {
        Asset accountCreationFee = new Asset(5000, AssetSymbolType.STEEM);

        long maximumBlockSize = 65570;
        int sbdInterestRate = -1;

        new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.ChainProperties} object.
     */
    @Test(expected = InvalidParameterException.class)
    public void testChainPropertiesValidationBlockSize() {
        Asset accountCreationFee = new Asset(5000, AssetSymbolType.STEEM);

        long maximumBlockSize = 5215;
        int sbdInterestRate = 0;

        new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.ChainProperties} object.
     */
    @Test(expected = InvalidParameterException.class)
    public void testChainPropertiesValidationAccountCreationFee() {
        Asset accountCreationFee = new Asset(5000, AssetSymbolType.STEEM);

        long maximumBlockSize = 65535;
        int sbdInterestRate = 5;

        new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);
    }

    /**
     * Test the validation of the
     * {@link eu.bittrade.libs.steemj.base.models.ChainProperties} object with
     * valid values.
     */
    @Test
    public void testChainPropertiesValidation() {
        new AccountName("dez-1337");
        new AccountName("dez");
        new AccountName("dez1337-steemj");

        Asset accountCreationFee = new Asset(3, AssetSymbolType.STEEM);

        long maximumBlockSize = 65536;
        int sbdInterestRate = 5;

        new ChainProperties(accountCreationFee, maximumBlockSize, sbdInterestRate);
    }
}

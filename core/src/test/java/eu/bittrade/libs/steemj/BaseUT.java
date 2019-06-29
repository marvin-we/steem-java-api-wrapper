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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import eu.bittrade.libs.steemj.configuration.SteemJConfig;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class BaseUT extends BaseTest {
    protected static SteemJConfig config;

    protected static final String PRIVATE_POSTING_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final String PRIVATE_ACTIVE_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";
    protected static final String PRIVATE_OWNER_KEY = "5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3";

    /**
     * Prepare a the environment for standard unit tests.
     */
    protected static void setupUnitTestEnvironment() {
        config = SteemJConfig.getNewInstance();

        List<ImmutablePair<PrivateKeyType, String>> privateKeys = new ArrayList<>();

        privateKeys.add(new ImmutablePair<>(PrivateKeyType.POSTING, PRIVATE_POSTING_KEY));
        privateKeys.add(new ImmutablePair<>(PrivateKeyType.ACTIVE, PRIVATE_ACTIVE_KEY));
        privateKeys.add(new ImmutablePair<>(PrivateKeyType.OWNER, PRIVATE_OWNER_KEY));

        config.getPrivateKeyStorage().addAccount(new AccountName("dez1337"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("foobara"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("foobarc"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("foo"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("steemj"), privateKeys);
        config.getPrivateKeyStorage().addAccount(new AccountName("xeroc"), privateKeys);
    }
}

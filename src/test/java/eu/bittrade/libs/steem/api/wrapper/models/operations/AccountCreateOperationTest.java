package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steem.api.wrapper.BaseUnitTest;

/**
 * This is a copy of the Steem Piston test. Have a look at <a href=
 * "https://github.com/steemit/steem-python/blob/master/tests/steem/test_transactions.py">GitHub</a>
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperationTest extends BaseUnitTest {

    @BeforeClass()
    public static void prepareTestClass() throws Exception {
        setupUnitTestEnvironment();
    }

    @Test
    public void testAccountCreateOperationToByteArray() {
        /*
         * AccountCreateOperation accountCreateOperation = new
         * AccountCreateOperation(); accountCreateOperation.setFee(); op =
         * operations.AccountCreate( {'creator': 'xeroc', 'fee': '10.000 STEEM',
         * 'json_metadata': '', 'memo_key':
         * 'STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp',
         * 'new_account_name': 'fsafaasf', 'owner': {'account_auths': [],
         * 'key_auths':
         * [['STM5jYVokmZHdEpwo5oCG3ES2Ca4VYzy6tM8pWWkGdgVnwo2mFLFq', 1], [
         * 'STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp', 1]],
         * 'weight_threshold': 1}, 'active': {'account_auths': [], 'key_auths':
         * [['STM6pbVDAjRFiw6fkiKYCrkz7PFeL7XNAfefrsREwg8MKpJ9VYV9x', 1], [
         * 'STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp', 1]],
         * 'weight_threshold': 1}, 'posting': {'account_auths': [], 'key_auths':
         * [['STM8CemMDjdUWSV5wKotEimhK6c4dY7p2PdzC2qM1HpAP8aLtZfE7', 1], [
         * 'STM6zLNtyFVToBsBZDsgMhgjpwysYVbsQD6YhP3kRkQhANUB4w7Qp', 1], [
         * 'STM6pbVDAjRFiw6fkiKYCrkz7PFeL7XNAfefrsREwg8MKpJ9VYV9x', 1 ]],
         * 'weight_threshold': 1}} )
         */
    }
}

package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.bitcoinj.core.Utils;
import org.junit.Test;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AuthorityTest {
    private final String EXPECTED_BYTE_REPRESENTATION = "060000000001032c5f5ef5d71e5196b0831c6683db997b6c95ce5e13d52"
            + "ce5bf78d5dd3aa731550100";

    @Test
    public void testSteemChainPropertiesToByteArray() throws Exception {
        Authority exampleAuthority = new Authority();
        exampleAuthority.setAccountAuths(new HashMap<>());
        Map<PublicKey, Integer> postingKeyAuth = new HashMap<>();
        postingKeyAuth.put(new PublicKey("STM7Amy3akYfmSY92YrxYxfEGfc1pe3ctJtWjRi1wfo66K2e9veCN"), 1);
        exampleAuthority.setKeyAuths(postingKeyAuth);
        exampleAuthority.setWeightThreshold(6);

        assertThat("Expect that the asset object has the given byte representation.",
                Utils.HEX.encode(exampleAuthority.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));

    }
}

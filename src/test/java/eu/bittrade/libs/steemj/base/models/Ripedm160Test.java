package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

/**
 * Test the Ripedm160Test object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class Ripedm160Test {
    private final int EXPECTED_NUMBER = 14534105;

    @Test
    public void testVestsAssetToByteArray() throws Exception {
        Ripemd160 hash = new Ripemd160("00ddc5d91d1bd0050853ce67c665c390cb232bbf") {
        };

        assertThat(hash.getNumberFromHash(), equalTo(EXPECTED_NUMBER));
    }
}

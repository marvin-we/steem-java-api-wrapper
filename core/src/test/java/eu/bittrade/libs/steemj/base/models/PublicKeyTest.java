package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.bitcoinj.core.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.libs.steemj.BaseUnitTest;

/**
 * Test the PublicKey object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class PublicKeyTest extends BaseUnitTest {
    private static final String ADDRESS = "BTS6UtYWWs3rkZGV8JA86qrgkG6tyFksgECefKE1MiH4HkLD8PFGL";
    private static final String ANOTHER_ADDRESS = "BTS8YAMLtNcnqGNd3fx28NP3WoyuqNtzxXpwXTkZjbfe9scBmSyGT";
    private static final String EXPECTED_BYTE_REPRESENTATION = "02d1ce1cefe421600c23427e2d8d97f3dacf77b2395d79e0ea6be38a1a34dad714";

    private static PublicKey publicKey;

    @BeforeClass
    public static void prepareTestClass() throws Exception {
        publicKey = new PublicKey(ADDRESS);
    }

    @Test
    public void testPublicKeyFromAddress() throws Exception {
        assertThat(Utils.HEX.encode(publicKey.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    @Test
    public void testAddressFromPublicKey() throws Exception {
        assertThat(ADDRESS, equalTo(publicKey.getAddressFromPublicKey()));
    }

    @Test
    public void testPublicKeyEqualsMethod() {
        PublicKey publicKey = new PublicKey(ADDRESS);
        PublicKey samePublicKey = new PublicKey(ADDRESS);
        PublicKey differentPublicKey = new PublicKey(ANOTHER_ADDRESS);

        assertThat(publicKey.equals(samePublicKey), equalTo(true));
        assertThat(samePublicKey.equals(differentPublicKey), equalTo(false));
    }
}

package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.BeforeClass;
import org.junit.Test;

import eu.bittrade.crypto.core.CryptoUtils;
import eu.bittrade.libs.steemj.BaseUT;

/**
 * Test the PublicKey object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 *
 */
public class PublicKeyTest extends BaseUT {
    private static final String ADDRESS = "BTS6UtYWWs3rkZGV8JA86qrgkG6tyFksgECefKE1MiH4HkLD8PFGL";
    private static final String ANOTHER_ADDRESS = "BTS8YAMLtNcnqGNd3fx28NP3WoyuqNtzxXpwXTkZjbfe9scBmSyGT";
    private static final String EXPECTED_BYTE_REPRESENTATION = "02d1ce1cefe421600c23427e2d8d97f3dacf77b2395d79e0ea6be38a1a34dad714";

    private static PublicKey publicKey;

    /**
     * Prepare the environment for this specific test.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @BeforeClass
    public static void prepareTestClass() throws Exception {
        publicKey = new PublicKey(ADDRESS);
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.base.models.PublicKey#toByteArray()}
     * method by creating a new PublicKey from an Address.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testPublicKeyFromAddress() throws Exception {
        assertThat(CryptoUtils.HEX.encode(publicKey.toByteArray()), equalTo(EXPECTED_BYTE_REPRESENTATION));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.base.models.PublicKey#getAddressFromPublicKey}
     * method.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testAddressFromPublicKey() throws Exception {
        assertThat(ADDRESS, equalTo(publicKey.getAddressFromPublicKey()));
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.base.models.PublicKey#equals(Object)}
     * method.
     */
    @Test
    public void testPublicKeyEqualsMethod() {
        PublicKey publicKey = new PublicKey(ADDRESS);
        PublicKey samePublicKey = new PublicKey(ADDRESS);
        PublicKey differentPublicKey = new PublicKey(ANOTHER_ADDRESS);

        assertThat(publicKey.equals(samePublicKey), equalTo(true));
        assertThat(samePublicKey.equals(differentPublicKey), equalTo(false));
    }
}

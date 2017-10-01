package eu.bittrade.libs.steemj.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;

import org.junit.Test;

/**
 * Test the key generation. The test are a Java implementation of the
 * steem-python project (see <a href=
 * "https://github.com/steemit/steem-python/blob/master/tests/steembase/test_base_account.py">GitHub</a>).
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class KeyGeneratorTest {
    /**
     * Generate different key sets based on different brain keys and verify the
     * results.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testKeyGeneration() throws Exception {
        KeyGenerator keyGeneratorOne = new KeyGenerator(
                "COLORER BICORN KASBEKE FAERIE LOCHIA GOMUTI SOVKHOZ Y GERMAL AUNTIE PERFUMY TIME FEATURE GANGAN CELEMIN MATZO",
                0);
        KeyGenerator keyGeneratorTwo = new KeyGenerator(
                "NAK TILTING MOOTING TAVERT SCREENY MAGIC BARDIE UPBORNE CONOID MAUVE CARBON NOTAEUM BITUMEN HOOEY KURUMA COWFISH",
                0);
        KeyGenerator keyGeneratorThree = new KeyGenerator(
                "CORKITE CORDAGE FONDISH UNDER FORGET BEFLEA OUTBUD ZOOGAMY BERLINE ACANTHA STYLO YINCE TROPISM TUNKET FALCULA TOMENT",
                0);
        KeyGenerator keyGeneratorFour = new KeyGenerator(
                "MURZA PREDRAW FIT LARIGOT CRYOGEN SEVENTH LISP UNTAWED AMBER CRETIN KOVIL TEATED OUTGRIN POTTAGY KLAFTER DABB",
                0);
        KeyGenerator keyGeneratorFive = new KeyGenerator(
                "VERDICT REPOUR SUNRAY WAMBLY UNFILM UNCOUS COWMAN REBUOY MIURUS KEACORN BENZOLE BEMAUL SAXTIE DOLENT CHABUK BOUGHED",
                0);
        KeyGenerator keyGeneratorSix = new KeyGenerator(
                "HOUGH TRUMPH SUCKEN EXODY MAMMATE PIGGIN CRIME TEPEE URETHAN TOLUATE BLINDLY CACOEPY SPINOSE COMMIE GRIECE FUNDAL",
                0);
        KeyGenerator keyGeneratorSeven = new KeyGenerator(
                "OERSTED ETHERIN TESTIS PEGGLE ONCOST POMME SUBAH FLOODER OLIGIST ACCUSE UNPLAT OATLIKE DEWTRY CYCLIZE PIMLICO CHICOT",
                0);

        assertThat(keyGeneratorOne.getPrivateKeyAsWIF(),
                equalTo("5JfwDztjHYDDdKnCpjY6cwUQfM4hbtYmSJLjGd9KTpk9J4H2jDZ"));
        assertThat(keyGeneratorTwo.getPrivateKeyAsWIF(),
                equalTo("5JcdQEQjBS92rKqwzQnpBndqieKAMQSiXLhU7SFZoCja5c1JyKM"));
        assertThat(keyGeneratorThree.getPrivateKeyAsWIF(),
                equalTo("5JsmdqfNXegnM1eA8HyL6uimHp6pS9ba4kwoiWjjvqFC1fY5AeV"));
        assertThat(keyGeneratorFour.getPrivateKeyAsWIF(),
                equalTo("5J2KeFptc73WTZPoT1Sd59prFep6SobGobCYm7T5ZnBKtuW9RL9"));
        assertThat(keyGeneratorFive.getPrivateKeyAsWIF(),
                equalTo("5HryThsy6ySbkaiGK12r8kQ21vNdH81T5iifFEZNTe59wfPFvU9"));
        assertThat(keyGeneratorSix.getPrivateKeyAsWIF(),
                equalTo("5Ji4N7LSSv3MAVkM3Gw2kq8GT5uxZYNaZ3d3y2C4Ex1m7vshjBN"));
        assertThat(keyGeneratorSeven.getPrivateKeyAsWIF(),
                equalTo("5HqSHfckRKmZLqqWW7p2iU18BYvyjxQs2sksRWhXMWXsNEtxPZU"));
    }

    /**
     * Generate a sequence of key sets based on the same brain key and verify
     * the results.
     * 
     * @throws Exception
     *             If something went wrong.
     */
    @Test
    public void testKeyGenerationSequence() throws Exception {
        final ArrayList<String> EXPECTED_KEYS = new ArrayList<>();

        EXPECTED_KEYS.add("5Hsbn6kXio4bb7eW5bX7kTp2sdkmbzP8kGWoau46Cf7en7T1RRE");
        EXPECTED_KEYS.add("5K9MHEyiSye5iFL2srZu3ZVjzAZjcQxUgUvuttcVrymovFbU4cc");
        EXPECTED_KEYS.add("5JBXhzDWQdYPAzRxxuGtzqM7ULLKPK7GZmktHTyF9foGGfbtDLT");
        EXPECTED_KEYS.add("5Kbbfbs6DmJFNddWiP1XZfDKwhm5dkn9KX5AENQfQke2RYBBDcz");
        EXPECTED_KEYS.add("5JUqLwgxn8f7myNz4gDwo5e77HZgopHMDHv4icNVww9Rxu1GDG5");
        EXPECTED_KEYS.add("5JNBVj5QVh86N8MUUwY3EVUmsZwChZftxnuJx22DzEtHWC4rmvK");
        EXPECTED_KEYS.add("5JdvczYtxPPjQdXMki1tpNvuSbvPMxJG5y4ndEAuQsC5RYMQXuC");
        EXPECTED_KEYS.add("5HsUSesU2YB4EA3dmpGtHh8aPAwEdkdhidG8hcU2Nd2tETKk85t");
        EXPECTED_KEYS.add("5JpveiQd1mt91APyQwvsCdAXWJ7uag3JmhtSxpGienic8vv1k2W");
        EXPECTED_KEYS.add("5KDGhQUqQmwcGQ9tegimSyyT4vmH8h2fMzoNe1MT9bEGvRvR6kD");

        for (int i = 10; i < EXPECTED_KEYS.size(); i++) {
            KeyGenerator keyGenerator = new KeyGenerator(
                    "COLORER BICORN KASBEKE FAERIE LOCHIA GOMUTI SOVKHOZ Y GERMAL AUNTIE PERFUMY TIME FEATURE GANGAN CELEMIN MATZO",
                    i);
            assertThat(keyGenerator.getPrivateKeyAsWIF(), equalTo(EXPECTED_KEYS.get(i)));
        }
    }
}

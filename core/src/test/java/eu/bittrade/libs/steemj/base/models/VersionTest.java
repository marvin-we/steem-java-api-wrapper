package eu.bittrade.libs.steemj.base.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class VersionTest {
    /**
     * Test if a Version object results in the expected String representation
     * under the use of the
     * {@link eu.bittrade.libs.steemj.base.models.Version#Version(byte,byte,short)}
     * constructor.
     */
    @Test
    public void versionToStringTest() {
        Version version = new Version((byte) 0, (byte) 19, (short) 2);

        assertThat(version.toString(), equalTo("0.19.2"));
    }

    /**
     * Test if a Version object results in the expected String representation
     * under the use of the
     * {@link eu.bittrade.libs.steemj.base.models.Version#Version(String)}
     * constructor.
     */
    @Test
    public void versionFromStringToStringTest() {
        Version version = new Version("0.19.1");

        assertThat(version.toString(), equalTo("0.19.1"));
    }
}

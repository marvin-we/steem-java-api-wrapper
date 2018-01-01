package eu.bittrade.libs.steemj.plugins.apis.witness;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import eu.bittrade.libs.steemj.BaseIT;
import eu.bittrade.libs.steemj.IntegrationTest;
import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.exceptions.SteemCommunicationException;
import eu.bittrade.libs.steemj.exceptions.SteemResponseException;
import eu.bittrade.libs.steemj.plugins.apis.witness.enums.BandwidthType;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.AccountBandwidth;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs;
import eu.bittrade.libs.steemj.plugins.apis.witness.models.ReserveRatioObject;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class contains all test connected to the
 * {@link eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi WitnessApi}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class WitnessApiIT extends BaseIT {
    private static CommunicationHandler COMMUNICATION_HANDLER;

    /**
     * Setup the test environment.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     */
    @BeforeClass
    public static void init() throws SteemCommunicationException {
        setupIntegrationTestEnvironment();

        COMMUNICATION_HANDLER = new CommunicationHandler();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi#getAccountBandwidth(CommunicationHandler, eu.bittrade.libs.steemj.plugins.apis.witness.models.GetAccountBandwidthArgs)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetAccountBandwidth() throws SteemCommunicationException, SteemResponseException {
        final AccountBandwidth accountBandwidth = WitnessApi
                .getAccountBandwidth(COMMUNICATION_HANDLER,
                        new GetAccountBandwidthArgs(new AccountName("dez1337"), BandwidthType.POST))
                .getBandwidth().get();
    }

    /**
     * Test the
     * {@link eu.bittrade.libs.steemj.plugins.apis.witness.WitnessApi#getReserveRatio(CommunicationHandler)}
     * method.
     * 
     * @throws SteemCommunicationException
     *             If a communication error occurs.
     * @throws SteemResponseException
     *             If the response is an error.
     */
    @Category({ IntegrationTest.class })
    @Test
    public void testGetReserveRatio() throws SteemCommunicationException, SteemResponseException {
        final ReserveRatioObject reserveRatioObject = WitnessApi.getReserveRatio(COMMUNICATION_HANDLER);
    }
}
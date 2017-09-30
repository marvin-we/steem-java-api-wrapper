package eu.bittrade.libs.steemj.base.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.annotations.SignatureRequired;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * This abstract class contains fields that exist in all Steem
 * "escrow_operation" objects.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
abstract class AbstractEscrowOperation extends Operation {
    protected AccountName from;
    protected AccountName to;
    protected AccountName agent;
    // Original type is unit32_t so we use long here.
    @JsonProperty("escrow_id")
    protected long escrowId;

    /**
     * Create a new Operation object by providing the operation type.
     */
    protected AbstractEscrowOperation(boolean virtual) {
        super(virtual);
    }

}

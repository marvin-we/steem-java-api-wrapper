package eu.bittrade.libs.steemj.base.models.operations;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.annotations.SignatureRequired;
import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * This abstract class contains fields that exist in all Steem
 * "trasnfer_operation" types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
abstract class AbstractTransferOperation extends Operation {
    @SignatureRequired(type = PrivateKeyType.ACTIVE)
    @JsonProperty("from")
    protected AccountName from;
    @JsonProperty("to")
    protected AccountName to;
    @JsonProperty("amount")
    protected Asset amount;

    /**
     * Create a new Operation object by providing the operation type.
     */
    protected AbstractTransferOperation(boolean virtual) {
        super(virtual);
    }
}

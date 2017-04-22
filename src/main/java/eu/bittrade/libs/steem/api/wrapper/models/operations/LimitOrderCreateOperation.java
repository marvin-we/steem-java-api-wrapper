package eu.bittrade.libs.steem.api.wrapper.models.operations;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.VarInt;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.OperationType;
import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.models.Asset;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/steem_operations.hpp">Steem
 * limit_order_create object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class LimitOrderCreateOperation extends Operation {
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("orderid")
    // Type is uint32 in the original code.
    private int orderId;
    @JsonProperty("amount_to_sell")
    private Asset amountToSell;
    @JsonProperty("min_to_receive")
    private Asset minToReceive;
    @JsonProperty("fill_or_kill")
    private Boolean fillOrKill;
    @JsonProperty("expiration")
    private Date expiration;

    public LimitOrderCreateOperation() {
        // Define the required key type for this operation.
        super(PrivateKeyType.POSTING);
    }

    public String getOwner() {
        return owner;
    }

    public int getOrderId() {
        return orderId;
    }

    public Asset getAmountToSell() {
        return amountToSell;
    }

    public Asset getMinToReceive() {
        return minToReceive;
    }

    public Boolean getFillOrKill() {
        return fillOrKill;
    }

    public Date getExpiration() {
        return expiration;
    }

    @Override
    public byte[] toByteArray() throws UnsupportedEncodingException {
        byte[] serializedLimitOrderCreateOperation = {};

        VarInt operationType = new VarInt(OperationType.LIMIT_ORDER_CREATE_OPERATION.ordinal());
        serializedLimitOrderCreateOperation = ArrayUtils.addAll(serializedLimitOrderCreateOperation,
                operationType.encode());

        // Serializing the owner name is done in two steps: 1. Length as VarInt
        // 2. The owner name.
        VarInt ownerAccountNameLength = new VarInt(this.owner.length());
        serializedLimitOrderCreateOperation = ArrayUtils.addAll(serializedLimitOrderCreateOperation,
                ownerAccountNameLength.encode());

        serializedLimitOrderCreateOperation = ArrayUtils.addAll(serializedLimitOrderCreateOperation,
                ByteBuffer.allocate(this.owner.length()).order(ByteOrder.LITTLE_ENDIAN)
                        .put(this.owner.getBytes("UTF-8")).array());

        // TODO other fields...

        return serializedLimitOrderCreateOperation;
    }
}

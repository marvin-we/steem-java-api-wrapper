package eu.bittrade.libs.steemj.base.models.operations;

import java.util.Map;

import org.joou.UInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;

/**
 * This abstract class contains fields that exist in all Steem
 * "limit_order_operation" objects.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class AbstractLimitOrderOperation extends Operation {
    @JsonProperty("owner")
    protected AccountName owner;
    @JsonProperty("orderid")
    protected UInteger orderId;

    /**
     * Create a new Operation object by providing the operation type.
     * 
     * @param virtual
     *            Define if the operation instance is a virtual
     *            (<code>true</code>) or a market operation
     *            (<code>false</code>).
     */
    protected AbstractLimitOrderOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the owner of this order.
     * 
     * @return The owner of the order.
     */
    public abstract AccountName getOwner();

    /**
     * Set the owner for this order.
     * 
     * @param owner
     *            The owner for this order.
     */
    public abstract void setOwner(AccountName owner);

    /**
     * Get the id of this order.
     * 
     * @return The id of this order.
     */
    public abstract UInteger getOrderId();

    /**
     * Set the id of this order. The only limitation for this id is that it has
     * to be free, meaning that there is no other open order with this id.
     * 
     * @param orderId
     *            The id of this order.
     */
    public abstract void setOrderId(UInteger orderId);

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getOwner(), PrivateKeyType.ACTIVE);
    }
}

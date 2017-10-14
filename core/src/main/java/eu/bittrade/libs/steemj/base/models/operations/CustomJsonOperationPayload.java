package eu.bittrade.libs.steemj.base.models.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import eu.bittrade.libs.steemj.apis.follow.models.operations.FollowOperation;
import eu.bittrade.libs.steemj.apis.follow.models.operations.ReblogOperation;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.interfaces.Validatable;

/**
 * This class is a wrapper for the different kinds of Operations, a
 * {@link CustomJsonOperation} can carry.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_ARRAY)
@JsonSubTypes({ @Type(value = FollowOperation.class, name = "follow"),
        @Type(value = ReblogOperation.class, name = "reblog") })
public abstract class CustomJsonOperationPayload implements Validatable, ByteTransformable {
    /**
     * Create a new CustomJsonOperationPayload object by providing the operation
     * type.
     * 
     * @param virtual
     *            Define if the operation instance is a virtual
     *            (<code>true</code>) or a market operation
     *            (<code>false</code>).
     */
    protected CustomJsonOperationPayload(boolean virtual) {
        // super(virtual);
    }
    
    /**
     * Add the authorities which are required to sign this operation to an
     * existing map.
     * 
     * @param requiredAuthoritiesBase
     *            A map to which the required authorities of this operation
     *            should be added to.
     * 
     * @return A map of required authorities.
     */
    public abstract Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase);

    /**
     * Use this helper method to merge a single <code>accountName</code> into
     * the <code>requiredAuthoritiesBase</code>.
     * 
     * @param requiredAuthoritiesBase
     *            A map to which the required authorities of this operation
     *            should be added to.
     * @param signatureObject
     *            The signature object (e.g. an account names) to merge into the
     *            list.
     * @param privateKeyType
     *            The required key type.
     * @return The merged set of signature objects and required private key
     *         types.
     */
    protected Map<SignatureObject, List<PrivateKeyType>> mergeRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase, SignatureObject signatureObject,
            PrivateKeyType privateKeyType) {
        Map<SignatureObject, List<PrivateKeyType>> requiredAuthorities = requiredAuthoritiesBase;
        if (requiredAuthorities == null) {
            requiredAuthorities = new HashMap<>();
        } else if (requiredAuthorities.containsKey(signatureObject)
                && requiredAuthorities.get(signatureObject) != null) {
            requiredAuthorities.get(signatureObject).add(privateKeyType);
        } else {
            ArrayList<PrivateKeyType> requiredKeyType = new ArrayList<>();
            requiredKeyType.add(privateKeyType);

            requiredAuthorities.put(signatureObject, requiredKeyType);
        }

        return requiredAuthorities;
    }

    /**
     * Use this helper method to merge a a list of account names into the
     * <code>requiredAuthoritiesBase</code>.
     * 
     * @param requiredAuthoritiesBase
     *            A map to which the required authorities of this operation
     *            should be added to.
     * @param signatureObjects
     *            The signature objects (e.g. a list of account names) to merge
     *            into the list.
     * @param privateKeyType
     *            The required key type.
     * @return The merged set of signature objects and required private key
     *         types.
     */
    protected Map<SignatureObject, List<PrivateKeyType>> mergeRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase,
            List<? extends SignatureObject> signatureObjects, PrivateKeyType privateKeyType) {
        Map<SignatureObject, List<PrivateKeyType>> requiredAuthorities = requiredAuthoritiesBase;

        for (SignatureObject signatureObject : signatureObjects) {
            requiredAuthorities = mergeRequiredAuthorities(requiredAuthorities, signatureObject, privateKeyType);
        }

        return requiredAuthorities;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

/*
 *     This file is part of SteemJ (formerly known as 'Steem-Java-Api-Wrapper')
 * 
 *     SteemJ is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     SteemJ is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.protocol.operations;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;

/**
 * This class represents the Steem "pow2_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Pow2Operation extends Operation {
    // TODO: Fix type of work
    // pow2_work --> typedef fc::static_variant< pow2, equihash_pow > pow2_work;
    @JsonProperty("work")
    private Object[] work;
    // TODO: Fix type --> optional< public_key_type > new_owner_key;
    @JsonProperty("new_owner_key")
    private String newOwnerKey;
    @JsonProperty("props")
    private ChainProperties properties;

    /**
     * Create a new Pow2 Operation.
     */
    public Pow2Operation() {
        super(false);
    }

    /**
     * @return the work
     */
    public Object[] getWork() {
        return work;
    }

    /**
     * @param work
     *            the work to set
     */
    public void setWork(Object[] work) {
        this.work = work;
    }

    /**
     * @return the newOwnerKey
     */
    public String getNewOwnerKey() {
        return newOwnerKey;
    }

    /**
     * @param newOwnerKey
     *            the newOwnerKey to set
     */
    public void setNewOwnerKey(String newOwnerKey) {
        this.newOwnerKey = newOwnerKey;
    }

    /**
     * @return the properties
     */
    public ChainProperties getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(ChainProperties properties) {
        this.properties = properties;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        // TODO: return mergeRequiredAuthorities(requiredAuthoritiesBase,
        // this.getOwner(), PrivateKeyType.ACTIVE);
        return requiredAuthoritiesBase;
    }

    @Override
    public void validate(ValidationType validationType) {
        // TODO Auto-generated method stub

    }
}

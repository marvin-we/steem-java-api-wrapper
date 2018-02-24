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
import org.joou.ULong;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.ChainProperties;
import eu.bittrade.libs.steemj.base.models.Checksum;
import eu.bittrade.libs.steemj.base.models.Pow;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents the Steem "pow_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class PowOperation extends Operation {
    @JsonProperty("worker_account")
    private AccountName workerAccount;
    @JsonProperty("block_id")
    private Checksum blockId;
    @JsonProperty("nonce")
    private ULong nonce;
    @JsonProperty("work")
    private Pow work;
    @JsonProperty("props")
    private ChainProperties properties;

    /**
     * Create a mew pow operation.
     */
    public PowOperation() {
        super(false);
        // Set default values:
        this.setNonce(ULong.valueOf(0));
    }

    /**
     * @return the workerAccount
     */
    public AccountName getWorkerAccount() {
        return workerAccount;
    }

    /**
     * <b>Notice:</b> The private active key of this account needs to be stored
     * in the key storage.
     * 
     * @param workerAccount
     *            the workerAccount to set
     */
    public void setWorkerAccount(AccountName workerAccount) {
        this.workerAccount = workerAccount;
    }

    /**
     * @return the blockId
     */
    public Checksum getBlockId() {
        return blockId;
    }

    /**
     * @param blockId
     *            the blockId to set
     */
    public void setBlockId(Checksum blockId) {
        this.blockId = blockId;
    }

    /**
     * @return the nonce
     */
    public ULong getNonce() {
        return this.nonce;
    }

    /**
     * @param nonce
     *            the nonce to set
     */
    public void setNonce(ULong nonce) {
        this.nonce = nonce;
    }

    /**
     * @return the work
     */
    public Pow getWork() {
        return work;
    }

    /**
     * @param work
     *            the work to set
     */
    public void setWork(Pow work) {
        this.work = work;
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

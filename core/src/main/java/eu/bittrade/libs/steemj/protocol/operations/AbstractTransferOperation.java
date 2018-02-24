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

import java.security.InvalidParameterException;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This abstract class contains fields that exist in all Steem
 * "trasnfer_operation" objects.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
abstract class AbstractTransferOperation extends Operation {
    @JsonProperty("from")
    protected AccountName from;
    @JsonProperty("to")
    protected AccountName to;
    @JsonProperty("amount")
    protected Asset amount;

    /**
     * Create a new Operation object by providing the operation type.
     * 
     * @param virtual
     *            Define if the operation instance is a virtual
     *            (<code>true</code>) or a market operation
     *            (<code>false</code>).
     */
    protected AbstractTransferOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the account name of the user who transfered the <code>amount</code>.
     * 
     * @return The user which transfered the <code>amount</code>.
     */
    public AccountName getFrom() {
        return from;
    }

    /**
     * Set the account name of the user who transfers the <code>amount</code>.
     * <b>Notice:</b> The private active key of this account needs to be stored
     * in the key storage.
     * 
     * @param from
     *            The account name of the user who will transfer the
     *            <code>amount</code>.
     * @throws InvalidParameterException
     *             If the <code>from</code> account is null.
     */
    public void setFrom(AccountName from) {
        this.from = SteemJUtils.setIfNotNull(from, "The from account can't be null.");
    }

    /**
     * Get the account name of the user who received the <code>amount</code>.
     * 
     * @return The account name of the user who received the
     *         <code>amount</code>.
     */
    public AccountName getTo() {
        return to;
    }

    /**
     * Set the account name of the user who will received the
     * <code>amount</code>.
     * 
     * @param to
     *            The account name of the user who will received the
     *            <code>amount</code>.
     * @throws InvalidParameterException
     *             If the <code>to</code> account is null.
     */
    public void setTo(AccountName to) {
        this.to = SteemJUtils.setIfNotNull(to, "The to account can't be null.");
    }

    /**
     * Get the <code>amount</code> of that has been send.
     * 
     * @return The <code>amount</code> of that has been send.
     */
    public Asset getAmount() {
        return amount;
    }

    /**
     * Set the <code>amount</code> of that will be send.
     * 
     * @param amount
     *            The <code>amount</code> of that will be send.
     * @throws InvalidParameterException
     *             If the <code>amount</code> is null.
     */
    public void setAmount(Asset amount) {
        this.amount = SteemJUtils.setIfNotNull(amount, "The amount can't be null.");
    }

    @Override
    public Map<SignatureObject, PrivateKeyType> getRequiredAuthorities(
            Map<SignatureObject, PrivateKeyType> requiredAuthoritiesBase) {
        return mergeRequiredAuthorities(requiredAuthoritiesBase, this.getFrom(), PrivateKeyType.ACTIVE);
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_ASSET_VALIDATION.equals(validationType)
                && !ValidationType.SKIP_VALIDATION.equals(validationType) && amount.getAmount() <= 0) {
            throw new InvalidParameterException("Must transfer a nonzero amount.");
        }
    }
}

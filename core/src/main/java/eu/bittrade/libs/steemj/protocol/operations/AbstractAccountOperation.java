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

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.enums.ValidationType;
import eu.bittrade.libs.steemj.protocol.Authority;
import eu.bittrade.libs.steemj.protocol.PublicKey;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This abstract class contains fields that exist in all Steem Operations
 * related to the account creation / update.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public abstract class AbstractAccountOperation extends Operation {
    @JsonProperty("owner")
    protected Authority owner;
    @JsonProperty("active")
    protected Authority active;
    @JsonProperty("posting")
    protected Authority posting;
    @JsonProperty("memo_key")
    protected PublicKey memoKey;
    @JsonProperty("json_metadata")
    protected String jsonMetadata;

    /**
     * Create a new Operation object by providing the operation type.
     * 
     * @param virtual
     *            Define if the operation instance is a virtual
     *            (<code>true</code>) or a market operation
     *            (<code>false</code>).
     */
    protected AbstractAccountOperation(boolean virtual) {
        super(virtual);
    }

    /**
     * Get the owner {@link eu.bittrade.libs.steemj.protocol.Authority
     * Authority}.
     * 
     * @return The owner authority.
     */
    public abstract Authority getOwner();

    /**
     * Set the owner {@link eu.bittrade.libs.steemj.protocol.Authority
     * Authority}.
     * 
     * @param owner
     *            The owner authority.
     */
    public abstract void setOwner(Authority owner);

    /**
     * Get the active {@link eu.bittrade.libs.steemj.protocol.Authority
     * Authority}.
     * 
     * @return The active authority.
     */
    public abstract Authority getActive();

    /**
     * Set the active {@link eu.bittrade.libs.steemj.protocol.Authority
     * Authority}.
     * 
     * @param active
     *            The active authority.
     */
    public abstract void setActive(Authority active);

    /**
     * Get the posting {@link eu.bittrade.libs.steemj.protocol.Authority
     * Authority}.
     * 
     * @return The posting authority.
     */
    public abstract Authority getPosting();

    /**
     * Set the posting {@link eu.bittrade.libs.steemj.protocol.Authority
     * Authority}.
     * 
     * @param posting
     *            The posting authority.
     */
    public abstract void setPosting(Authority posting);

    /**
     * Get the memo {@link eu.bittrade.libs.steemj.protocol.PublicKey
     * PublicKey}.
     * 
     * @return The memo key.
     */
    public abstract PublicKey getMemoKey();

    /**
     * Set the memo {@link eu.bittrade.libs.steemj.protocol.PublicKey
     * PublicKey}.
     * 
     * @param memoKey
     *            The memo key.
     */
    public abstract void setMemoKey(PublicKey memoKey);

    /**
     * Get the json metadata that has been added to this operation.
     * 
     * @return The json metadata that has been added to this operation.
     */
    public String getJsonMetadata() {
        return jsonMetadata;
    }

    /**
     * Add json metadata to this operation.
     * 
     * @param jsonMetadata
     *            The json metadata.
     */
    public void setJsonMetadata(String jsonMetadata) {
        this.jsonMetadata = jsonMetadata;
    }

    @Override
    public void validate(ValidationType validationType) {
        if (!ValidationType.SKIP_VALIDATION.equals(validationType)
                && (!jsonMetadata.isEmpty() && !SteemJUtils.verifyJsonString(jsonMetadata))) {
            throw new InvalidParameterException("The given json metadata is no valid JSON");
        }
    }
}

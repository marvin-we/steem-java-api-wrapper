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
 *     along with SteemJ.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.bittrade.libs.steemj.enums;

/**
 * An enumeration for all existing validation types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum ValidationType {
    /**
     * Indicates that all validation methods (except of null pointer checks)
     * should be skipped.
     */
    SKIP_VALIDATION,
    /** Indicates that the validation of assets should be skipped. */
    SKIP_ASSET_VALIDATION,
    /**
     * Indicates that the JSON response should not be handled strict. This can
     * help in cases where the Steem node provides an API in an incompatible
     * version which results in incompatible/missing fields.
     */
    SKIP_JSON_VALIDATION
}

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
package eu.bittrade.libs.steemj.interfaces;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;

/**
 * This interface is used to make sure each operation implements a method to get
 * its byte representation.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public interface ByteTransformable {
    /**
     * Covert the operation into a byte array.
     * 
     * @return The operation as a byte array.
     * @throws SteemInvalidTransactionException
     *             If there was a problem while transforming the transaction
     *             into a byte array.
     */
    byte[] toByteArray() throws SteemInvalidTransactionException;
}

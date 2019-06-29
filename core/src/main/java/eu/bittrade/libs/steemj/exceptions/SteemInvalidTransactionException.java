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
package eu.bittrade.libs.steemj.exceptions;

/**
 * A custom Exception to handle invalid transactions.
 * 
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemInvalidTransactionException extends Exception {
    private static final long serialVersionUID = -3747123400720358339L;

    public SteemInvalidTransactionException(String message) {
        super(message);
    }

    public SteemInvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}

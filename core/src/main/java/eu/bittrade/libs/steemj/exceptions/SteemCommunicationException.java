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
 * Generic Exception Type to handle connection problems with the Steem Node.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SteemCommunicationException extends Exception {
    private static final long serialVersionUID = -3389735550453652555L;

    public SteemCommunicationException() {
        super();
    }

    public SteemCommunicationException(String message) {
        super(message);
    }

    public SteemCommunicationException(Throwable cause) {
        super("Sorry, an error occurred while processing your request.", cause);
    }

    public SteemCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

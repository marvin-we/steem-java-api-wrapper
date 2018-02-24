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
package eu.bittrade.libs.steemj.enums;

/**
 * The "block_header_exntesions" type is a variant, which means that it can
 * cover several different types. This enum stores the known core types.
 * 
 * Different types are identifies by their order: The first type has the id 0,
 * the next one the id 1 and so on. Therefore <b>do not change the order</b> of
 * this enum.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum BlockHeaderExtentsionsType {
    /** NA */
    VOID_T,
    /** Normal witness version reporting, for diagnostics and voting. */
    VERSION,
    /** NA */
    HARDFORK_VERSION_VOTE
}

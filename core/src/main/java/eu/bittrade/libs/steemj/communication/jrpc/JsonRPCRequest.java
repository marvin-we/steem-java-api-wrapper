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
package eu.bittrade.libs.steemj.communication.jrpc;

import java.util.Random;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;

import eu.bittrade.libs.steemj.communication.CommunicationHandler;
import eu.bittrade.libs.steemj.enums.RequestMethod;
import eu.bittrade.libs.steemj.enums.SteemApiType;

/**
 * A wrapper object that carries all required fields for a request.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonPropertyOrder({ "jsonrpc", "method", "params", "id" })
public class JsonRPCRequest {
    /** A shared <code>Random</code> instance. */
    private static Random randomGenerator = new Random();
    /** The JSON RPC version. */
    private static final String JSONRPC = "2.0";
    /** The ID of this request. */
    private final long id = randomGenerator.nextLong();
    private String method;
    private Object params;

    /**
     * Instantiate a new RequestObject.
     * 
     * @param steemApiType
     *            The {@link SteemApiType} the <code>requestMethod</code>
     *            belongs to or <code>null</code> if the default namespace
     *            should be used.
     * @param requestMethod
     *            The {@link RequestMethod} to request.
     * @param params
     *            An object which contains all parameters required by the
     *            <code>requestMethod</code> or <code>null</code> if no
     *            parameters are required.
     */
    public JsonRPCRequest(@Nullable SteemApiType steemApiType, RequestMethod requestMethod, @Nullable Object params) {
        String namespaceAndMethod = "";
        if (steemApiType != null) {
            namespaceAndMethod = steemApiType.name().toLowerCase() + ".";
        }
        this.method = namespaceAndMethod + requestMethod.name().toLowerCase();
        this.params = params;
    }

    /**
     * Get the json-rpc version.
     * 
     * @return The used json-rpc version.
     */
    public String getJsonrpc() {
        return JSONRPC;
    }

    /**
     * Get the id of this request.
     * 
     * @return The id of this request.
     */
    public long getId() {
        return id;
    }

    /**
     * Get the full namespace and method name.
     * 
     * @return The full namespace and method name.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Get the additional parameters.
     * 
     * @return The additional parameters.
     */
    public Object getParams() {
        return params;
    }

    /**
     * @return The json representation of this object.
     * @throws JsonProcessingException
     *             If the object can not be transformed into valid json.
     */
    public String toJson() throws JsonProcessingException {
        return CommunicationHandler.getObjectMapper().writeValueAsString(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.chain.EscrowObject;

/**
 * This class represents a Steem "list_escrows_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListEscrowsReturn {
    @JsonProperty("escrows")
    private List<EscrowObject> escrows;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    private ListEscrowsReturn() {
    }

    /**
     * @return the escrows
     */
    public List<EscrowObject> getEscrows() {
        return escrows;
    }

    /**
     * @param escrows
     *            the escrows to set
     */
    public void setEscrows(List<EscrowObject> escrows) {
        this.escrows = escrows;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

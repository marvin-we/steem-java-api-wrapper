package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Steem "list_witnesses_return" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ListWitnessesReturn {
    @JsonProperty("witnesses")
    private List<Witness> witnesses;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     */
    protected ListWitnessesReturn() {
    }

    /**
     * @return the witnesses
     */
    public List<Witness> getWitnesses() {
        return witnesses;
    }

    /**
     * @param witnesses
     *            the witnesses to set
     */
    public void setWitnesses(List<Witness> witnesses) {
        this.witnesses = witnesses;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

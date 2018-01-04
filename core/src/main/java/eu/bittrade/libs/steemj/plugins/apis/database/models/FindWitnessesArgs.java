package eu.bittrade.libs.steemj.plugins.apis.database.models;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;

/**
 * This class represents a Steem "find_witnesses_args" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class FindWitnessesArgs {
    @JsonProperty("owners")
    private List<AccountName> owners;

    /**
     * 
     * @param owners
     */
    @JsonCreator()
    public FindWitnessesArgs(@JsonProperty("owners") List<AccountName> owners) {
        this.setOwners(owners);
    }

    /**
     * @return the owners
     */
    public List<AccountName> getOwners() {
        return owners;
    }

    /**
     * @param owners
     *            the owners to set
     */
    public void setOwners(List<AccountName> owners) {
        this.owners = owners;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

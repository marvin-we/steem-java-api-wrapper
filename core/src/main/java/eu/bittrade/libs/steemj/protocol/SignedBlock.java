package eu.bittrade.libs.steemj.protocol;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.SignedTransaction;

/**
 * This class is the java implementation of the Steem "signed_block" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class SignedBlock extends SignedBlockHeader {
    @JsonProperty("transactions")
    protected List<SignedTransaction> transactions;

    /**
     * This object is only used to wrap the JSON response in a POJO, so
     * therefore this class should not be instantiated.
     * 
     * Visibility set to protected as this object is the parent of
     * {@link ExtendedSignedBlock}.
     */
    protected SignedBlock() {
    }

    /**
     * @return A list of transactions processed in this block.
     */
    public List<SignedTransaction> getTransactions() {
        return transactions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

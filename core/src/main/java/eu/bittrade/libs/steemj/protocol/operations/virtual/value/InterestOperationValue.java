package eu.bittrade.libs.steemj.protocol.operations.virtual.value;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.protocol.AccountName;
import eu.bittrade.libs.steemj.protocol.Asset;

public class InterestOperationValue {
	@JsonProperty("owner")
    private AccountName owner;
    @JsonProperty("interest")
    private Asset interest;

    /**
     * @return The owner.
     */
    public AccountName getOwner() {
        return owner;
    }

    /**
     * @return The interest.
     */
    public Asset getInterest() {
        return interest;
    }

	private InterestOperationValue() {
		super();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

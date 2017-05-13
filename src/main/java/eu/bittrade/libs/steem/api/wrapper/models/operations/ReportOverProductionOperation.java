package eu.bittrade.libs.steem.api.wrapper.models.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steem.api.wrapper.enums.PrivateKeyType;
import eu.bittrade.libs.steem.api.wrapper.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.SignedBlockHeader;

/**
 * This operation is used to report a miner who signs two blocks at the same
 * time. To be valid, the violation must be reported within
 * STEEMIT_MAX_WITNESSES blocks of the head block (1 round) and the producer
 * must be in the ACTIVE witness set.
 *
 * Users not in the ACTIVE witness set should not have to worry about their key
 * getting compromised and being used to produced multiple blocks so the
 * attacker can report it and steel their vesting steem.
 *
 * The result of the operation is to transfer the full VESTING STEEM balance of
 * the block producer to the reporter.
 */
public class ReportOverProductionOperation extends Operation {
    private AccountName reporter;
    @JsonProperty("first_block")
    private SignedBlockHeader firstBlock;
    @JsonProperty("second_block")
    private SignedBlockHeader secondBlock;

    public ReportOverProductionOperation() {
        super(PrivateKeyType.POSTING);
    }

    /**
     * @return the reporter
     */
    public AccountName getReporter() {
        return reporter;
    }

    /**
     * @param reporter
     *            the reporter to set
     */
    public void setReporter(AccountName reporter) {
        this.reporter = reporter;
    }

    /**
     * @return the firstBlock
     */
    public SignedBlockHeader getFirstBlock() {
        return firstBlock;
    }

    /**
     * @param firstBlock
     *            the firstBlock to set
     */
    public void setFirstBlock(SignedBlockHeader firstBlock) {
        this.firstBlock = firstBlock;
    }

    /**
     * @return the secondBlock
     */
    public SignedBlockHeader getSecondBlock() {
        return secondBlock;
    }

    /**
     * @param secondBlock
     *            the secondBlock to set
     */
    public void setSecondBlock(SignedBlockHeader secondBlock) {
        this.secondBlock = secondBlock;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

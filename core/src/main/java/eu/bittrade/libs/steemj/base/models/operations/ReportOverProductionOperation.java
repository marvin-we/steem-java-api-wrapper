package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.base.models.AccountName;
import eu.bittrade.libs.steemj.base.models.SignedBlockHeader;
import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.enums.PrivateKeyType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "report_over_production_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class ReportOverProductionOperation extends Operation {
    @JsonProperty("reporter")
    private AccountName reporter;
    @JsonProperty("first_block")
    private SignedBlockHeader firstBlock;
    @JsonProperty("second_block")
    private SignedBlockHeader secondBlock;

    /**
     * Create a new report over production operation.
     * 
     * This operation is used to report a miner who signs two blocks at the same
     * time. To be valid, the violation must be reported within
     * STEEMIT_MAX_WITNESSES blocks of the head block (1 round) and the producer
     * must be in the ACTIVE witness set.
     *
     * Users not in the ACTIVE witness set should not have to worry about their
     * key getting compromised and being used to produced multiple blocks so the
     * attacker can report it and steel their vesting steem.
     *
     * The result of the operation is to transfer the full VESTING STEEM balance
     * of the block producer to the reporter.
     * 
     * @param reporter
     * @param firstBlock
     * @param secondBlock
     * @throws InvalidParameterException
     *             If one of the arguments does not fulfill the requirements.
     */
    public ReportOverProductionOperation(@JsonProperty("reporter") AccountName reporter,
            @JsonProperty("first_block") SignedBlockHeader firstBlock,
            @JsonProperty("second_block") SignedBlockHeader secondBlock) {
        super(false);
    }

    /**
     * Get the account who reported the violation.
     * 
     * @return The account who reported the violation.
     */
    public AccountName getReporter() {
        return reporter;
    }

    /**
     * Set the account who wants to report the violation.
     * 
     * @param reporter
     *            The account who reported the violation.
     */
    public void setReporter(AccountName reporter) {
        this.reporter = reporter;
    }

    /**
     * Get the first block signed by the witness.
     * 
     * @return The first block signed by the witness.
     */
    public SignedBlockHeader getFirstBlock() {
        return firstBlock;
    }

    /**
     * Set the first block signed by the witness.
     * 
     * @param firstBlock
     *            The first block signed by the witness.
     */
    public void setFirstBlock(SignedBlockHeader firstBlock) {
        this.firstBlock = firstBlock;

        validate_account_name(first_block.witness);
        FC_ASSERT(first_block.witness == second_block.witness);
        FC_ASSERT(first_block.timestamp == second_block.timestamp);
        FC_ASSERT(first_block.signee() == second_block.signee());
        FC_ASSERT(first_block.id() != second_block.id());
    }

    /**
     * Get the second block signed by the witness.
     * 
     * @return The second block signed by the witness.
     */
    public SignedBlockHeader getSecondBlock() {
        return secondBlock;
    }

    /**
     * Set the second block signed by the witness.
     * 
     * @param secondBlock
     *            The second block signed by the witness.
     */
    public void setSecondBlock(SignedBlockHeader secondBlock) {
        this.secondBlock = secondBlock;

        FC_ASSERT(first_block.witness == second_block.witness);
        FC_ASSERT(first_block.timestamp == second_block.timestamp);
        FC_ASSERT(first_block.signee() == second_block.signee());
        FC_ASSERT(first_block.id() != second_block.id());
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedReportOverProductionOperation = new ByteArrayOutputStream()) {
            serializedReportOverProductionOperation.write(SteemJUtils
                    .transformIntToVarIntByteArray(OperationType.REPORT_OVER_PRODUCTION_OPERATION.ordinal()));
            serializedReportOverProductionOperation.write(this.getReporter().toByteArray());
            serializedReportOverProductionOperation.write(this.getFirstBlock().toByteArray());
            serializedReportOverProductionOperation.write(this.getSecondBlock().toByteArray());

            return serializedReportOverProductionOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Map<SignatureObject, List<PrivateKeyType>> getRequiredAuthorities(
            Map<SignatureObject, List<PrivateKeyType>> requiredAuthoritiesBase) {
        return requiredAuthoritiesBase;
    }
}

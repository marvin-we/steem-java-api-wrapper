package eu.bittrade.libs.steem.api.wrapper.communication.dto;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.ArrayUtils;
import org.bitcoinj.core.VarInt;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author<a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class TransactionDTO {
    /**
     * The ref_block_num indicates a particular block in the past by referring
     * to the block number which has this number as the last two bytes.
     */
    @JsonProperty("ref_block_num")
    private short refBlockNum;
    /**
     * The ref_block_prefix on the other hand is obtain from the block id of
     * that particular reference block.
     */
    @JsonProperty("ref_block_prefix")
    private int refBlockPrefix;
    @JsonProperty("expiration")
    private Date expirationDate;
    private OperationDTO operations[];
    // TODO Find out what type this is and what the use of this field is.
    private Object[] extensions;
    private String[] signatures;

    private SimpleDateFormat simpleDateFormat;

    public TransactionDTO() {
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        this.simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public short getRefBlockNum() {
        return refBlockNum;
    }

    public void setRefBlockNum(short refBlockNum) {
        this.refBlockNum = refBlockNum;
    }

    public int getRefBlockPrefix() {
        return refBlockPrefix;
    }

    public void setRefBlockPrefix(int refBlockPrefix) {
        this.refBlockPrefix = refBlockPrefix;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * The date has to be specified as String and needs a special format:
     * yyyy-MM-dd'T'HH:mm:ss
     * 
     * Example: "2016-08-08T12:24:17"
     * 
     * @param expirationDate
     *            The expiration date as its String representation.
     * @throws ParseException
     *             If the given String does not patch the pattern.
     */
    public void setExpirationDate(String expirationDate) throws ParseException {
        this.expirationDate = this.simpleDateFormat.parse(expirationDate + "UTC");
    }

    public OperationDTO[] getOperations() {
        return operations;
    }

    public void setOperations(OperationDTO[] operations) {
        this.operations = operations;
    }

    public Object[] getExtensions() {
        return extensions;
    }

    public void setExtensions(Object[] extensions) {
        this.extensions = extensions;
    }

    // TODO: HANDLE THESE
    public String[] getSignatures() {
        return this.signatures;
    }

    public void setSignatures(String[] signatures) {
        this.signatures = signatures;
    }

    public int getExpirationDateAsUnixTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expirationDate);

        return (int) (calendar.getTimeInMillis() / 1000);
    }

    /**
     * This method creates a byte array based on a transaction object under the
     * use of a guide written by <a
     * href="https://steemit.com/steem/@xeroc/steem-transaction-signing-in-a-nutshell>Xeroc</a> and is only for internal use.
     * 
     * @return The serialized transaction object.
     * @throws UnsupportedEncodingException
     *             If your platform does not know UTF-8.
     */
    byte[] serialize() throws UnsupportedEncodingException {
        byte[] serializedTransaction = {};

        serializedTransaction = ArrayUtils.addAll(serializedTransaction,
                ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(this.getRefBlockNum()).array());

        serializedTransaction = ArrayUtils.addAll(serializedTransaction,
                ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(this.getRefBlockPrefix()).array());

        serializedTransaction = ArrayUtils.addAll(serializedTransaction, ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN).putInt(this.getExpirationDateAsUnixTime()).array());

        VarInt numberOfTransactionsAsVarInt = new VarInt(this.getOperations().length);
        serializedTransaction = ArrayUtils.addAll(serializedTransaction, numberOfTransactionsAsVarInt.encode());

        for (OperationDTO operation : this.getOperations()) {
            serializedTransaction = ArrayUtils.addAll(serializedTransaction, operation.serializeOperation());
        }

        // TODO: Understand what this field is used for. For now we just append
        // an empty byte.
        byte[] extension = { 0x00 };
        serializedTransaction = ArrayUtils.addAll(serializedTransaction, extension);

        return serializedTransaction;
    }
}

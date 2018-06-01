package eu.bittrade.libs.steemj.base.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BlockHeader implements ByteTransformable , HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    protected BlockId previous;
    protected TimePointSec timestamp;
    protected String witness;
    @JsonProperty("transaction_merkle_root")
    protected Checksum transactionMerkleRoot;
    // Original type is "block_header_extensions_type" which is an array of
    // "block_header_extensions".
    protected List<BlockHeaderExtensions> extensions;

    /**
     * @return the previous
     */
    public BlockId getPrevious() {
        return previous;
    }

    /**
     * @param previous
     *            the previous to set
     */
    public void setPrevious(BlockId previous) {
        this.previous = previous;
    }

    /**
     * @return the witness
     */
    public String getWitness() {
        return witness;
    }

    /**
     * @param witness
     *            the witness to set
     */
    public void setWitness(String witness) {
        this.witness = witness;
    }

    /**
     * @return the transactionMerkleRoot
     */
    public Checksum getTransactionMerkleRoot() {
        return transactionMerkleRoot;
    }

    /**
     * @param transactionMerkleRoot
     *            the transactionMerkleRoot to set
     */
    public void setTransactionMerkleRoot(Checksum transactionMerkleRoot) {
        this.transactionMerkleRoot = transactionMerkleRoot;
    }

    /**
     * Get the list of configured extensions.
     * 
     * @return All extensions.
     */
    public List<BlockHeaderExtensions> getExtensions() {
        if (extensions == null) {
            extensions = new ArrayList<>();
        }
        return extensions;
    }

    /**
     * Extensions are currently not supported and will be ignored.
     * 
     * @param extensions
     *            Define a list of extensions.
     */
    public void setExtensions(List<BlockHeaderExtensions> extensions) {
        this.extensions = extensions;
    }

    /**
     * @return the timestamp
     */
    public TimePointSec getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(TimePointSec timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        // serializedRecoverAccountOperation
        // .write(SteemJUtils.transformIntToVarIntByteArray(this.getExtensions().size()));
        return null;
    }
}

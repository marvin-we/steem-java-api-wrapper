package dez.steemit.com.models.operations;

import java.util.Date;

public class AccountAction {
	private String try_id;
	private long block;
	private int trx_in_block;
	private int op_in_trx;
	private int virtual_op;
	private Date timestamp;
	private Operation[] op;

	public String getTry_id() {
		return try_id;
	}

	public long getBlock() {
		return block;
	}

	public int getTrx_in_block() {
		return trx_in_block;
	}

	public int getOp_in_trx() {
		return op_in_trx;
	}

	public int getVirtual_op() {
		return virtual_op;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public Operation[] getOp() {
		return op;
	}
}

package eu.bittrade.libs.steemj.base.models;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AppliedOperation {
    /**
     * id_type id;
     * 
     * transaction_id_type trx_id; uint32_t block = 0; uint32_t trx_in_block =
     * 0; uint16_t op_in_trx = 0; uint64_t virtual_op = 0; time_point_sec
     * timestamp; buffer_type serialized_op;
     */

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

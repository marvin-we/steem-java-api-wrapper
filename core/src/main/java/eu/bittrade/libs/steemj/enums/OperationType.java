package eu.bittrade.libs.steemj.enums;

/**
 * This enumeration contains all available, non virtual operation types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public enum OperationType {
    /** The vote_operation type. */
    VOTE_OPERATION(0),
    /** The comment_operation type. */
    COMMENT_OPERATION(1),
    /** The transfer_operation type. */
    TRANSFER_OPERATION(2),
    /** The transfer_to_vesting_operation type. */
    TRANSFER_TO_VESTING_OPERATION(3),
    /** The withdraw_vesting_operation type. */
    WITHDRAW_VESTING_OPERATION(4),
    /** The limit_order_create_operation type. */
    LIMIT_ORDER_CREATE_OPERATION(5),
    /** The limit_order_cancel_operation type. */
    LIMIT_ORDER_CANCEL_OPERATION(6),
    /** The feed_publish_operation type. */
    FEED_PUBLISH_OPERATION(7),
    /** The convert_operation type. */
    CONVERT_OPERATION(8),
    /** The account_create_operation type. */
    ACCOUNT_CREATE_OPERATION(9),
    /** The account_update_operation type. */
    ACCOUNT_UPDATE_OPERATION(10),
    /** The witness_update_operation type. */
    WITNESS_UPDATE_OPERATION(11),
    /** The account_witness_vote_operation type. */
    ACCOUNT_WITNESS_VOTE_OPERATION(12),
    /** The account_witness_proxy_operation type. */
    ACCOUNT_WITNESS_PROXY_OPERATION(13),
    /** The pow_operation type. */
    POW_OPERATION(14),
    /** The custom_operation type. */
    CUSTOM_OPERATION(15),
    /** The report_over_production_operation type. */
    REPORT_OVER_PRODUCTION_OPERATION(16),
    /** The delete_comment_operation type. */
    DELETE_COMMENT_OPERATION(17),
    /** The custom_json_operation type. */
    CUSTOM_JSON_OPERATION(18),
    /** The comment_options_operation type. */
    COMMENT_OPTIONS_OPERATION(19),
    /** The set_withdraw_vesting_route_operation type. */
    SET_WITHDRAW_VESTING_ROUTE_OPERATION(20),
    /** The limit_order_create2_operation type. */
    LIMIT_ORDER_CREATE2_OPERATION(21),
    /** The challenge_auhtority_operation type. */
    CHALLENGE_AUTHORITY_OPERATION(22),
    /** The prove_authority_operation type. */
    PROVE_AUTHORITY_OPERATION(23),
    /** The request_account_recovery_operation type. */
    REQUEST_ACCOUNT_RECOVERY_OPERATION(24),
    /** The recover_account_operation type. */
    RECOVER_ACCOUNT_OPERATION(25),
    /** The recovery_account_operation type. */
    CHANGE_RECOVERY_ACCOUNT_OPERATION(26),
    /** The escrow_transfer_operation type. */
    ESCROW_TRANSFER_OPERATION(27),
    /** The escrow_dispute_operation type. */
    ESCROW_DISPUTE_OPERATION(28),
    /** The escrow_release_operation type. */
    ESCROW_RELEASE_OPERATION(29),
    /** The pow2_operation type. */
    POW2_OPERATION(30),
    /** The escrow_approve_operation type. */
    ESCROW_APPROVE_OPERATION(31),
    /** The transfer_to_savings_operation type. */
    TRANSFER_TO_SAVINGS_OPERATION(32),
    /** The transfer_from_savings_operation type. */
    TRANSFER_FROM_SAVINGS_OPERATION(33),
    /** The cancel_transfer_from_savings_operation type. */
    CANCEL_TRANSFER_FROM_SAVINGS_OPERATION(34),
    /** The custom_binary_operation type. */
    CUSTOM_BINARY_OPERATION(35),
    /** The decline_voting_rights_operation type. */
    DECLINE_VOTING_RIGHTS_OPERATION(36),
    /** The reset_account_operation type. */
    RESET_ACCOUNT_OPERATION(37),
    /** The set_reset_account_operation type. */
    SET_RESET_ACCOUNT_OPERATION(38),
    /** The claim_reward_balance_operation type. */
    CLAIM_REWARD_BALANCE_OPERATION(39),
    /** The delegate_vesting_shares_operation type. */
    DELEGATE_VESTING_SHARES_OPERATION(40),
    /** The account_create_with_delegation_operation type. */
    ACCOUNT_CREATE_WITH_DELEGATION_OPERATION(41);

    /**
     * The id of an operation. The id is used for the byte transformation and
     * changing it can cause an unexpected behavior.
     */
    private int orderId;

    /**
     * Set the id of the operation.
     * 
     * @param orderId
     *            The id of the operation to set.
     */
    private OperationType(int orderId) {
        this.orderId = orderId;
    }

    /**
     * Get the id of the operation.
     * 
     * @return The id of the operation.
     */
    public int getOrderId() {
        return orderId;
    }
}

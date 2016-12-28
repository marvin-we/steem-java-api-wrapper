package dez.steemit.com.communication;

/**
 * An enumeration for all existing API methods.
 * 
 * @author http://steemit.com/@dez1337
 */
public enum RequestMethods {
	// database_api
	GET_ACCOUNT_COUNT, GET_ACCOUNT_HISTORY, GET_ACCOUNT_VOTES, GET_WITNESS_COUNT, GET_MINER_QUEUE, GET_CONFIG, GET_TRENDING_TAGS,
	// network_node_api
	GET_INFO,
	// login_api
	LOGIN, GET_API_BY_NAME, GET_VERSION,
	// network_broadcast_api
	BROADCAST_TRANSACTION_SYNCHRONOUS;
}

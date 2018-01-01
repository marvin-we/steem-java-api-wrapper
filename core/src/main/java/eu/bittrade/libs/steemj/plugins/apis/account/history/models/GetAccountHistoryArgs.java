package eu.bittrade.libs.steemj.plugins.apis.account.history.models;

public class GetAccountHistoryArgs {
    struct get_account_history_args
    {
       steem::protocol::account_name_type   account;
       uint64_t                               start = -1;
       uint32_t                               limit = 1000;
    };
}

package eu.bittrade.libs.steem.api.wrapper.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Work {
    @JsonProperty("worker")
    private String worker;
    @JsonProperty("input")
    private String input;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("work")
    private String work;

    public String getWorker() {
        return worker;
    }

    public String getInput() {
        return input;
    }

    public String getSignature() {
        return signature;
    }

    public String getWork() {
        return work;
    }
}

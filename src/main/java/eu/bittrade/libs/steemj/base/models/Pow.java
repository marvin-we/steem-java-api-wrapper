package eu.bittrade.libs.steemj.base.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Pow {
    @JsonProperty("worker")
    private PublicKey worker;
    @JsonProperty("input")
    private String input;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("work")
    private String work;

   /*TODO: public_key_type worker;
    digest_type     input;
    signature_type  signature;
    digest_type     work;
    */ 
    
    public PublicKey getWorker() {
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

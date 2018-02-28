package eu.bittrade.libs.steemj.base.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import eu.bittrade.crypto.core.ECKey;
import eu.bittrade.crypto.core.Sha256Hash;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;

/**
 * This class represents the Steem "pow" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Pow implements ByteTransformable {
    @JsonProperty("worker")
    private PublicKey worker;
    // Original type is "digest_type" which is a "fc:sha256" object.
    @JsonProperty("input")
    private Sha256Hash input;
    // TODO: signature_type signature;
    @JsonProperty("signature")
    private String signature;
    // Original type is "digest_type" which is a "fc:sha256" object.
    @JsonProperty("work")
    private Sha256Hash work;

    public Pow() {

    }

    public Pow(ECKey privateKey, Sha256Hash input) {
    }

    public PublicKey getWorker() {
        return worker;
    }

    public Sha256Hash getInput() {
        return input;
    }

    public String getSignature() {
        return signature;
    }

    public Sha256Hash getWork() {
        return work;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        // TODO Auto-generated method stub
        return null;
    }
}

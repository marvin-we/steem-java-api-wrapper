package eu.bittrade.libs.steem.api.wrapper.models.deserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eu.bittrade.libs.steem.api.wrapper.models.AccountName;
import eu.bittrade.libs.steem.api.wrapper.models.BeneficiaryRouteType;
import eu.bittrade.libs.steem.api.wrapper.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steem.api.wrapper.models.PublicKey;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentPayoutBeneficiariesDeserializer extends JsonDeserializer<CommentPayoutBeneficiaries> {
    @Override
    public CommentPayoutBeneficiaries deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        HashMap<Integer, BeneficiaryRouteType> result = new HashMap<>();

        ObjectCodec codec = jsonParser.getCodec();
        TreeNode rootNode = codec.readTree(jsonParser);
        System.out.println("Size=" + rootNode.size() + "Type:" + rootNode.toString());
        if (rootNode.isValueNode()) {
            
            for (JsonNode node : (ArrayNode) rootNode) {
                BeneficiaryRouteType beneficiaryRouteType = new BeneficiaryRouteType();
                beneficiaryRouteType.setAccount(new AccountName(""));
                beneficiaryRouteType.setWeight((short)0);
            }

            //CommentPayoutBeneficiaries commentPayoutBeneficiaries= new CommentPayoutBeneficiaries();
            //commentPayoutBeneficiaries.setBeneficiaries(result);
            

            //result.put(Integer.valueOf(0), beneficiaryRouteType);
            
            //return commentPayoutBeneficiaries;
        }

        throw new IllegalArgumentException("JSON Node is not an array.");
    }
}

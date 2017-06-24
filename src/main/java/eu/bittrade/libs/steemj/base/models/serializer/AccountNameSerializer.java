package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eu.bittrade.libs.steemj.base.models.AccountName;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountNameSerializer extends JsonSerializer<AccountName> {

    @Override
    public void serialize(AccountName accountName, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException, JsonProcessingException {
        jsonGenerator.writeString(accountName.getAccountName());
    }
}

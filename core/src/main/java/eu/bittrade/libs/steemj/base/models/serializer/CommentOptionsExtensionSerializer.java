package eu.bittrade.libs.steemj.base.models.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;

import eu.bittrade.libs.steemj.base.models.CommentOptionsExtension;
import eu.bittrade.libs.steemj.base.models.CommentPayoutBeneficiaries;
import eu.bittrade.libs.steemj.enums.CommentOptionsExtensionsType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class CommentOptionsExtensionSerializer extends JsonSerializer<CommentOptionsExtension> {

    @Override
    public void serialize(CommentOptionsExtension commentOptionsExtension, JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider) throws IOException {
        if (!(commentOptionsExtension instanceof CommentPayoutBeneficiaries)) {
            throw new IllegalArgumentException(
                    "Unknown extension type class '" + commentOptionsExtension.getClass().getSimpleName() + "'.");
        }

        jsonGenerator.writeStartArray();
        jsonGenerator.writeNumber(CommentOptionsExtensionsType.COMMENT_PAYOUT_BENEFICIARIES.ordinal());

        JavaType javaType = serializerProvider.constructType(CommentPayoutBeneficiaries.class);
        BeanDescription beanDesc = serializerProvider.getConfig().introspect(javaType);
        JsonSerializer<Object> serializer = BeanSerializerFactory.instance.findBeanSerializer(serializerProvider,
                javaType, beanDesc);
        serializer.serialize((CommentPayoutBeneficiaries) commentOptionsExtension, jsonGenerator, serializerProvider);

        jsonGenerator.writeEndArray();
    }
}

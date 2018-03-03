package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.bittrade.libs.steemj.base.models.Asset;
import eu.bittrade.libs.steemj.enums.AssetSymbolType;

/**
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AssetDeserializer extends JsonDeserializer<Asset> {
	@Override
	public Asset deserialize(JsonParser jasonParser, DeserializationContext deserializationContext) throws IOException {
		JsonToken currentToken = jasonParser.currentToken();
		String text = jasonParser.getText();
		if (currentToken != null && JsonToken.VALUE_STRING.equals(currentToken)) {
			if (text.contains(" ")) {
				String[] assetFields = text.split(" ");
				/*
				 * Set the symbol first which calculates the precision internally.
				 *
				 * The amount is provided as a double value while we need a long value for the
				 * byte representation so we transform the amount into a long value here.
				 */
				BigDecimal value = new BigDecimal(assetFields[0]);
				long lvalue = value.movePointRight(value.scale()).longValue();
				return new Asset(lvalue, AssetSymbolType.valueOf(assetFields[1]));
			}
			//no " ", so no asset type marker, assume SBD
			BigDecimal value = new BigDecimal(text);
			long lvalue = value.movePointRight(value.scale()).longValue();
			return new Asset(lvalue, AssetSymbolType.SBD);
		}
		if (jasonParser.getCurrentToken() == JsonToken.START_ARRAY) {
			while (jasonParser.nextToken() != JsonToken.END_ARRAY);
		}
		return new Asset(0l, AssetSymbolType.SBD);
//		throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.VALUE_STRING + "'.");
	}
}
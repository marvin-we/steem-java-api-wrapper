package eu.bittrade.libs.steemj.base.models.deserializer;

import java.io.IOException;

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
    public Asset deserialize(JsonParser jasonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonToken currentToken = jasonParser.currentToken();
        if (currentToken != null && JsonToken.VALUE_STRING.equals(currentToken)) {
            String[] assetFields = jasonParser.getText().split(" ");

            if (assetFields.length == 2) {
                Asset asset = new Asset();
                // Set the symbol first which calculates the precision
                // internally.
                asset.setSymbol(AssetSymbolType.valueOf(assetFields[1]));
                // The amount is provided as a double value while we need a long
                // value for the byte representation so we transform the amount
                // into a long value here.
                Double assetAmount = Double.valueOf(assetFields[0]) * Math.pow(10.0, asset.getPrecision());
                asset.setAmount(assetAmount.longValue());
                return asset;
            }
        }

        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.VALUE_STRING + "'.");
    }
}
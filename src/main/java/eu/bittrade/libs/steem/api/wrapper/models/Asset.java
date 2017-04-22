package eu.bittrade.libs.steem.api.wrapper.models;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import eu.bittrade.libs.steem.api.wrapper.enums.AssetSymbolType;

/**
 * This class is the java implementation of the <a href=
 * "https://github.com/steemit/steem/blob/master/libraries/protocol/include/steemit/protocol/asset.hpp">Steem
 * assets object</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize(using = AssetDeserializer.class)
public class Asset {
    // Type is safe<int64_t> in the original code.
    private double amount;
    // Type us uint64_t in the original code.
    private AssetSymbolType symbol;
    private int precision;

    public double getAmount() {
        return amount;
    }

    public int getPrecision() {
        // TODO?
        //return "{:.{prec}f} {}".format(self["amount"], self["asset"], prec=prec)
        return precision;
    }

    public AssetSymbolType getSymbol() {
        return symbol;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSymbol(AssetSymbolType symbol) {
        if (symbol.equals(AssetSymbolType.VESTS)) {
            this.precision = 6;
        } else {
            this.precision = 3;
        }

        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

class AssetDeserializer extends JsonDeserializer<Asset> {
    @Override
    public Asset deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        JsonToken currentToken = p.currentToken();
        if (currentToken != null && JsonToken.VALUE_STRING.equals(currentToken)) {
            String[] assetFields = p.getText().split(" ");

            if (assetFields.length == 2) {
                Asset asset = new Asset();
                asset.setAmount(Double.valueOf(assetFields[0]));
                asset.setSymbol(AssetSymbolType.valueOf(assetFields[1]));
                return asset;
            }
        }

        throw new IllegalArgumentException("Found '" + currentToken + "' instead of '" + JsonToken.VALUE_STRING + "'.");
    }
}

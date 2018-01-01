package eu.bittrade.libs.steemj.protocol.operations;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class summarizes utility methods for the different operation types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BaseOperation {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

package eu.bittrade.libs.steemj.base.models.operations;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class summarizes utility methods for the different operation types.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class BaseOperation {
    /**
     * This method will check if given <code>objectToSet</code> is
     * <code>null</code> and throw an {@link InvalidParameterException} if this
     * is the case.
     * 
     * @param <T>
     *            The type of the <code>objectToSet</code>.
     * @param objectToSet
     *            The object to check.
     * @param message
     *            The message of the generated exception.
     * @return The given <code>objectToSet</code> if its not <code>null</code>.
     * @throws InvalidParameterException
     *             If the <code>objectToSet</code> is <code>null</code>.
     */
    protected <T> T setIfNotNull(T objectToSet, String message) {
        if (objectToSet == null) {
            throw new InvalidParameterException(message);
        }

        return objectToSet;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

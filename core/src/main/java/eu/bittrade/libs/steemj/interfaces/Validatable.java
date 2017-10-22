package eu.bittrade.libs.steemj.interfaces;

import java.security.InvalidParameterException;

import eu.bittrade.libs.steemj.enums.ValidationType;

/**
 * This interface is used to make sure an object implements the validate method.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public interface Validatable {
    /**
     * Use this method to verify that this object is valid.
     * 
     * @param validationType
     *            An indicator telling the method what should be validated.
     * @throws InvalidParameterException
     *             If a field does not fulfill the requirements.
     */
    public void validate(ValidationType validationType);
}

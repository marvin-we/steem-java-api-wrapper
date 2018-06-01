package eu.bittrade.libs.steemj.base.models;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.interfaces.ByteTransformable;
import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;
import eu.bittrade.libs.steemj.interfaces.SignatureObject;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem data type "account_name_type".
 * 
 * @author <a href="http://Steemit.com/@dez1337">dez1337</a>
 */
public class AccountName implements ByteTransformable, SignatureObject, HasJsonAnyGetterSetter {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    private String name;

    /**
     * Create an account name object with an empty account name.
     */
    public AccountName() {
        this.setName("");
    }

    /**
     * Create an account name object containing the given account name.
     * 
     * @param name
     *            The name for the account to set.
     * @throws InvalidParameterException
     *             If the account name is not valid (@see #setName(String)).
     * 
     */
    @JsonCreator
    public AccountName(String name) {
        this.setName(name);
    }

    /**
     * Get the account name of this instance.
     * 
     * @return The account name.
     */
    @JsonValue
    public String getName() {
        return name;
    }

    /**
     * Set the account name of this instance.
     * 
     * @param name
     *            An account name in its String representation. The account name
     *            can either be empty or needs to have a length between 3 and 16
     *            characters. If provided, the account name has to follow
     *            specific rules:
     *            <ul>
     *            <li>The account name must start with: a-z</li>
     *            <li>Followed by: a-z,0-9,-</li>
     *            <li>End with: a-z, 0-9</li>
     *            </ul>
     *            If the account name contains a '.', the rules above are only
     *            checked for the characters before the first '.' occurred.
     * @throws InvalidParameterException
     *             If the account does not fulfill the requirements describes
     *             above.
     */
    public void setName(String name) {
        if (name == null) {
            this.name = "";
        } else {
            if (!name.isEmpty()) {
                if (name.length() < 3 || name.length() > 16) {
                    throw new InvalidParameterException(
                            "An account name needs to have a minimum length of 3 and a maximum length of 16.");
                } else if (!name.split("\\.")[0].matches("^[a-z]{1}[a-z0-9\\-]{1,14}[a-z0-9]{1}")) {
                    /*
                     * It looks like only values infront of a "." are validated.
                     * Those characters in front of a dot must fullfil the
                     * following rules: The first char needs to be one of "a-z"
                     * while a "-" and "0-9" are allowed for further chars.
                     */
                    throw new InvalidParameterException("The given account name '" + name
                            + "' contains unsupported characters. The first character needs to be one"
                            + " of 'a-z', characters in the middle can be 'a-z', '0,9' and a '-' and the last character of the "
                            + "name has to be one of 'a-z' and '0-9'.");
                }
            }

            this.name = name;
        }
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        return SteemJUtils.transformStringToVarIntByteArray(this.getName());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object otherAccount) {
        if (this == otherAccount)
            return true;
        if (otherAccount == null || !(otherAccount instanceof AccountName))
            return false;
        AccountName other = (AccountName) otherAccount;
        return this.getName().equals(other.getName());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + (this.getName() == null ? 0 : this.getName().hashCode());
        return hashCode;
    }

    /**
     * Returns {@code true} if, and only if, the account name has more than
     * {@code 0} characters.
     *
     * @return {@code true} if the account name has more than {@code 0},
     *         otherwise {@code false}
     */
    public boolean isEmpty() {
        return this.getName().isEmpty();
    }
}

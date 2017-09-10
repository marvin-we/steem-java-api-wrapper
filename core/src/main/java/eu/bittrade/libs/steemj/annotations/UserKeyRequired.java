package eu.bittrade.libs.steemj.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eu.bittrade.libs.steemj.enums.PrivateKeyType;

/**
 * This annotation is used to mark account name fields whose private key is
 * required to sign a transaction.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserKeyRequired {
    /**
     * @return The required private key type.
     */
    PrivateKeyType type();
}

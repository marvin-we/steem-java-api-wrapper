package eu.bittrade.libs.steemj.base.models.operations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;

import eu.bittrade.libs.steemj.enums.OperationType;
import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class represents the Steem "account_create_operation" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class AccountCreateOperation extends AbstractAccountCreateOperation {
    /**
     * Create a new create account operation. Use this operation to create a new
     * account.
     */
    public AccountCreateOperation() {
        super(false);
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedAccountCreateOperation = new ByteArrayOutputStream()) {
            serializedAccountCreateOperation
                    .write(SteemJUtils.transformIntToVarIntByteArray(OperationType.ACCOUNT_CREATE_OPERATION.ordinal()));
            serializedAccountCreateOperation.write(this.getFee().toByteArray());
            serializedAccountCreateOperation.write(this.getCreator().toByteArray());
            serializedAccountCreateOperation.write(this.getNewAccountName().toByteArray());
            serializedAccountCreateOperation.write(this.getOwner().toByteArray());
            serializedAccountCreateOperation.write(this.getActive().toByteArray());
            serializedAccountCreateOperation.write(this.getPosting().toByteArray());
            serializedAccountCreateOperation.write(this.getMemoKey().toByteArray());
            serializedAccountCreateOperation
                    .write(SteemJUtils.transformStringToVarIntByteArray(this.getJsonMetadata()));

            return serializedAccountCreateOperation.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the operation into a byte array.", e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

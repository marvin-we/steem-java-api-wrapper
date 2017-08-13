package eu.bittrade.libs.steemj.communication.dto;

/**
 * A wrapper object for notifications and callbacks.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class NotificationDTO {
    private String method;
    private Object[] params;

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }
}

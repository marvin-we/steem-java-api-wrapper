package eu.bittrade.libs.steemj.base.models;

import java.util.HashMap;
import java.util.Map;

import eu.bittrade.libs.steemj.interfaces.HasJsonAnyGetterSetter;

/**
 * This class is the java implementation of the Steem "checksum_type" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class Checksum extends Ripemd160 {
	private final Map<String, Object> _anyGetterSetterMap = new HashMap<>();
	@Override
	public Map<String, Object> _getter() {
		return _anyGetterSetterMap;
	}

	@Override
	public void _setter(String key, Object value) {
		_getter().put(key, value);
	}

    /** Generated serial version uid. */
    private static final long serialVersionUID = 4420141835144717006L;

    /**
     * @param hashValue
     *            The ripemd160 hash.
     */
    public Checksum(String hashValue) {
        super(hashValue);
    }
}

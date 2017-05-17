package eu.bittrade.libs.steem.api.wrapper.util;

/**
 * This class can be used to generate a new public, private and brain key. It is
 * more or less the Java implementation of the original graphene implementation
 * that can be found <a href=
 * "https://github.com/cryptonomex/graphene/blob/master/libraries/wallet/wallet.cpp">on
 * GitHub</a>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class KeyGenerator {
    private static int BRAIN_KEY_WORD_COUNT = 16;

    
    
    public void suggestBrainKey() {
        System.out.println(BrainkeyDictionaryManager.getInstance().getBrainKeyDictionary().length);
    }
}

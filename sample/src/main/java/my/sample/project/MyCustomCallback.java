package my.sample.project;

import eu.bittrade.libs.steemj.communication.BlockAppliedCallback;
import eu.bittrade.libs.steemj.protocol.SignedBlockHeader;

/**
 * This class implements the {@link BlockAppliedCallback} and can therefore be
 * registered at the {@link eu.bittrade.libs.steemj.communication.CallbackHub}.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public class MyCustomCallback extends BlockAppliedCallback {

    @Override
    public void onNewBlock(SignedBlockHeader signedBlockHeader) {
        System.out.println(signedBlockHeader.toString());
    }

}

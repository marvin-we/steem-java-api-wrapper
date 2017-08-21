package eu.bittrade.libs.steemj;

import eu.bittrade.libs.steemj.base.models.SignedBlockHeader;
import eu.bittrade.libs.steemj.communication.BlockAppliedCallback;

public class MyCustomCallback extends BlockAppliedCallback {

    @Override
    public void onNewBlock(SignedBlockHeader signedBlockHeader) {
        System.out.println(signedBlockHeader.toString());
    }

}

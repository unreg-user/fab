package wta.blocks.blockEntitiesModClasses;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import wta.blocks.BlocksInit;

public class BurdockSaplingBlockEClass extends BlockEntity {
    private boolean canDamage=true;

    public BurdockSaplingBlockEClass(BlockPos pos, BlockState state) {
        super(BlocksInit.burdockSaplingBE, pos, state);
    }

    public boolean canDamage(){
        return canDamage;
    }

    public void setCanDamage(boolean value){
        canDamage=value;
    }
}

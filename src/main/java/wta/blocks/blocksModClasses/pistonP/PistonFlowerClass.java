package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import wta.Fun;
import wta.blocks.BlocksInit;

import static wta.TagsMod.BlockTagsMod.pistonBlocks;
import static wta.TagsMod.BlockTagsMod.pistonBlocksRotate;
import static wta.blocks.PropertiesMod.IN_TREE;

public class PistonFlowerClass extends PistonTreeBlock {
    public PistonFlowerClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(IN_TREE, false)
        );
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        if (!state.get(IN_TREE)){
            return true;
        }else{
            for (int i = 0; i < 6; i++) {
                BlockPos posI=Fun.getBlockByF(pos, Fun.dirs.get(i));
                BlockState stateI=world.getBlockState(posI);
                if (stateI.isIn(pistonBlocksRotate)){
                    Direction rotateI=stateI.get(Properties.FACING);
                    if (Fun.dirs.get(i)==rotateI.getOpposite()){
                        return true;
                    }
                }else if (stateI.getBlock()==BlocksInit.pistonKnot){
                    Boolean[] boolMapI=PistonKnotClass.getBool(stateI);
                    if (boolMapI[Fun.getOppositeDir.get(i)]){ //короче связка
                        return true;
                    }
                }else if (stateI.isIn(pistonBlocks)){
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(IN_TREE);
    }
}

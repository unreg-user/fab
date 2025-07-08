package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import wta.Fun;
import wta.blocks.BlocksInit;

import static wta.blocks.PropertiesMod.NBT_DO;

public class pistonKnotClass extends PistonTreeBlock {

    public pistonKnotClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(NBT_DO.do0, true)
                .with(NBT_DO.do1, true)
                .with(NBT_DO.do2, true)
                .with(NBT_DO.do3, true)
                .with(NBT_DO.do4, true)
                .with(NBT_DO.do5, true)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder
                .add(NBT_DO.do0)
                .add(NBT_DO.do1)
                .add(NBT_DO.do2)
                .add(NBT_DO.do3)
                .add(NBT_DO.do4)
                .add(NBT_DO.do5);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Fun.getKnotHitGen(
                state.get(NBT_DO.do0),
                state.get(NBT_DO.do1),
                state.get(NBT_DO.do2),
                state.get(NBT_DO.do3),
                state.get(NBT_DO.do4),
                state.get(NBT_DO.do5)
        );
    }

    public static BlockState getBlockState(Boolean[] boolMap){
        return BlocksInit.pistonKnot.getDefaultState()
                .with(NBT_DO.do0, boolMap[0])
                .with(NBT_DO.do1, boolMap[1])
                .with(NBT_DO.do2, boolMap[2])
                .with(NBT_DO.do3, boolMap[3])
                .with(NBT_DO.do4, boolMap[4])
                .with(NBT_DO.do5, boolMap[5]);
    }

    protected static Boolean[] getBool(BlockState state){
        Fun.BoolMap boolMap=new Fun.BoolMap(false);
        if (state.get(NBT_DO.do0)) boolMap.set(0);
        if (state.get(NBT_DO.do1)) boolMap.set(1);
        if (state.get(NBT_DO.do2)) boolMap.set(2);
        if (state.get(NBT_DO.do3)) boolMap.set(3);
        if (state.get(NBT_DO.do4)) boolMap.set(4);
        if (state.get(NBT_DO.do5)) boolMap.set(5);
        return boolMap.get();
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        Boolean[] boolMap=getBool(state);
        for (int i = 0; i < 6; i++) {
            if (boolMap[i]){
                BlockPos posI=Fun.getBlockByF(pos, Fun.dirs.get(i));
                BlockState stateI=world.getBlockState(posI);
                Block blockI=stateI.getBlock();
                if (!BlocksInit.pistonBlocks.contains(blockI)) {
                    return false;
                }
            }
        }
        return true;
    }
}

package wta.blocks.blocksModClasses.classes;


import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class RotateBlockDrop extends RotateBlock{
    public RotateBlockDrop(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return canStateAt(state, direction, neighborState, world, pos, neighborPos) ? state : Blocks.AIR.getDefaultState();
    }

    protected boolean canStateAt(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos){
        Direction rotate=state.get(ROTATE);
        BlockPos pos2=pos.offset(rotate.getOpposite());
        return world.getBlockState(pos2).isSideSolidFullSquare(world, pos2, rotate);
    }
}

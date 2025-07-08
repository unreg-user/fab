package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import wta.Fun;
import wta.blocks.BlocksInit;

public class pistonUpStopedClass extends PistonTreeBlock {
    private static DirectionProperty ROTATE= Properties.FACING;
    public pistonUpStopedClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(ROTATE, Direction.UP));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return pistonUpClass.HITBOXES.get(state.get(ROTATE));
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos pos2= Fun.getBlockByF(pos, state.get(ROTATE).getOpposite());
        return BlocksInit.pistonBlocks.contains(world.getBlockState(pos2).getBlock());
    }
}

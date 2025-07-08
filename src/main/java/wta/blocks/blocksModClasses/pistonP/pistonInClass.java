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

import java.util.HashMap;

public class pistonInClass extends PistonTreeBlock {
    private static DirectionProperty ROTATE=Properties.FACING;
    public static HashMap<Direction, VoxelShape> HITBOXES=new HashMap<>(){{
        put(Direction.UP, Block.createCuboidShape(6, 0, 6, 10, 16, 10));
        put(Direction.DOWN, Block.createCuboidShape(6, 0, 6, 10, 16, 10));
        put(Direction.NORTH, Block.createCuboidShape(6, 6, 0, 10, 10, 16));
        put(Direction.SOUTH, Block.createCuboidShape(6, 6, 0, 10, 10, 16));
        put(Direction.EAST, Block.createCuboidShape(0, 6, 6, 16, 10, 10));
        put(Direction.WEST, Block.createCuboidShape(0, 6, 6, 16, 10, 10));

    }};

    public pistonInClass(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBOXES.get(state.get(ROTATE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE);
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        Direction rotate=state.get(ROTATE);
        BlockPos[] pos1={
                Fun.getBlockByF(pos, rotate),
                Fun.getBlockByF(pos, rotate.getOpposite())
        };
        for (BlockPos posI : pos1){
            BlockState stateI=world.getBlockState(posI);
            if (!BlocksInit.pistonBlocks.contains(stateI.getBlock())){
                return false;
            }
        }
        return true;
    }
}

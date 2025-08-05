package wta.mixins.mixinClasses.blocks.blocksVanillaClasses;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.HashMap;

public class StickBlock extends PillarBlock {
    protected static HashMap<Direction.Axis, VoxelShape> HITBOXES=new HashMap<>();

    public StickBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return canStateAt((World) world, pos, state) ? state : Blocks.AIR.getDefaultState();
    }

    protected boolean canStateAt(World world, BlockPos pos, BlockState state){
        Direction.Axis axis=state.get(AXIS);
        Direction direction=Direction.get(Direction.AxisDirection.POSITIVE, axis);
        for (Direction dirI : new Direction[]{direction, direction.getOpposite()}){
            if (tryDirection(world, pos, state, dirI)){
                return true;
            }
        }
        return false;
    }

    protected boolean tryDirection(World world, BlockPos pos, BlockState state, Direction direction){
        BlockPos pos2=pos.offset(direction);
        BlockState state2=world.getBlockState(pos2);
        if (state2.isSideSolidFullSquare(world, pos2, direction.getOpposite())){
            return true;
        }else if (state.equals(state2)){
            return tryDirection(world, pos2, state2, direction);
        }else{
            return false;
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canStateAt((World) world, pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBOXES.get(state.get(AXIS));
    }

    static {
        HITBOXES.put(Direction.Axis.Y,
                createCuboidShape(7, 0, 7,
                        9, 16, 9));
        HITBOXES.put(Direction.Axis.X,
                createCuboidShape(0, 7, 7,
                        16, 9, 9));
        HITBOXES.put(Direction.Axis.Z,
                createCuboidShape(7, 7, 0,
                        9, 9, 16));
    }
}

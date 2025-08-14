package i_zachem_ya_tratil_na_eto_vremya;

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
import wta.Fun;

import java.util.HashMap;

public class pistonKnot0Class extends PistonTreeBlock {
    private static DirectionProperty ROTATE=Properties.FACING;
    //private static DirectionProperty H_ROTATE=DirectionProperty.of("h_facing", new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
    public static HashMap<Direction, VoxelShape> HITBOXES=new HashMap<>(){{
        put(Direction.UP, Fun.getKnotHitGen(true, true, false, true, true, true));
        put(Direction.DOWN, Fun.getKnotHitGen(true, true, true, false, true, true));
        put(Direction.EAST, Fun.getKnotHitGen(false, true, true, true, true, true));
        put(Direction.WEST, Fun.getKnotHitGen(true, false, true, true, true, true));
        put(Direction.SOUTH, Fun.getKnotHitGen(true, true, true, true, false, true));
        put(Direction.NORTH, Fun.getKnotHitGen(true, true, true, true, true, false));
    }};

    public pistonKnot0Class(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(ROTATE, Direction.UP));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBOXES.get(state.get(ROTATE));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE);
    }
}

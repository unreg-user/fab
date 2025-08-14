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

import java.util.Arrays;
import java.util.HashMap;

public class pistonKnot2Class extends PistonTreeBlock {
    public static DirectionProperty ROTATE=Properties.FACING;
    public static DirectionProperty H_ROTATE=DirectionProperty.of("h_facing", new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST});
    public static HashMap<Direction, HashMap<Direction, Boolean[]>> HITBOXES_RETS=new HashMap<>();
    public static HashMap<Direction, HashMap<Direction, VoxelShape>> HITBOXES=new HashMap<>();

    public pistonKnot2Class(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(ROTATE, Direction.UP).with(H_ROTATE, Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBOXES.get(state.get(ROTATE)).get(state.get(H_ROTATE));
    }

    private static void hitboxGen(){
        for (Direction rotate : Fun.dirs) {
            HITBOXES.put(rotate, new HashMap<>());
            HITBOXES_RETS.put(rotate, new HashMap<>());
            for (Direction rotate2 : Fun.h_dirs) {
                Boolean[] rets = {null, null, null, null, null, null};
                switch (rotate) {
                    case EAST:
                        rets[0] = false;
                        rets[1] = true;
                        break;
                    case WEST:
                        rets[1] = false;
                        rets[0] = true;
                        break;
                    case UP:
                        rets[2] = false;
                        rets[3] = true;
                        break;
                    case DOWN:
                        rets[3] = false;
                        rets[2] = true;
                        break;
                    case SOUTH:
                        rets[4] = false;
                        rets[5] = true;
                        break;
                    case NORTH:
                        rets[5] = false;
                        rets[4] = true;
                        break;
                }
                //set1
                int nextDir = Fun.h_dirs.indexOf(rotate2);
                int setDir = Fun.getIndexIndex(Arrays.stream(rets).toList(), null, nextDir);
                rets[setDir] = true;
                //set2
                setDir = Fun.getIndexIndex(Arrays.stream(rets).toList(), null, nextDir);
                rets[setDir] = true;

                for (int i = 0; i < rets.length; i++) {
                    if (rets[i] == null) {
                        rets[i] = false;
                    }
                }
                HITBOXES.get(rotate).put(rotate2, Fun.getKnotHitGen(
                        rets[0],
                        rets[1],
                        rets[2],
                        rets[3],
                        rets[4],
                        rets[5]
                ));
                HITBOXES_RETS.get(rotate).put(rotate2, rets);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE).add(H_ROTATE);
    }

    static{
        hitboxGen();
    }
}

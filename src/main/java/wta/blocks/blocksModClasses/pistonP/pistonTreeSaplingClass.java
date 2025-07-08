package wta.blocks.blocksModClasses.pistonP;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import wta.Fun;
import wta.blocks.BlocksInit;

import java.util.HashMap;

import static wta.blocks.PropertiesMod.START;

public class pistonTreeSaplingClass extends PistonTreeBlock {
    private static DirectionProperty ROTATE=Properties.FACING;
    public static HashMap<Direction, VoxelShape> HITBOXES=new HashMap<>();

    public pistonTreeSaplingClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(ROTATE, Direction.UP).with(START, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE).add(START);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        try {
            BlockPos pos2=Fun.getBlockByF(pos, state.get(ROTATE));
            if (!state.get(START) && random.nextInt(2)==0){
                if (world.getBlockState(pos2).getBlock()==Blocks.AIR){
                    world.setBlockState(pos2, BlocksInit.pistonUp.getDefaultState().with(ROTATE, state.get(ROTATE)), 3);
                    world.setBlockState(pos, state.with(START, true), 3);
                }
            }
        } catch (Exception e) {}
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(ROTATE, ctx.getSide());
    }

    @Override
    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(START)){
            BlockPos pos2=Fun.getBlockByF(pos, state.get(ROTATE));
            return BlocksInit.pistonBlocksForSet.contains(world.getBlockState(pos2).getBlock());
        }else{
            return true;
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(START)){
            return HITBOXES.get(state.get(ROTATE));
        }else{
            return VoxelShapes.fullCube();
        }
    }

    static{
        for (int i = 0; i < 6; i++) {
            Direction dirI=Fun.dirs.get(i);
            HITBOXES.put(dirI,
                    VoxelShapes.union(
                            Fun.HalfBlockGen(dirI, 4, 3),
                            pistonInClass.HITBOXES.get(dirI)
                    )
            );
        }
    }
}

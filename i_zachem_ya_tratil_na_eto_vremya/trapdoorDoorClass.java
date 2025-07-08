package wta.blocks;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import wta.Fun;
import wta.blocks.blocksModClasses.pistonP.PistonTreeBlock;

import java.util.HashMap;

import static wta.blocks.PropertiesMod.*;

public class trapdoorDoorClass extends Block{
    private static BooleanProperty OPEN=Properties.OPEN;
    private static EnumProperty<BlockHalf> HALF =Properties.BLOCK_HALF;
    public static HashMap<Direction, VoxelShape> HITBOXES;
    public trapdoorDoorClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(H_ROTATE, Direction.NORTH)
                .with(OPEN, false)
                .with(TRAP, true)
                .with(HALF, BlockHalf.TOP)
        );
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(TRAP)){
            if (!state.get(OPEN)){
                if (state.get(HALF)==BlockHalf.TOP){
                    return HITBOXES.get(Direction.UP);
                }else{
                    return HITBOXES.get(Direction.DOWN);
                }
            }else{
                return HITBOXES.get(state.get(H_ROTATE));
            }
        }else{
            if (state.get(OPEN)) {
                return HITBOXES.get(state.get(H_ROTATE).rotateYClockwise());
            }else{
                return HITBOXES.get(state.get(H_ROTATE));
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder
                .add(H_ROTATE)
                .add(OPEN)
                .add(TRAP)
                .add(HALF);
    }

    private void setToDoor(BlockPos posDOWN, BlockPos posUP, BlockState stateONE, World world){
        BlockState newState=stateONE
                .with(OPEN, false)
                .with(TRAP, false)
                .with(HALF, BlockHalf.TOP);
        world.setBlockState(posUP,  newState, 3);
        world.setBlockState(posDOWN, newState.with(HALF, BlockHalf.BOTTOM), 3);
    }

    private void setToTrapdoors(BlockPos posDOWN, BlockPos posUP, BlockState stateONE, World world){
        Direction newRotate=stateONE.get(H_ROTATE);
        if (stateONE.get(OPEN)) {
            newRotate=newRotate.rotateYClockwise();
        }
        BlockState newState=this.getDefaultState()
                .with(H_ROTATE, newRotate)
                .with(OPEN, true)
                .with(TRAP, true)
                .with(HALF, BlockHalf.TOP);
        world.setBlockState(posUP, newState, 3);
        world.setBlockState(posDOWN, newState.with(HALF, BlockHalf.BOTTOM), 3);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.isSneaking()){ //shift=true
            if (state.get(TRAP)){
                if (state.get(OPEN)) {
                    if (state.get(HALF) == BlockHalf.TOP) {
                        BlockState state2H = world.getBlockState(pos.down(1));
                        BlockState stateDOWN = world.getBlockState(pos.down(2));
                        if (state2H.getBlock() == this && stateDOWN.isSideSolidFullSquare(world, pos.down(2), Direction.UP)) {
                            if (state2H.get(HALF) == BlockHalf.BOTTOM
                                    && state2H.get(H_ROTATE) == state.get(H_ROTATE)
                                    && state2H.get(TRAP)
                                    && state2H.get(OPEN)) {
                                this.setToDoor(pos.down(), pos, state, world);
                            }
                        }
                    } else {
                        BlockState state2H = world.getBlockState(pos.up(1));
                        BlockState stateDOWN = world.getBlockState(pos.down(1));
                        if (state2H.getBlock() == this && stateDOWN.isSideSolidFullSquare(world, pos.down(2), Direction.UP)) {
                            if (state2H.get(HALF) == BlockHalf.TOP
                                    && state2H.get(H_ROTATE) == state.get(H_ROTATE)
                                    && state2H.get(TRAP)
                                    && state2H.get(OPEN)) {
                                this.setToDoor(pos, pos.up(), state, world);
                            }
                        }
                    }
                }
            }else{
                BlockHalf half=state.get(HALF);
                BlockPos pos2H=pos.down(Fun.HalfToInt.get(half));
                BlockState state2H=world.getBlockState(pos2H);
                if (state.with(HALF, Fun.HalfOpposite.get(half)).equals(state2H)){
                    if (half==BlockHalf.TOP){
                        this.setToTrapdoors(pos2H, pos, state, world);
                    }else{
                        this.setToTrapdoors(pos, pos2H, state, world);
                    }
                }
            }
        }else{ //shift=false
            BlockState newState=state.with(OPEN, !state.get(OPEN));
            world.setBlockState(pos, newState, 3);
            if (!state.get(TRAP)){
                BlockHalf half=state.get(HALF);
                world.setBlockState(pos.down(Fun.HalfToInt.get(half)), newState.with(HALF, Fun.HalfOpposite.get(half)), 3);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction direction = ctx.getSide();
        if (!ctx.canReplaceExisting() && direction.getAxis().isHorizontal()) {
            blockState = (BlockState) ((BlockState) blockState.with(H_ROTATE, direction.getOpposite())).with(HALF, ctx.getHitPos().y - (double) ctx.getBlockPos().getY() > (double) 0.5F ? BlockHalf.TOP : BlockHalf.BOTTOM);
        } else {
            blockState = (BlockState) ((BlockState) blockState.with(H_ROTATE, ctx.getHorizontalPlayerFacing())).with(HALF, direction == Direction.UP ? BlockHalf.BOTTOM : BlockHalf.TOP);
        }
        return blockState.with(TRAP, true).with(OPEN, false);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!canStateAt(state, world, pos)){
            //setToTrapdoors();
        }
        return state;
    }

    protected boolean canStateAt(BlockState state, WorldView world, BlockPos pos) {
        if (!state.get(TRAP) && state.get(HALF)==BlockHalf.BOTTOM){
            if (!world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP)){
                return false;
            }
        }
        return true;
    }

    static{
        HITBOXES=new HashMap<>();
        for (Direction dirI : Fun.dirs){
            HITBOXES.put(dirI, Fun.HalfBlockGen(dirI.getOpposite(), 1, 3));
        }
    }
}

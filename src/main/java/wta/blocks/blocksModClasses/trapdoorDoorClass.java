package wta.blocks.blocksModClasses;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import wta.Fun;
import wta.blocks.PropertiesMod;

import java.util.HashMap;

public class trapdoorDoorClass extends Block{
    private static BooleanProperty OPEN=Properties.OPEN;
    private static EnumProperty<BlockHalf> HALF=Properties.BLOCK_HALF;
    private static BooleanProperty TRAP= PropertiesMod.TRAP;
    private static DirectionProperty H_ROTATE=Properties.HORIZONTAL_FACING;
    private static BooleanProperty STATIC=PropertiesMod.STATIC;
    public static HashMap<Direction, VoxelShape> HITBOXES;
    private BlockSetType sound_type;
    public trapdoorDoorClass(BlockSetType type, Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(H_ROTATE, Direction.NORTH)
                .with(OPEN, false)
                .with(TRAP, true)
                .with(HALF, BlockHalf.TOP)
                .with(STATIC, false)
        );
        sound_type=type;
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
                .add(HALF)
                .add(STATIC);
    }

    private BlockState convToDoor(BlockState state){
        return state
                .with(OPEN, false)
                .with(TRAP, false);
    }

    private BlockState convToTrapdoor(BlockState state){
        Direction rotate=state.get(H_ROTATE);
        if (state.get(OPEN)) {
            rotate=rotate.rotateYClockwise();
        }
        return state
                .with(H_ROTATE, rotate)
                .with(TRAP, true)
                .with(OPEN, true);
    }

    protected void playDoor(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? sound_type.doorOpen() : sound_type.doorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    protected void playTrapdoor(@Nullable PlayerEntity player, World world, BlockPos pos, boolean open) {
        world.playSound(player, pos, open ? sound_type.trapdoorOpen() : sound_type.trapdoorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
    }

    protected void playUnstatic(@Nullable PlayerEntity player, World world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, GameEvent.ITEM_INTERACT_FINISH, pos);
    }

    protected void playStatic(@Nullable PlayerEntity player, World world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.BLOCK_CHAIN_PLACE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, GameEvent.ITEM_INTERACT_FINISH, pos);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.isSneaking()){ //shift=true
            if (!state.get(STATIC)) {
                BlockHalf half = state.get(HALF);
                BlockPos pos2H = pos.down(Fun.halfToInt.get(half));
                BlockState state2H = world.getBlockState(pos2H);
                if (Fun.equalsBlockStateGroup(state.with(HALF, Fun.halfOpposite.get(half)), state2H)) {
                    if (state.get(TRAP)) {
                        if (state.get(OPEN) && state2H.get(OPEN)) {
                            world.setBlockState(pos, this.convToDoor(state), 3);
                            world.setBlockState(pos2H, this.convToDoor(state2H), 3);
                            playTrapdoor(player, world, pos, true);
                        }
                    } else {
                        world.setBlockState(pos, this.convToTrapdoor(state), 3);
                        world.setBlockState(pos2H, this.convToTrapdoor(state2H), 3);
                        playTrapdoor(player, world, pos, true);
                    }
                }
            }else{
                return ActionResult.PASS;
            }
        }else{ //shift=false
            BlockState newState=state.cycle(OPEN);
            boolean open=newState.get(OPEN);
            world.setBlockState(pos, newState, 3);
            if (!state.get(TRAP)){
                BlockHalf half=state.get(HALF);
                BlockPos pos2H=pos.down(Fun.halfToInt.get(half));
                BlockState newState2H=world.getBlockState(pos2H).cycle(OPEN);
                world.setBlockState(pos2H, newState2H, 3);
                playDoor(player, world, pos, open);
            }else{
                playTrapdoor(player, world, pos, open);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item=stack.getItem();
        boolean static_=state.get(STATIC);
        BlockHalf half = state.get(HALF);
        BlockPos pos2H = pos.down(Fun.halfToInt.get(half));
        BlockState state2H = world.getBlockState(pos2H);
        if (item==Items.IRON_NUGGET && !static_){
            world.setBlockState(pos, state.with(STATIC, true));
            if (!state.get(TRAP)){
                world.setBlockState(pos2H, state2H.with(STATIC, true), 3);
            }
            stack.decrementUnlessCreative(1, player);
            playStatic(player, world, pos);
            return ItemActionResult.CONSUME;
        } else if (stack.isIn(ItemTags.AXES) && static_) {
            world.setBlockState(pos, state.with(STATIC, false));
            if (!state.get(TRAP)){
                world.setBlockState(pos2H, state2H.with(STATIC, false), 3);
            }
            stack.damage(2, player, Fun.getSlotHand.apply(hand));
            playUnstatic(player, world, pos);
            return ItemActionResult.CONSUME;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        //FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction direction = ctx.getSide();
        if (!ctx.canReplaceExisting() && direction.getAxis().isHorizontal()) {
            blockState = (BlockState) ((BlockState) blockState.with(H_ROTATE, direction.getOpposite())).with(HALF, ctx.getHitPos().y - (double) ctx.getBlockPos().getY() > (double) 0.5F ? BlockHalf.TOP : BlockHalf.BOTTOM);
        } else {
            blockState = (BlockState) ((BlockState) blockState.with(H_ROTATE, ctx.getHorizontalPlayerFacing())).with(HALF, direction == Direction.UP ? BlockHalf.BOTTOM : BlockHalf.TOP);
        }
        return blockState
                .with(TRAP, true)
                .with(OPEN, false)
                .with(STATIC, false);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.get(TRAP)) {
            if (!(neighborState.getBlock() instanceof trapdoorDoorClass)) {
                if (!canStateAtDoor(state, world, pos)) {
                    return convToTrapdoor(state);
                }
            }
            if (neighborState.getBlock() == Blocks.CRAFTING_TABLE && !state.get(STATIC)){
                return convToTrapdoor(state);
            }
        }
        return state;
    }

    protected boolean canStateAtDoor(BlockState state, WorldView world, BlockPos pos) {
        BlockHalf half=state.get(HALF);
        BlockHalf half2=Fun.halfOpposite.get(half);
        BlockPos pos2H=pos.up(Fun.halfToInt.get(half2));
        BlockState state2H=world.getBlockState(pos2H);
        if (!Fun.equalsBlockStateGroup(state.with(HALF, half2), state2H)){
            return false;
        }
        if (half==BlockHalf.BOTTOM
            && !world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP)){
            return false;
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

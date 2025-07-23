package wta.blocks.blocksModClasses.stick_detectors;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import wta.Fun;

import java.util.HashMap;

public class StickDetectorClass extends StickDetectorBlock {
    public static final MapCodec<StickDetectorClass> CODEC=createCodec(StickDetectorClass::new);
    private static final HashMap<Direction, VoxelShape> HITBPXES;
    private static final DirectionProperty ROTATE=Properties.FACING;
    private static final int stopTicks=3;

    public StickDetectorClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(ROTATE, Direction.UP)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ROTATE);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item=stack.getItem();
        if (item==Items.STICK){
            world.setBlockState(pos, state.with(POWER, getStickPower(stack, rand)), 3);
            updateNeighbors(world, pos, state);
            world.scheduleBlockTick(pos, this, stopTicks);
            playStick(world, player, pos);
            return ItemActionResult.SUCCESS;
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void updateNeighbors(World world, BlockPos pos, BlockState state) {
        world.updateNeighborsAlways(pos, this);
        world.updateNeighborsAlways(pos.offset(state.get(ROTATE).getOpposite()), this);
    }

    private void playStick(World world, PlayerEntity player, BlockPos pos){
        world.playSound(player, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS);
        world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBPXES.get(state.get(ROTATE));
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        updateNeighbors(world, pos, state);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state=this.getDefaultState();
        Direction dir=ctx.getSide();
        BlockPos pos2=ctx.getBlockPos().offset(dir.getOpposite());
        World world=ctx.getWorld();
        BlockState state2=world.getBlockState(pos2);
        if (state2.isSideSolidFullSquare(world, pos2, dir)){
            return state.with(ROTATE, dir);
        }else{
            return state.with(ROTATE, Direction.UP);
        }
    }

    @Override
    protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return state.get(ROTATE)==direction ? getStickRedstonePower(state.get(POWER)) : 0;
    }

    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return getStickRedstonePower(state.get(POWER));
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction rotate=state.get(ROTATE);
        BlockPos pos2=pos.offset(rotate.getOpposite());
        BlockState state2=world.getBlockState(pos2);
        if (state2.getBlock()==this && state.get(ROTATE)!=state2.get(ROTATE)){
            return true;
        }else{
            return state2.isSideSolidFullSquare(world, pos2, rotate);
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return canStateAt(state, world, pos) ? state : Blocks.AIR.getDefaultState();
    }

    protected boolean canStateAt(BlockState state, WorldAccess world, BlockPos pos){
        Direction rotate=state.get(ROTATE);
        BlockPos pos2=pos.offset(rotate.getOpposite());
        return world.getBlockState(pos2).isSideSolidFullSquare(world, pos2, rotate);
    }

    static {
        VoxelShape hitbox=VoxelShapes.union(
                Fun.HalfBlockGen(Direction.UP, 0.5F, 3),
                Block.createCuboidShape(0, 0, 0, 16, 3, 2),
                Block.createCuboidShape(0, 0, 0, 2, 3, 16),
                Block.createCuboidShape(14, 0, 0, 16, 3, 16),
                Block.createCuboidShape(0, 0, 14, 16, 3, 16)
        );
        HITBPXES=Fun.VoxelShapeR.rotateMap(Direction.UP, hitbox);
    }
}

package wta.blocks.blocksModClasses;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
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
import net.minecraft.world.event.GameEvent;
import wta.Fun;

import java.util.HashMap;

public class StickDetectorClass extends FacingBlock {
    public static final MapCodec<StickDetectorClass> CODEC=createCodec(StickDetectorClass::new);
    private static final DirectionProperty ROTATE=Properties.FACING;
    private static final BooleanProperty POWERED=Properties.POWERED;
    private static final HashMap<Direction, VoxelShape> HITBPXES;
    private static final int stopTicks=5;

    public StickDetectorClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(ROTATE, Direction.UP)
                .with(POWERED, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ROTATE, POWERED);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        Item item=stack.getItem();
        if (item==Items.STICK){
            world.setBlockState(pos, state.with(POWERED, true), 3);
            world.updateNeighborsAlways(pos, this);
            world.updateNeighborsAlways(pos.offset(state.get(ROTATE).getOpposite()), this);
            world.scheduleBlockTick(pos, this, stopTicks);
        }
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    private void playStick(World world, PlayerEntity player, BlockPos pos){
        world.playSound(player, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS);
        world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(POWERED, false), 3);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBPXES.get(state.get(ROTATE));
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
                /*/new HashMap<>(){{
            put(Direction.EAST, (new Fun.VoxelShapeR(hitbox).rotate(1, Direction.UP, Direction.EAST).getShape()));
            put(Direction.WEST, Fun.HalfBlockGen(Direction.WEST, 0.5F, 3));
            put(Direction.UP, (new Fun.VoxelShapeR(hitbox).mirror(Direction.Axis.Y).getShape()));
            put(Direction.DOWN, (new Fun.VoxelShapeR(hitbox).mirror(Direction.Axis.Y).getShape()));
            put(Direction.SOUTH, (new Fun.VoxelShapeR(hitbox).rotate(1, Direction.UP, Direction.SOUTH).getShape()));
            put(Direction.NORTH, (new Fun.VoxelShapeR(hitbox).rotate(1, Direction.UP, Direction.NORTH).getShape()));
        }};/*/
    }
}

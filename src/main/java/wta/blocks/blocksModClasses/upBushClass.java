package wta.blocks.blocksModClasses;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.GameEvent.Emitter;
import wta.blocks.BlocksInit;

public class upBushClass extends PlantBlock implements Fertilizable {
    public static final MapCodec<upBushClass> CODEC = createCodec(upBushClass::new);
    //private static final float MIN_MOVEMENT_FOR_DAMAGE = 0.003F;
    //public static final int MAX_AGE = 3;
    public static final IntProperty AGE;
    private static final VoxelShape SMALL_SHAPE;
    private static final VoxelShape LARGE_SHAPE;

    public MapCodec<upBushClass> getCodec() {
        return CODEC;
    }

    public upBushClass(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(AGE, 0));
    }

    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(Items.SWEET_BERRIES);
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if ((Integer)state.get(AGE) == 0) {
            return SMALL_SHAPE;
        } else {
            return (Integer)state.get(AGE) < 3 ? LARGE_SHAPE : super.getOutlineShape(state, world, pos, context);
        }
    }

    protected boolean hasRandomTicks(BlockState state) {
        return (Integer)state.get(AGE) < 3;
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = (Integer)state.get(AGE);
        if (i < 3 && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.down(), 0) > 0) {
            BlockState blockState = (BlockState)state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, Emitter.of(blockState));
        }

    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity lentity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.slowMovement(state, new Vec3d((double)0.2F, (double)0.1F, (double)0.2F));
            if (!world.isClient && (Integer)state.get(AGE) > 0 && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                double d = Math.abs(entity.getX() - entity.lastRenderX);
                double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                //if (d >= (double)0.003F || e >= (double)0.003F) {
                if (lentity.getLastAttackedTime() < lentity.age - 20) {
                    lentity.damage(world.getDamageSources().sweetBerryBush(), 1.0F);
                    //lentity.setDa(lentity.age); // обновляем время последнего урона
                }
                //}
            }

        }
    }

    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = (Integer)state.get(AGE);
        boolean bl = i == 3;
        return !bl && stack.isOf(Items.BONE_MEAL) ? ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int i = (Integer)state.get(AGE);
        boolean bl = i == 3;
        if (i > 1) {
            int j = 1 + world.random.nextInt(2);
            dropStack(world, pos, new ItemStack(BlocksInit.usbbI, j + (bl ? 1 : 0)));
            world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            BlockState blockState = (BlockState)state.with(AGE, 1);
            world.setBlockState(pos, blockState, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, Emitter.of(player, blockState));
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{AGE});
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return (Integer)state.get(AGE) < 3;
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int i = Math.min(3, (Integer)state.get(AGE) + 1);
        world.setBlockState(pos, (BlockState)state.with(AGE, i), 2);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    static {
        AGE = Properties.AGE_3;
        SMALL_SHAPE = Block.createCuboidShape(
                (double)3.0F,
                (double)8.0F,
                (double)3.0F,
                (double)13.0F,
                (double)16.0F,
                (double)13.0F);
        LARGE_SHAPE = Block.createCuboidShape(
                (double)1.0F,
                (double)0.0F,
                (double)1.0F,
                (double)15.0F,
                (double)16.0F,
                (double)15.0F);
    }
}


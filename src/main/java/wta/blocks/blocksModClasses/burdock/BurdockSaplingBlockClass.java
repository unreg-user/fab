package wta.blocks.blocksModClasses.burdock;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import wta.blocks.blockEntitiesModClasses.BurdockSaplingBlockEClass;
import wta.entities.projectiles.ProjectilesInit;
import wta.entities.projectiles.burdock.BurdockEntity;

import static wta.Fab.MODID;

public class BurdockSaplingBlockClass extends PlantBlock implements Fertilizable, BlockEntityProvider {
    private static final IntProperty AGE = Properties.AGE_4;
    private static final int damageTime = 10;
    private static final Identifier tree;

    private final VoxelShape[] HITBOXES = {
            VoxelShapes.empty(),
            Block.createCuboidShape(7, 0, 7, 9, 1, 9),
            Block.createCuboidShape(1, 0, 1, 15, 10, 15),
            VoxelShapes.fullCube(),
            VoxelShapes.fullCube()
    };

    public BurdockSaplingBlockClass(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState())
                .with(AGE, 0)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return HITBOXES[state.get(AGE)];
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextBoolean() && world.getLightLevel(pos.up()) >= 9) {
            int age=state.get(AGE);
            if (age<4){
                world.setBlockState(pos, state.with(AGE, state.get(AGE)+1), 3);
            }else{
                if (random.nextBoolean()){
                    spawnTree(world, pos);
                }
            }
        }
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return null;
    }

    public void spawnTree(World world, BlockPos pos) {
        if (world.isClient) return;

        ConfiguredFeature<?, ?> treeF=world.getRegistryManager()
                .get(RegistryKeys.CONFIGURED_FEATURE)
                .get(tree);

        treeF.generate(
                (StructureWorldAccess) world,
                ((ServerWorld) world).getChunkManager().getChunkGenerator(),
                world.random,
                pos
        );
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        if (world.getLightLevel(pos.up()) >= 9){
            if (state.get(AGE)<4){
                return true;
            }else{
                return random.nextInt(3)==0; //4
            }
        }
        return false;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int age=state.get(AGE);
        if (age<4){
            world.setBlockState(
                    pos,
                    this.getDefaultState()
                            .with(AGE, Math.min(age+random.nextInt(2)+1, 3)),
                    3
            );
        }else{
            spawnTree(world, pos);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.get(AGE)==4){
            world.setBlockState(pos, state, 3);
            dropStack(world, pos, new ItemStack(ProjectilesInit.burdockI, world.random.nextInt(5)+1));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            BlockState state2=state.with(AGE, 3);
            world.setBlockState(pos, state2, 2);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, state2));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        BlockEntity be_=world.getBlockEntity(pos);
        if (!(be_ instanceof  BurdockSaplingBlockEClass be)) return;
        if (be.canDamage()){
            int age = state.get(AGE);
            if (entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE && age > 1) {
                entity.slowMovement(state, new Vec3d(0.9F, 0.85F, 0.9F));
            }
            if (entity instanceof LivingEntity livingEntity && age == 4) {
                BurdockEntity.damageEntityEffect(world, livingEntity, Random.create());
                world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
                world.emitGameEvent(GameEvent.ENTITY_DAMAGE, pos, GameEvent.Emitter.of(livingEntity));
                be.setCanDamage(false);
                world.scheduleBlockTick(pos, this, damageTime);
                if (world.random.nextInt(3) == 0) {
                    BlockState state2 = state.with(AGE, 3);
                    world.setBlockState(pos, state2, 3);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(livingEntity, state2));
                }
            }
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockEntity be_=world.getBlockEntity(pos);
        if (!(be_ instanceof  BurdockSaplingBlockEClass be)) return;
        be.setCanDamage(true);
    }

    @Override
    public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
        if (state.get(AGE)==4){
            burdockExploid((World) world, pos);
        }
    }

    public static void burdockExploid(World world, BlockPos pos, int min, int max){
        Random random=Random.create();
        int count=min+random.nextInt(max-min+1);
        for (int i=0; i<count; i++) {
            BurdockEntity.summonRandomBurdock(
                    world,
                    new Vec3d(
                            pos.getX(),
                            pos.getY(),
                            pos.getZ()
                    ),
                    1,
                    random
            );
        }
    }
    public static void burdockExploid(World world, BlockPos pos){
        burdockExploid(world, pos, 3, 7);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BurdockSaplingBlockEClass(pos, state);
    }

    static {
        tree=Identifier.of(MODID, "burdock_tree");
    }
}

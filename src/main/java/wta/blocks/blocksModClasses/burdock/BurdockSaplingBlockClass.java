package wta.blocks.blocksModClasses.burdock;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import wta.entities.projectiles.ProjectilesInit;

public class BurdockSaplingBlockClass extends PlantBlock implements Fertilizable {
    private static IntProperty AGE=Properties.AGE_4;
    private VoxelShape[] HITBOXES={
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
        if (random.nextBoolean()){
            world.setBlockState(pos, state.with(AGE, Math.min(state.get(AGE)+1, 4)), 3);
        }
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return null;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        if (state.get(AGE)<4){
            return true;
        }else{
            return random.nextInt(4)==0;
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int age=state.get(AGE);
        if (age<4){
            world.setBlockState(
                    pos,
                    this.getDefaultState()
                            .with(AGE, Math.min(age+random.nextInt(2)+1, 3)),
                    4
            );
        }else{

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
}

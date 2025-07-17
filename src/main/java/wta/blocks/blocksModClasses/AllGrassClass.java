package wta.blocks.blocksModClasses;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import wta.blocks.BlocksInit;

import static wta.TagsMod.BlockTagsMod;

public class AllGrassClass extends Block implements Fertilizable {
    public AllGrassClass(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        fillDirections(pos, world, random);
        filling:
        for(int i = 0; i < 128; ++i) {
            BlockPos pos2=pos;
            for (int j = 0; j < i / 16; ++j) {
                pos2=pos2.add(
                        random.nextInt(3) - 1,
                        random.nextInt(3) - 1,
                        random.nextInt(3) - 1
                );
                BlockState state2=world.getBlockState(pos2);
                if (!state2.isIn(BlockTags.DIRT)){
                    continue filling;
                }
            }
            BlockState state2=world.getBlockState(pos2);
            if (state2.getBlock() instanceof AllGrassClass all_grass) {
                if (random.nextInt(100)==0){
                    all_grass.grow(world, random, pos, state);
                }
            }else{
                world.setBlockState(pos2, this.getDefaultState(), 3);
            }
            fillDirections(pos2, world, random);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos pos2=pos.add(
                random.nextInt(3) - 1,
                random.nextInt(3) - 1,
                random.nextInt(3) - 1
        );
        BlockState state2=world.getBlockState(pos2);
        if (state2.isIn(BlockTags.DIRT) && state2.getBlock()!=this){
            world.setBlockState(pos2, this.getDefaultState(), 3);
        }
    }

    private void fillDirections(BlockPos pos, ServerWorld world, Random random){
        for (Direction dirI : DIRECTIONS) {
            if (random.nextBoolean()) {
                BlockPos posI = pos.offset(dirI);
                BlockState stateI = world.getBlockState(posI);
                if (stateI.getBlock() == Blocks.AIR) {
                    if (dirI == Direction.DOWN && random.nextBoolean()) {
                        world.setBlockState(posI, BlocksInit.usbb.getDefaultState().with(Properties.AGE_3, random.nextInt(4)), 3);
                    } else {
                        world.setBlockState(posI, BlocksInit.shSeeds.getDefaultState().with(Properties.FACING, dirI).with(Properties.AGE_2, random.nextInt(3)), 3);
                    }
                } else if (stateI.isIn(BlockTagsMod.allGrass)) {
                    return;
                } else if (stateI.getBlock() instanceof Fertilizable fertilizable) {
                    if (fertilizable.getFertilizableType() == FertilizableType.GROWER && fertilizable.canGrow(world, random, posI, stateI)) {
                        fertilizable.grow(world, random, posI, stateI);
                    }
                }
            }
        }
    }

    public Fertilizable.FertilizableType getFertilizableType() {
        return FertilizableType.NEIGHBOR_SPREADER;
    }
}

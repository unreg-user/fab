package wta.features.featuresClasses;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import wta.Fun;
import wta.features.configures.BallTreeFeatureConfig;

import java.util.ArrayList;
import java.util.stream.Stream;

public class BallTreeFeature extends Feature<BallTreeFeatureConfig> {
    private static final ArrayList<BlockPos> logX;
    private static final ArrayList<BlockPos> logY;
    private static final ArrayList<BlockPos> logZ;
    private static final ArrayList<BlockPos> logs;
    private static final ArrayList<BlockPos> leaves;

    public BallTreeFeature(Codec<BallTreeFeatureConfig> configCodec) {
        super(configCodec);
    }

    private boolean canGenerate(World world, BlockPos pos, int rotate){
        if (!world.getBlockState(pos.down()).isIn(BlockTags.DIRT)){
            return false;
        }
        for (BlockPos i : logs){
            BlockPos posI=pos.add( new Fun.BlockPosR(i).horizontalRotate(rotate) );
            BlockState state=world.getBlockState(posI);
            if (!(
                    state.isIn(BlockTags.AIR) ||
                    state.isIn(BlockTags.SAPLINGS) ||
                    state.isIn(BlockTags.LEAVES)
            )){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean generate(FeatureContext<BallTreeFeatureConfig> context) {
        World world=(World) context.getWorld();
        BlockPos pos=context.getOrigin();
        BallTreeFeatureConfig config=context.getConfig();
        Block log=Fun.getBlockById( config.log() );
        Block leaves=Fun.getBlockById( config.leaves() );
        Random random=context.getRandom();
        int rotate=random.nextInt(4);
        if (!canGenerate(world, pos, rotate)){
            return false;
        }

        BlockState stateI;
        stateI=log.getDefaultState().with(Properties.AXIS, Direction.Axis.X);
        for (BlockPos i : logX){
            setBlockState(world, pos.add( new Fun.BlockPosR(i).horizontalRotate(rotate) ), stateI);
        }
        stateI=log.getDefaultState().with(Properties.AXIS, Direction.Axis.Y);
        for (BlockPos i : logY){
            setBlockState(world, pos.add( new Fun.BlockPosR(i).horizontalRotate(rotate) ), stateI);
        }
        stateI=log.getDefaultState().with(Properties.AXIS, Direction.Axis.Z);
        for (BlockPos i : logZ){
            setBlockState(world, pos.add( new Fun.BlockPosR(i).horizontalRotate(rotate) ), stateI);
        }
        stateI=leaves.getDefaultState();
        for (BlockPos i : this.leaves) {
            BlockPos posI=pos.add(new Fun.BlockPosR(i).horizontalRotate(rotate));
            if (world.getBlockState(posI).isIn(BlockTags.AIR)) {
                setBlockState(world, posI, stateI);
            }
        }
        return true;
    }

    /*/public void setBlocks(World world, BlockPos pos, BlockState state, int rotate, ArrayList<BlockPos> blocks){
        for (BlockPos i : blocks){
            setBlockState(world, pos.add( new Fun.BlockPosR(i).horizontalRotate(rotate) ), state);
        }
    }/*/


    @Override
    protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, Block.NOTIFY_ALL | Block.FORCE_STATE);
    }

    static {
        int[][] iLogX = {{6, 5, 4}, {7, 5, 4}, {1, 6, 4}, {4, 6, 2}, {2, 7, 4}, {3, 7, 4}, {4, 7, 4}, {6, 8, 4}, {4, 10, 6}};
        int[][] iLogY = {{5, 0, 4}, {5, 1, 4}, {5, 2, 4}, {5, 3, 4}, {5, 4, 4}, {5, 5, 4}, {2, 6, 4}, {5, 6, 2}, {5, 6, 4}, {7, 6, 4}, {5, 7, 4}, {5, 7, 7}, {2, 8, 4}, {3, 8, 6}, {5, 8, 2}, {5, 8, 4}, {3, 9, 6}, {5, 9, 2}, {6, 9, 5}, {3, 10, 6}, {5, 10, 2}};
        int[][] iLogZ = {{5, 6, 5}, {5, 6, 6}, {5, 6, 7}, {3, 7, 5}, {3, 7, 6}, {5, 7, 2}, {5, 7, 3}, {2, 8, 3}, {6, 8, 5}, {5, 10, 3}};
        int[][] stateA = {{4, 3, 4}, {5, 3, 3}, {5, 3, 5}, {6, 3, 4}, {4, 4, 4}, {5, 4, 3}, {5, 4, 5}, {6, 4, 4}, {1, 5, 4}, {2, 5, 4}, {4, 5, 2}, {4, 5, 4}, {5, 5, 2}, {5, 5, 3}, {5, 5, 5}, {5, 5, 6}, {5, 5, 7}, {6, 5, 3}, {6, 5, 5}, {7, 5, 3}, {7, 5, 5}, {8, 5, 4}, {0, 6, 4}, {1, 6, 3}, {1, 6, 5}, {2, 6, 3}, {2, 6, 5}, {3, 6, 2}, {3, 6, 4}, {3, 6, 5}, {3, 6, 6}, {4, 6, 1}, {4, 6, 3}, {4, 6, 4}, {4, 6, 5}, {4, 6, 6}, {4, 6, 7}, {5, 6, 1}, {5, 6, 3}, {5, 6, 8}, {6, 6, 2}, {6, 6, 4}, {6, 6, 5}, {6, 6, 6}, {6, 6, 7}, {7, 6, 3}, {7, 6, 5}, {8, 6, 4}, {1, 7, 4}, {2, 7, 3}, {2, 7, 5}, {2, 7, 6}, {3, 7, 3}, {3, 7, 7}, {4, 7, 2}, {4, 7, 3}, {4, 7, 5}, {4, 7, 6}, {4, 7, 7}, {5, 7, 1}, {5, 7, 5}, {5, 7, 6}, {5, 7, 8}, {6, 7, 2}, {6, 7, 3}, {6, 7, 4}, {6, 7, 5}, {6, 7, 7}, {7, 7, 4}, {1, 8, 3}, {1, 8, 4}, {2, 8, 2}, {2, 8, 5}, {2, 8, 6}, {3, 8, 3}, {3, 8, 4}, {3, 8, 5}, {3, 8, 7}, {4, 8, 2}, {4, 8, 4}, {4, 8, 6}, {5, 8, 1}, {5, 8, 3}, {5, 8, 5}, {5, 8, 7}, {6, 8, 2}, {6, 8, 3}, {6, 8, 6}, {7, 8, 4}, {7, 8, 5}, {2, 9, 3}, {2, 9, 4}, {2, 9, 6}, {3, 9, 5}, {3, 9, 7}, {4, 9, 2}, {4, 9, 6}, {5, 9, 1}, {5, 9, 3}, {5, 9, 4}, {5, 9, 5}, {6, 9, 2}, {6, 9, 4}, {6, 9, 6}, {7, 9, 5}, {2, 10, 6}, {3, 10, 5}, {3, 10, 7}, {4, 10, 2}, {4, 10, 3}, {4, 10, 5}, {4, 10, 7}, {5, 10, 1}, {5, 10, 4}, {5, 10, 6}, {6, 10, 2}, {6, 10, 3}, {6, 10, 5}, {3, 11, 6}, {4, 11, 6}, {5, 11, 2}, {5, 11, 3}};
        int[][] state2 = {{3, 4, 4}, {4, 4, 3}, {4, 4, 5}, {6, 4, 3}, {6, 4, 5}, {7, 4, 5}, {2, 5, 3}, {2, 5, 5}, {3, 5, 4}, {3, 5, 5}, {3, 5, 6}, {4, 5, 1}, {4, 5, 3}, {4, 5, 5}, {4, 5, 6}, {4, 5, 7}, {5, 5, 1}, {6, 5, 2}, {6, 5, 6}, {6, 5, 7}, {7, 5, 2}, {7, 5, 6}, {8, 5, 3}, {8, 5, 5}, {2, 6, 2}, {2, 6, 6}, {3, 6, 1}, {3, 6, 3}, {3, 6, 7}, {4, 6, 0}, {4, 6, 8}, {6, 6, 1}, {6, 6, 3}, {6, 6, 8}, {7, 6, 2}, {7, 6, 6}, {7, 6, 7}, {8, 6, 3}, {8, 6, 5}, {1, 7, 3}, {1, 7, 5}, {1, 7, 6}, {2, 7, 2}, {2, 7, 7}, {3, 7, 2}, {3, 7, 8}, {4, 7, 1}, {4, 7, 8}, {5, 7, 0}, {5, 7, 9}, {6, 7, 1}, {6, 7, 6}, {6, 7, 8}, {7, 7, 2}, {7, 7, 3}, {7, 7, 5}, {7, 7, 7}, {8, 7, 4}, {1, 8, 5}, {1, 8, 6}, {2, 8, 7}, {3, 8, 2}, {3, 8, 8}, {4, 8, 1}, {4, 8, 3}, {4, 8, 5}, {4, 8, 7}, {5, 8, 0}, {5, 8, 6}, {5, 8, 8}, {6, 8, 1}, {6, 8, 7}, {7, 8, 2}, {7, 8, 3}, {7, 8, 6}, {8, 8, 4}, {8, 8, 5}, {1, 9, 3}, {1, 9, 4}, {1, 9, 6}, {2, 9, 2}, {2, 9, 5}, {2, 9, 7}, {3, 9, 2}, {3, 9, 3}, {3, 9, 4}, {3, 9, 8}, {4, 9, 1}, {4, 9, 3}, {4, 9, 4}, {4, 9, 5}, {4, 9, 7}, {5, 9, 6}, {5, 9, 7}, {6, 9, 1}, {6, 9, 3}, {6, 9, 7}, {7, 9, 2}, {7, 9, 4}, {7, 9, 6}, {8, 9, 5}, {2, 10, 3}, {2, 10, 4}, {2, 10, 5}, {3, 10, 2}, {3, 10, 3}, {3, 10, 4}, {4, 10, 1}, {4, 10, 4}, {4, 10, 8}, {5, 10, 5}, {5, 10, 7}, {6, 10, 1}, {6, 10, 4}, {6, 10, 6}, {7, 10, 2}, {7, 10, 3}, {7, 10, 5}, {3, 11, 5}, {4, 11, 3}, {4, 11, 5}, {4, 11, 7}, {5, 11, 4}, {5, 11, 6}, {6, 11, 3}, {6, 11, 5}};
        int[][] state3 = {{6, 4, 7}, {3, 5, 1}, {3, 5, 3}, {6, 5, 1}, {8, 5, 6}, {2, 6, 7}, {7, 6, 1}, {8, 6, 2}, {8, 6, 6}, {2, 7, 8}, {3, 7, 1}, {7, 7, 1}, {7, 7, 6}, {8, 7, 3}, {8, 7, 5}, {8, 7, 7}, {1, 8, 7}, {3, 8, 1}, {3, 8, 9}, {4, 8, 0}, {4, 8, 8}, {5, 8, 9}, {6, 8, 0}, {6, 8, 8}, {7, 8, 7}, {8, 8, 2}, {8, 8, 3}, {8, 8, 6}, {9, 8, 5}, {1, 9, 5}, {3, 9, 1}, {4, 9, 8}, {5, 9, 8}, {6, 9, 8}, {7, 9, 1}, {7, 9, 3}, {7, 9, 7}, {8, 9, 4}, {8, 9, 6}, {9, 9, 5}, {4, 10, 9}, {5, 10, 8}, {6, 10, 7}, {7, 10, 4}, {7, 10, 6}, {8, 10, 3}, {8, 10, 5}, {2, 11, 5}, {3, 11, 4}, {4, 11, 4}, {5, 11, 5}, {5, 11, 7}, {6, 11, 4}, {6, 11, 6}, {7, 11, 3}, {7, 11, 5}, {4, 12, 5}, {5, 12, 4}, {5, 12, 6}, {6, 12, 5}};
        int[][] state6 = {{8, 6, 1}, {2, 7, 9}, {8, 7, 6}, {8, 7, 8}, {9, 7, 3}, {9, 7, 5}, {4, 8, 9}, {6, 8, 9}, {7, 8, 8}, {8, 8, 7}, {9, 8, 3}, {5, 9, 9}, {8, 9, 3}, {8, 9, 7}, {9, 9, 4}, {10, 9, 5}, {7, 10, 7}, {8, 10, 4}, {8, 10, 6}, {5, 11, 8}, {6, 11, 7}, {7, 11, 4}, {7, 11, 6}, {5, 12, 5}};
        int[][] state7 = {{9, 7, 6}, {8, 9, 8}, {9, 9, 3}, {9, 9, 7}, {8, 10, 7}, {7, 11, 7}, {5, 13, 5}};
        int[][] state8 = {{9, 10, 7}};
        int[][] iLeaves=Stream.of(stateA, state2, state3, state6, state7, state8)
                .flatMap(Stream::of).toArray(int[][]::new);

        logX = Fun.intsToPos(iLogX);
        logY = Fun.intsToPos(iLogY);
        logZ = Fun.intsToPos(iLogZ);
        leaves = Fun.intsToPos(iLeaves);
        Fun.offsetBlockPoses(new BlockPos(-5, 0, -4), logX, logY, logZ, leaves);
        logs = new ArrayList<>(logX);
        logs.addAll(logY);
        logs.addAll(logZ);
    }
}
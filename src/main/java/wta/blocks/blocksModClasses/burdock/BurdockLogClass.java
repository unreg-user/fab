package wta.blocks.blocksModClasses.burdock;

import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import wta.Fun;
import wta.TagsMod;

import java.util.List;

public class BurdockLogClass extends PillarBlock {
    public BurdockLogClass(Settings settings) {
        super(settings);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        List<Direction> directions=Fun.axisToDirection.get(state.get(AXIS).ordinal());
        if (state.get(AXIS)==Direction.Axis.Y && world.getBlockState(pos.down()).isIn(BlockTags.DIRT)){
            return;
        }
        for (Direction dirI : directions){
            if (!world.getBlockState(pos.offset(dirI)).isIn(TagsMod.BlockTagsMod.burdock_tree_blocks)) {
                world.breakBlock(pos, true);
            }
        }
    }
}

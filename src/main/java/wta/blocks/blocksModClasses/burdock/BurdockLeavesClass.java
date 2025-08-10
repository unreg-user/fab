package wta.blocks.blocksModClasses.burdock;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import wta.entities.projectiles.burdock.BurdockEntity;

public class BurdockLeavesClass extends LeavesBlock {
    public BurdockLeavesClass(Settings settings) {
        super(settings);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        if (random.nextInt(100)==0){
            BurdockEntity.summonRandomBurdock(world, new Vec3d(pos.getX(), pos.getY(), pos.getZ()), 1, Random.create(), 2);
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }
}

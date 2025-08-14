package wta.entities.mobs.itemZombie;

import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class LookAroundTask2 extends LookAroundTask {
    public LookAroundTask2(int minRunTime, int maxRunTime) {
        super(minRunTime, maxRunTime);
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        super.keepRunning(serverWorld, mobEntity, l);

        System.out.println(1);
    }
}

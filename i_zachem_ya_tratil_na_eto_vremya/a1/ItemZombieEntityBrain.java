package wta.entities.mobs.itemZombie;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.LookAtMobTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;

import java.util.List;
import java.util.Set;

public class ItemZombieEntityBrain {
    public static final List<SensorType<? extends Sensor<? super ItemZombieEntity>>> sensors=ImmutableList.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY
    );
    public static final List<MemoryModuleType<?>> memoryModules=ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.NEAREST_PLAYERS,
            MemoryModuleType.VISIBLE_MOBS
    );

    public static Brain<?> create(ItemZombieEntity itemZombie, Brain<ItemZombieEntity> brain) {
        addCoreTasks(brain);
        addIdleTasks(brain);
        addFightTasks(itemZombie, brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE); //fight
        brain.resetPossibleActivities();

        return brain;
    }

    private static void addCoreTasks(Brain<ItemZombieEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new LookAroundTask2(45, 90),
                new StayAboveWaterTask(1F)
        ));
    }

    private static void addIdleTasks(Brain<ItemZombieEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create(itemZombie -> itemZombie.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                Pair.of(1, UpdateAttackTargetTask.create(ItemZombieEntity::getHurtBy)),
                Pair.of(2, LookAtMobTask.create(16))
        ));
    }

    private static void addFightTasks(ItemZombieEntity itemZombie, Brain<ItemZombieEntity> brain) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(), ImmutableSet.of());
    }

    static void updateActivities(ItemZombieEntity itemZombie) {
        itemZombie.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }
}
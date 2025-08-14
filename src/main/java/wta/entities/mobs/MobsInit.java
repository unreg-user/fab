package wta.entities.mobs;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import wta.entities.FabEntityModelLayers;
import wta.entities.mobs.enderCube.EnderCubeEntity;
import wta.entities.mobs.enderCube.EnderCubeEntityModel;
import wta.entities.mobs.enderCube.EnderCubeEntityRenderer;
import wta.entities.mobs.itemZombie.ItemZombieEntity;
import wta.entities.mobs.itemZombie.ItemZombieEntityModel;
import wta.entities.mobs.itemZombie.ItemZombieEntityRenderer;

import static wta.Fab.MODID;

public class MobsInit {
    public static EntityType<EnderCubeEntity> enderCubeE;
    public static EntityType<ItemZombieEntity> itemZombieE;

    public static void init(){
        enderCubeE=Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MODID, "ender_cube"),
                EntityType.Builder
                        .create(EnderCubeEntity::new, SpawnGroup.CREATURE)
                        .dimensions(1f, 1f)
                        .build("ender_cube")
        );
        FabricDefaultAttributeRegistry
                .register(enderCubeE, EnderCubeEntity.createAttributes());

        itemZombieE=Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MODID, "item_zombie"),
                EntityType.Builder
                        .create(ItemZombieEntity::newForRegistry, SpawnGroup.MONSTER)
                        .dimensions(0.6f, 1.95f)
                        .eyeHeight(1.74f)
                        .passengerAttachments(2.0125f)
                        .vehicleAttachment(-0.7f)
                        .maxTrackingRange(16)
                        .build("item_zombie")
        );
        FabricDefaultAttributeRegistry
                .register(itemZombieE, ItemZombieEntity.createAttributes());
    }

    public static void clientInit() {
        EntityModelLayerRegistry.registerModelLayer(FabEntityModelLayers.ENDER_CUBE, EnderCubeEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(enderCubeE, context -> new EnderCubeEntityRenderer(context, new EnderCubeEntityModel(context.getPart(FabEntityModelLayers.ENDER_CUBE)), 0.5F));

        EntityModelLayerRegistry.registerModelLayer(FabEntityModelLayers.ITEM_ZOMBIE, ItemZombieEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(itemZombieE, context -> new ItemZombieEntityRenderer(context, new ItemZombieEntityModel(context.getPart(FabEntityModelLayers.ITEM_ZOMBIE)), 0.5F));
    }
}

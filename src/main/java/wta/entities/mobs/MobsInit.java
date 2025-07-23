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
import wta.entities.mobs.enderCubeP.EnderCubeEntity;
import wta.entities.mobs.enderCubeP.EnderCubeEntityModel;
import wta.entities.mobs.enderCubeP.EnderCubeEntityRenderer;

import static wta.Fab.MODID;

public class MobsInit {
    public static EntityType<EnderCubeEntity> enderCubeE;

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
    }

    public static void clientInit() {
        EntityModelLayerRegistry.registerModelLayer(FabEntityModelLayers.ENDER_CUBE, EnderCubeEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(enderCubeE, context -> new EnderCubeEntityRenderer(context, new EnderCubeEntityModel(context.getPart(FabEntityModelLayers.ENDER_CUBE)), 0.5f));
    }
}

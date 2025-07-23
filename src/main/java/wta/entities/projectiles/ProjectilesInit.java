package wta.entities.projectiles;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import wta.AllInit;
import wta.entities.FabEntityModelLayers;
import wta.entities.mobs.MobsInit;
import wta.entities.mobs.enderCubeP.EnderCubeEntity;
import wta.entities.mobs.enderCubeP.EnderCubeEntityModel;
import wta.entities.mobs.enderCubeP.EnderCubeEntityRenderer;
import wta.entities.projectiles.burdock.BurdockEntity;
import wta.entities.projectiles.burdock.BurdockEntityModel;
import wta.entities.projectiles.burdock.BurdockEntityRenderer;
import wta.entities.projectiles.burdock.BurdockItemClass;

import static wta.Fab.MODID;

public class ProjectilesInit {
    //Entities
    public static EntityType<BurdockEntity> burdockE;

    //Items
    public static Item burdockI;

    //other
    private static Item[] inMI;

    public static void init(){
        //Entities
        burdockE=Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MODID, "burdock"),
                EntityType.Builder
                        .create(BurdockEntity::new, SpawnGroup.MISC)
                        .dimensions(0.5f, 0.5f)
                        .build("burdock")
        );

        //Items
        burdockI=Registry.register(
                Registries.ITEM,
                Identifier.of(MODID, "burdock"),
                new BurdockItemClass(new Item.Settings())
        );

        //other
        inMI=new Item[]{
                burdockI
        };
        AllInit.inMI.add(inMI);
    }

    public static void clientInit() {
        EntityModelLayerRegistry.registerModelLayer(FabEntityModelLayers.BURDOCK, BurdockEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(burdockE, context -> new BurdockEntityRenderer(context, new BurdockEntityModel(context.getPart(FabEntityModelLayers.BURDOCK)), 0f));
    }
}

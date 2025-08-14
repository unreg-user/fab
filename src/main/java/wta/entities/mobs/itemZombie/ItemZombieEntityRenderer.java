package wta.entities.mobs.itemZombie;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

import static wta.Fab.MODID;

public class ItemZombieEntityRenderer extends BipedEntityRenderer<ItemZombieEntity, ItemZombieEntityModel> {
    public ItemZombieEntityRenderer(EntityRendererFactory.Context ctx, ItemZombieEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
        this.addFeature(new HeadItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ItemZombieEntity entity) {
        return Identifier.of(MODID, "textures/entity/item_zombie/item_zombie.png");
    }
}

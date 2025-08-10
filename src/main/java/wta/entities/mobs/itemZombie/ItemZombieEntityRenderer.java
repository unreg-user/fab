package wta.entities.mobs.itemZombie;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class ItemZombieEntityRenderer extends BipedEntityRenderer<ItemZombieEntity, ItemZombieEntityModel> {
    public ItemZombieEntityRenderer(EntityRendererFactory.Context ctx, ItemZombieEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Override
    public Identifier getTexture(ItemZombieEntity entity) {
        return Identifier.ofVanilla("textures/entity/zombie/zombie.png");
    }
}

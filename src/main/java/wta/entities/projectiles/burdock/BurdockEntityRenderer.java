package wta.entities.projectiles.burdock;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import wta.entities.classes.CustomProjectileEntityRenderer;

import static wta.Fab.MODID;

@Environment(EnvType.CLIENT)
public class BurdockEntityRenderer extends CustomProjectileEntityRenderer<BurdockEntity, BurdockEntityModel> {
    public BurdockEntityRenderer(EntityRendererFactory.Context context, BurdockEntityModel model, float f) {
        super(context, model, f);
    }

    @Override
    public Identifier getTexture(BurdockEntity entity) {
        return Identifier.of(MODID, "textures/entity/burdock/burdock.png");
    }
}

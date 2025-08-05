package wta.entities.mobs.enderCubeP;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import static wta.Fab.MODID;

@Environment(EnvType.CLIENT)
public class EnderCubeEntityRenderer extends MobEntityRenderer<EnderCubeEntity, EnderCubeEntityModel> {
    public EnderCubeEntityRenderer(EntityRendererFactory.Context context, EnderCubeEntityModel entityModel, float f) {
        super(context, entityModel, f);
    }

    @Override
    public Identifier getTexture(EnderCubeEntity entity) {
        return Identifier.of(MODID, "textures/entity/ender_cube/ender_cube.png");
    }
}

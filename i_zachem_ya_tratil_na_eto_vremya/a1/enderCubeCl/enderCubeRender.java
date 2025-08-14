package wta.entities.mobs.enderCubeCl;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import static wta.Fab.MODID;

public class CubeEntityRenderer extends MobEntityRenderer<enderCubeClass, enderCubeModel> {

    public CubeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, enderCubeModel, 0.5f);
    }

    @Override
    public Identifier getTexture(enderCubeClass entity) {
        return Identifier.of(MODID, "textures/entity/cube/cube.png");
    }
}

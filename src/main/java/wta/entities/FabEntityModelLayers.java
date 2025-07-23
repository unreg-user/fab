package wta.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import wta.Fab;

@Environment(EnvType.CLIENT)
public class FabEntityModelLayers {
    public static final EntityModelLayer ENDER_CUBE=createMain("ender_cube");
    public static final EntityModelLayer BURDOCK=createMain("burdock");

    private static EntityModelLayer create(Identifier id, String layer) {
        return new EntityModelLayer(id, layer);
    }

    private static EntityModelLayer createMain(String name) {
        return create(Identifier.of(Fab.MODID, name), "main");
    }
}

package wta.features;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import wta.features.configures.BallTreeFeatureConfig;
import wta.features.featuresClasses.BallTreeFeature;

import static wta.Fab.MODID;

public class FeaturesInit {
    public static BallTreeFeature ballTree;

    public static void init(){
        ballTree= Registry.register(
                Registries.FEATURE,
                Identifier.of(MODID, "ball_tree"),
                new BallTreeFeature(BallTreeFeatureConfig.CODEC)
        );
    }
}

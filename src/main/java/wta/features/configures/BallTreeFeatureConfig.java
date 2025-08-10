package wta.features.configures;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.FeatureConfig;

public record BallTreeFeatureConfig(Identifier log, Identifier leaves) implements FeatureConfig {
    public static final Codec<BallTreeFeatureConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Identifier.CODEC.fieldOf("log").forGetter(BallTreeFeatureConfig::log),
                            Identifier.CODEC.fieldOf("leaves").forGetter(BallTreeFeatureConfig::leaves)
                    )
                    .apply(instance, BallTreeFeatureConfig::new)
    );
}

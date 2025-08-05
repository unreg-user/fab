package wta.mixins.mixin;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wta.mixins.mixinClasses.other.StuckBurdockFeatureRenderer;

@Mixin(LivingEntityRenderer.class)
public abstract class EntityRendererFixer<T extends LivingEntity, M extends EntityModel<T>>{
    @Shadow
    @Final
    protected abstract boolean addFeature(FeatureRenderer<T, M> feature);

    @Inject(
            method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/EntityModel;F)V",
            at = @At("RETURN")
    )
    private void onInit(EntityRendererFactory.Context ctx, EntityModel<?> model, float shadowRadius, CallbackInfo ci) {
        if (((Object) this) instanceof LivingEntityRenderer self){
            this.addFeature(new StuckBurdockFeatureRenderer<>(ctx, self));
        }
    }
}
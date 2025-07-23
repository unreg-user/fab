package wta.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wta.mixinClasses.Items.ItemsVanillaClasses.StickItem;
import wta.mixinClasses.blocks.BlocksVanillaInit;

@Mixin(Items.class)
public class ReplaceItems {
    @Shadow
    @Final
    @Mutable
    public static Item STICK;

    @Inject(
            method="<clinit>",
            at=@At("TAIL")
    )
    private static void onStaticInit(CallbackInfo ci) {
        STICK=Registry.register(
                Registries.ITEM,
                Identifier.ofVanilla("stick"),
                new StickItem(
                        BlocksVanillaInit.stickBlock,
                        new Item.Settings()
                )
        );
    }
}

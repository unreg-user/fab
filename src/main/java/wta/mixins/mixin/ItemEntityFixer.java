package wta.mixins.mixin;

import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import wta.entities.mobs.itemZombie.AtHeadItemEntity;

@Mixin(ItemEntity.class)
public abstract class ItemEntityFixer implements AtHeadItemEntity {
}

package wta.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import wta.effects.effectsClasses.burdockiness.Burdockiness;

import static wta.Fab.MODID;

public class EffectsInit {
    public static StatusEffect burdockiness;
    public static RegistryEntry<StatusEffect> burdockinessEntry;

    public static void init(){
        burdockiness=Registry.register(
                Registries.STATUS_EFFECT,
                Identifier.of(MODID, "burdockiness"),
                new Burdockiness()
        );
        burdockinessEntry=Registries.STATUS_EFFECT.getEntry(burdockiness);
    }
}

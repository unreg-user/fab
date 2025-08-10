package wta;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static wta.Fab.MODID;

public class DamageTypesMod {
    public static final RegistryKey<DamageType> burdock=get("burdock");

    public static RegistryKey<DamageType> get(String path){
        return RegistryKey.of(
                RegistryKeys.DAMAGE_TYPE,
                Identifier.of(MODID, path)
        );
    }
}

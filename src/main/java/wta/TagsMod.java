package wta;

import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import static wta.Fab.MODID;

public class TagsMod {
    public static <T> TagKey<T> tagMod(RegistryKey<Registry<T>> reg, String id){
        return TagKey.of(reg, Identifier.of(MODID, id));
    }
    public static class BlockTagsMod{
        public static TagKey<Block> allGrass=blockTagMod("all_grass");

        public static TagKey<Block> blockTagMod(String id){
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MODID, id));
        }
    }
}

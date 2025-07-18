package wta.mixinClasses.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import wta.mixinClasses.blocks.blocksVanillaClasses.StickBlock;

import static wta.Fab.MODID;

public class BlocksVanillaInit {
    public static Block stickBlock;

    static {
        stickBlock=Registry.register(
                Registries.BLOCK,
                Identifier.of(MODID, "stick"),
                new StickBlock(
                        AbstractBlock.Settings.create()
                                .mapColor(MapColor.DIRT_BROWN)
                                .sounds(BlockSoundGroup.GRASS)
                                .nonOpaque()
                                .allowsSpawning(Blocks::never)
                                .pistonBehavior(PistonBehavior.DESTROY)
                )
        );
    }
}

package wta.other;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import wta.entities.mobs.itemZombie.ItemZombieEntity;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static wta.Fab.MODID;

public class OnLoad2 implements SimpleSynchronousResourceReloadListener {
    public static final Identifier id=Identifier.of(MODID, "on_load");

    @Override
    public Identifier getFabricId() {
        return id;
    }

    @Override
    public void reload(ResourceManager manager) {
        System.out.println(new ItemStack(Items.BIRCH_SAPLING).isIn(ItemTags.SAPLINGS));
        ItemZombieEntity.loadTypes();
    }
}

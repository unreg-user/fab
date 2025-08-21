package wta.other;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
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

public class OnLoad  implements IdentifiableResourceReloadListener {
    public static final Identifier id=Identifier.of(MODID, "on_load");

    @Override
    public Identifier getFabricId() {
        return id;
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return CompletableFuture.runAsync(() -> {}).thenCompose(synchronizer::whenPrepared).thenAccept(
                (q) -> {
                    //System.out.println(new ItemStack(Items.BIRCH_SAPLING).isIn(ItemTags.SAPLINGS));
                    ItemZombieEntity.types=null;
                }
        );
    }
}

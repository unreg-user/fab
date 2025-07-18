package wta.mixin;

import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryInfo;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

import java.util.Map;
import java.util.Objects;

@Mixin(SimpleRegistry.class)
public abstract class Unregistry<T> {

    @Shadow
    @Final
    @Mutable
    private Map<RegistryKey<T>, RegistryEntry.Reference<T>> keyToEntry;

    @Shadow
    @Final
    @Mutable
    private Map<Identifier, RegistryEntry.Reference<T>> idToEntry;

    @Shadow
    @Final
    @Mutable
    private Map<T, RegistryEntry.Reference<T>> valueToEntry;

    @Shadow
    @Final
    @Mutable
    private ObjectList<RegistryEntry.Reference<T>> rawIdToEntry;

    @Shadow
    @Final
    @Mutable
    private Reference2IntMap<T> entryToRawId;

    @Shadow
    @Final
    @Mutable
    private Map<RegistryKey<T>, RegistryEntryInfo> keyToEntryInfo;

    @Shadow
    private boolean frozen;

    @Shadow
    private Map<T, RegistryEntry.Reference<T>> intrusiveValueToEntry;

    @Shadow
    private Lifecycle lifecycle;

    @Shadow
    @Final
    @Mutable
    private RegistryWrapper.Impl<T> wrapper;

    @Shadow
    @Nullable
    public abstract T get(@Nullable Identifier id);

    /**
     * @author UnregUser
     * @reason replace and delete new Item()... vanilla.<br>
     * For reregister just use original Identifier.<br>
     * For delete just register with null (now it don't work!), but this is dangerous.
     */
    @Overwrite
    public RegistryEntry.Reference<T> add(RegistryKey<T> key, T value, RegistryEntryInfo info) {
        Objects.requireNonNull(key);
        if (value==null){
            value=this.get(key.getValue());
            Objects.requireNonNull(value);
            RegistryEntry.Reference<T> reference;
            if (intrusiveValueToEntry != null) {
                reference = this.intrusiveValueToEntry.remove(value);
                if (reference == null) {
                    String var10002 = String.valueOf(key);
                    throw new AssertionError("Missing intrusive holder for " + var10002 + ":" + String.valueOf(value));
                }

                reference.setRegistryKey(key);
            } else {
                reference = this.keyToEntry.computeIfAbsent(key, (k) -> RegistryEntry.Reference.standAlone(wrapper, k));
            }

            this.keyToEntry.remove(key);
            this.idToEntry.remove(key.getValue());
            this.valueToEntry.remove(value);
            //int i = this.rawIdToEntry.size();
            this.rawIdToEntry.remove(reference);
            this.entryToRawId.remove(value);
            this.keyToEntryInfo.remove(key);
            this.lifecycle=this.lifecycle.add(info.lifecycle());
            return null;
        }else{
            RegistryEntry.Reference<T> reference;
            if (intrusiveValueToEntry != null) {
                reference = this.intrusiveValueToEntry.remove(value);
                if (reference == null) {
                    String var10002 = String.valueOf(key);
                    throw new AssertionError("Missing intrusive holder for " + var10002 + ":" + String.valueOf(value));
                }

                reference.setRegistryKey(key);
            } else {
                reference = this.keyToEntry.computeIfAbsent(key, (k) -> RegistryEntry.Reference.standAlone(wrapper, k));
            }

            this.keyToEntry.put(key, reference);
            this.idToEntry.put(key.getValue(), reference);
            this.valueToEntry.put(value, reference);
            int i = this.rawIdToEntry.size();
            this.rawIdToEntry.add(reference);
            this.entryToRawId.put(value, i);
            this.keyToEntryInfo.put(key, info);
            this.lifecycle = this.lifecycle.add(info.lifecycle());
            return reference;
        }
    }
}

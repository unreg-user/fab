package wta.entities.mobs.itemZombie.types;

import com.google.common.collect.ImmutableMap;
import com.ibm.icu.impl.locale.XCldrStub;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import oshi.annotation.concurrent.Immutable;

import java.util.HashMap;
import java.util.Map;

/** builder, чем первее тем приоритетней */
public class HierarchyB {
	private final HashMap<Item, Integer> levels=new HashMap<>();
	private int next=0;

	private HierarchyB() {

	}

	public static HierarchyB create(){
		return new HierarchyB();
	}

	/** add in this priority */
	public HierarchyB a(Item item){
		levels.put(item, next);
		return this;
	}

	/** add in this priority */
	public HierarchyB a(TagKey<Item> items){
		for (RegistryEntry<Item> i : Registries.ITEM.getOrCreateEntryList(items)){
			levels.put(i.value(), next);
		}
		return this;
	}

	/** start next priority and add */
	public HierarchyB an(Item item){
		next++;
		levels.put(item, next);
		return this;
	}

	/** start next priority and add */
	public HierarchyB an(TagKey<Item> items){
		next++;
		for (RegistryEntry<Item> i : Registries.ITEM.getOrCreateEntryList(items)){
			levels.put(i.value(), next);
		}
		return this;
	}

	public Hierarchy build(){
		return new Hierarchy(ImmutableMap.copyOf(levels));
	}

	@SuppressWarnings("DataFlowIssue")
	public static class Hierarchy {
		private final ImmutableMap<Item, Integer> levels;

		private Hierarchy(ImmutableMap<Item, Integer> levels){
			this.levels=levels;
		}

		public int get(Item item){
			return levels.getOrDefault(item, -1);
		}

		public boolean contains(Item item){
			return levels.containsKey(item);
		}
	}
}

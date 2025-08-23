package wta.entities.mobs.itemZombie.types;

import com.mojang.datafixers.types.Type;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import wta.fun.Type2ListError;

import java.util.ArrayList;

public class List2T {
	public ArrayList<Item> list;

	public List2T(ArrayList<Item> list1){
		this.list=list1;
	}

	public List2T(){
		this(new ArrayList<>());
	}

	public void add(Item obj){
		list.add(obj);
	}

	public void add(TagKey<Item> obj){
		for (RegistryEntry<Item> i : Registries.ITEM.getOrCreateEntryList(obj)){
			list.add(i.value());
		}
	}

	public static  List2T of(Object... objs){
		List2T list=new List2T();
		for (Object i : objs){
			if (i instanceof Item i2){
				list.add(i2);
			}else if (i instanceof TagKey i2){
				list.add(i2);
			}else{
				throw new Type2ListError("this not Item or TagKey<Item>");
			}
		}
		return list;
	}
}

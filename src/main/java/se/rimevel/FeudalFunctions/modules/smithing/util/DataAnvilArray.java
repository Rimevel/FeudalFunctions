package se.rimevel.FeudalFunctions.modules.smithing.util;

import java.lang.ref.WeakReference;

import se.rimevel.FeudalFunctions.modules.smithing.items.ItemParts;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DataAnvilArray
{
	public WeakReference<ItemParts> item;
	public String materialName;
	public int heatTime;
	public ItemStack materialStack;
	
	public DataAnvilArray(ItemParts item, String materialName, int heatTime, ItemStack materialStack)
	{
		this.item = new WeakReference<ItemParts>(item);
		this.materialName = materialName;
		this.heatTime = heatTime;
		this.materialStack = materialStack;
	}
}

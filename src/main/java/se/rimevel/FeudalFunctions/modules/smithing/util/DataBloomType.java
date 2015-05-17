package se.rimevel.FeudalFunctions.modules.smithing.util;

import net.minecraft.item.ItemStack;

public class DataBloomType
{
	public String typeName;
	public String name;
	public String[] oreNames;
	public int amount;
	public ItemStack result;
	
	public DataBloomType(String typeName, String name, ItemStack result, int amount, String ... oreNames)
	{
		this.typeName = typeName;
		this.name = name;
		this.result = result;
		this.amount = amount;
		this.oreNames = oreNames;
	}
}

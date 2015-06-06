package se.rimevel.FeudalFunctions.modules.survival.crafting;

import net.minecraft.item.ItemStack;

public class RecipeCampfire
{
	public ItemStack toCook;
	public ItemStack result;
	public int time;
	
	public RecipeCampfire(ItemStack toCook, ItemStack result, int time)
	{
		this.toCook = toCook;
		this.result = result;
		this.time = time;
	}
	
	public boolean checkMatch(ItemStack stack)
	{
		if(ItemStack.areItemStacksEqual(toCook, stack))
		{
			return true;
		}
		
		return false;
	}
}

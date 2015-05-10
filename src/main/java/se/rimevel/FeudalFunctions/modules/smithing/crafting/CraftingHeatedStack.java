package se.rimevel.FeudalFunctions.modules.smithing.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingHeatedStack
{
	public char symbol;
	public ItemStack stack;
	public boolean isHeated;
	
	public CraftingHeatedStack(char symbol, ItemStack stack, boolean isHeated)
	{
		this.symbol = symbol;
		this.stack = stack;
		this.isHeated = isHeated;
	}
	
	public CraftingHeatedStack(char symbol, Item item, boolean isHeated)
	{
		this.symbol = symbol;
		this.stack = new ItemStack(item);
		this.isHeated = isHeated;
	}
	
	public CraftingHeatedStack(char symbol, Block block, boolean isHeated)
	{
		this.symbol = symbol;
		this.stack = new ItemStack(block);
		this.isHeated = isHeated;
	}
	
	public CraftingHeatedStack copy()
	{
		return new CraftingHeatedStack(this.symbol, this.stack.copy(), this.isHeated);
	}
}

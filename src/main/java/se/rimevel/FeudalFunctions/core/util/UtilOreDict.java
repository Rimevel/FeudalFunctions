package se.rimevel.FeudalFunctions.core.util;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Utility functions for checking if a given object is of the correct oreDict name.
 */
public class UtilOreDict
{
	/**
	 * Check if the given item have the given oreDict name.
	 * 
	 * @param stack Stack to be checked.
	 * @param text OreDict name of to check.
	 * @return True if the item has the given oreDict name.
	 */
	public static boolean compareItem(ItemStack stack, String text)
	{
		int[] oreId = OreDictionary.getOreIDs(stack);
		
		if(oreId != null && oreId.length >= 1)
		{
			for (int ore : oreId)
			{
				if(OreDictionary.getOreName(ore).contains(text))
				{
					return true;
				}
			}
			
		}
		return false;
	}
	
	/**
	 * Check if the given block have the given oreDict name.
	 * 
	 * @param block Block to be checked.
	 * @param text OreDict name to check.
	 * @return True if the block has the given oreDict name.
	 */
	public static boolean compareBlock(Block block, String text)
	{
		ItemStack compareStack = new ItemStack(block, 1);
		return compareItem(compareStack, text);
	}
	
	public static boolean compareBlock(Block block, String text, int meta)
	{
		ItemStack compareStack = new ItemStack(block, 1, meta);
		return compareItem(compareStack, text);
	}
	
	public static boolean areItemsEqual(ItemStack firstStack, ItemStack secondStack)
	{
		int[] oreIdOne = OreDictionary.getOreIDs(firstStack);
		int[] OreIdTwo = OreDictionary.getOreIDs(secondStack);
		
		for (int one : oreIdOne)
		{
			for (int two : OreIdTwo)
			{
				if(one == two)
				{
					return true;
				}
			}
		}
		
		return false;
	}
}

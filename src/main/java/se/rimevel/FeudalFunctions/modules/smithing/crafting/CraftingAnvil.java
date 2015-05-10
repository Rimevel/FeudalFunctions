package se.rimevel.FeudalFunctions.modules.smithing.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemHeated;

public class CraftingAnvil
{
	int recipeWidth, recipeHeight;
	
	public final CraftingHeatedStack[] recipeItems;
	public final ItemStack recipeOutput;
	
	public CraftingAnvil(int width, int height, CraftingHeatedStack[] recipeArray, ItemStack recipeOutput)
	{
		this.recipeWidth = width;
		this.recipeHeight = height;
		this.recipeItems = recipeArray;
		this.recipeOutput = recipeOutput;
	}
	
	public ItemStack getRecipeOutput()
	{
		return this.recipeOutput;
	}
	
	public boolean matches(IInventory inventory, World world)
	{
		for (int x = 0; x <= 3 - this.recipeWidth; x++)
		{
			for (int y = 0; y <= 3 - this.recipeHeight; y++)
			{
				if (this.checkMatch(inventory, x, y, true))
				{
					return true;
				}

				if (this.checkMatch(inventory, x, y, false))
				{
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean checkMatch(IInventory inventory, int x, int y, boolean mode)
	{
		for (int width = 0; width < 3; width++)
		{
			for (int height = 0; height < 3; height++)
			{
				int xPos = width - x;
				int yPos = height - y;
				CraftingHeatedStack stack = null;

				if (xPos >= 0 && yPos >= 0 && xPos < this.recipeWidth && yPos < this.recipeHeight)
				{
					if (mode)
					{
						stack = this.recipeItems[this.recipeWidth - xPos - 1 + yPos * this.recipeWidth];
						UtilLog.info("Mode: true > " + (this.recipeWidth - xPos - 1 + yPos * this.recipeWidth));
					}
					else
					{
						stack = this.recipeItems[xPos + yPos * this.recipeWidth];
						UtilLog.info("Mode: false > " + (this.recipeWidth - xPos - 1 + yPos * this.recipeWidth));
					}
				}

				ItemStack invStack = getStackInRowAndColumn(inventory, width, height);
				
				if (invStack != null || stack != null)
				{
					if(stack != null && stack.isHeated)
					{
						if (invStack == null && stack.stack != null || invStack != null && stack.stack == null)
						{
							return false;
						}

						if(!ItemHeated.isHeatedItem(invStack))
						{
							return false;
						}
						
						UtilLog.info("!!One:" + stack.stack.getDisplayName());
						UtilLog.info("!!Two:" + ItemHeated.getHeatedItem(invStack).getDisplayName());
						
						if (stack.stack.getItem() != ItemHeated.getHeatedItem(invStack).getItem())
						{
							return false;
						}

						if (stack.stack.getItemDamage() != OreDictionary.WILDCARD_VALUE && stack.stack.getItemDamage() != ItemHeated.getHeatedItem(invStack).getItemDamage())
						{
							return false;
						}
					}
					else
					{
						if (invStack == null && stack != null || invStack != null && stack == null)
						{
							return false;
						}

						if (stack.stack.getItem() != invStack.getItem())
						{
							return false;
						}

						if (stack.stack.getItemDamage() != 32767 && stack.stack.getItemDamage() != invStack.getItemDamage())
						{
							return false;
						}
					}
					
				}
			}
		}

		return true;
	}

	/**
	 * Returns the recipes output ItemStack. If any item in the recipe is heated then it returns a heated item.
	 * @param inventory
	 * @return
	 */
	public ItemStack getCraftingResult(IInventory inventory)
	{
		boolean shouldBeHeated = false;
	
		ItemStack stack = this.getRecipeOutput().copy();
		for(int i = 0; i < inventory.getSizeInventory(); i++)
		{
			ItemStack slot = inventory.getStackInSlot(i);
			if(slot != null)
			{
				if(ItemHeated.isHeatedItem(slot))
				{
					shouldBeHeated = true;
				}
			}
		}
		
		if(shouldBeHeated)
		{
			stack = ItemHeated.createHeatedItem(this.getRecipeOutput().copy());
		}
		
		return stack;
	}

/**
	 * Get the stack in the slot matching the given coords on the crafting grid.
	 * @param inventory
	 * @param x
	 * @param y
	 * @return
	 */
	private ItemStack getStackInRowAndColumn(IInventory inventory, int x, int y)
	{
		if (x >= 0 && x < 3)
		{
			int slot = x + y * 3;

			ItemStack stack = inventory.getStackInSlot(slot);

			if(stack != null)
			{
				return inventory.getStackInSlot(slot);
			}
		}
		
		return null;
	}
}

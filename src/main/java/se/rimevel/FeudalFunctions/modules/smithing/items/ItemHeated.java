package se.rimevel.FeudalFunctions.modules.smithing.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import se.rimevel.FeudalFunctions.core.items.ItemBase;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataHeatableList;
import se.rimevel.FeudalFunctions.modules.smithing.util.EnumHeatableType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHeated extends ItemBase
{	
	public ItemHeated()
	{
		super("ingot", "axe_head", "pickaxe_head", "hoe_head", "shovel_head", "sword_blade", "hammer_head");
		setNoRepair();
		setMaxStackSize(1);
		setNoTexture(false);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack stack)
	{
		ItemStack heatedItem = getHeatedItem(stack);
		if(heatedItem != null)
		{
			IIcon icon = getTexture(getType(stack));
			return icon;
		}
		return null;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		ItemStack heatedItem = getHeatedItem(stack);
		if(heatedItem != null)
		{
			return "Hot " + heatedItem.getDisplayName();
		}
		return "No Item! Something is wrong!";
	}
	
	/**
	 * Get the item contained within the ItemHeated wrapper class. ONLY HEATED ITEMS!
	 * @param stack
	 * @return
	 */
	public static ItemStack getHeatedItem(ItemStack stack)
	{
		if(!stack.hasTagCompound()) {return null;}
		if(!stack.getTagCompound().hasKey("HeatedItem")) {return null;}
		return ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("HeatedItem"));
	}
	
	/**
	 * Creates a new ItemHeated with all the data needed. Returns null if the item is not heatable.
	 * @param heatedItem Item to be heated.
	 * @param type Type of heatable item.
	 * @return Returns an ItemStack if the stack is heatable and null if not.
	 */
	public static ItemStack createHeatedItem(ItemStack heatedItem)
	{
		if(DataHeatableList.getItemHeatTime(heatedItem) > 0)
		{
			ItemStack output = new ItemStack(MSmithing.items.heated_item.getInstance(), 1);
			if(!output.hasTagCompound())
			{
				output.stackTagCompound = new NBTTagCompound();
			}
			
			setHeatedItem(output, heatedItem);
			setType(heatedItem, DataHeatableList.getType(heatedItem));
			
			return output;
		}
		
		return null;
	}
	
	/**
	 * Set the item contained inside the ItemHeated wrapper class. ONLY HEATED ITEMS!
	 * @param stack
	 * @param heatedItem
	 * @return
	 */
	public static boolean setHeatedItem(ItemStack stack, ItemStack heatedItem)
	{
		if(isHeatedItem(stack))
		{
			if(!stack.hasTagCompound())
			{
				stack.stackTagCompound = new NBTTagCompound();
			}
			
			NBTTagCompound compound = new NBTTagCompound();
			heatedItem.writeToNBT(compound);
			stack.getTagCompound().setTag("HeatedItem", compound);
		}
		
		return true;
	}
	
	public static ItemStack removeHeat(ItemStack stack)
	{
		if(isHeatedItem(stack))
		{
			return ItemHeated.getHeatedItem(stack).copy();
		}
		
		return null;
	}
	
	public static boolean isHeatedItem(ItemStack stack)
	{
		return stack.getItem() instanceof ItemHeated;
	}
	
	public static void setType(ItemStack stack, EnumHeatableType type)
	{
		if(isHeatedItem(stack))
		{
			stack.getTagCompound().setByte("Type", (byte)type.ordinal());
		}
	}
	
	public static int getType(ItemStack stack)
	{
		if(isHeatedItem(stack))
		{
			if(stack.getTagCompound().hasKey("Type"))
			{
				return stack.getTagCompound().getByte("Type");
			}
		}
		
		return 0;
	}
}
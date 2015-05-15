package se.rimevel.FeudalFunctions.core.util;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;

/**
 * Utility class for tool materials. Add your own here so you have them in one place.
 */
public enum UtilToolMaterials
{
	STONE,
	COPPER,
	TIN,
	BRONZE,
	IRON,
	GOLD,
	STEEL,
	DIAMOND;
	
	private Item.ToolMaterial material;
	
	public Item.ToolMaterial getMaterial()
	{
		return this.material;	
	}
	
	public static void init()
	{
		STONE.material = Item.ToolMaterial.STONE;
		COPPER.material = EnumHelper.addToolMaterial("COPPER", 0, 80, 4.0F, 1.0F, 5);
		TIN.material = EnumHelper.addToolMaterial("TIN", 0, 40, 4.0F, 1.0F, 15);
		BRONZE.material = EnumHelper.addToolMaterial("BRONZE", 1, 150, 4.0F, 2.0F, 10);
		IRON.material = Item.ToolMaterial.IRON;
		GOLD.material = Item.ToolMaterial.GOLD;
		STEEL.material = EnumHelper.addToolMaterial("STEEL", 3, 1000, 7.0F, 3.0F, 8);
		DIAMOND.material = Item.ToolMaterial.EMERALD;
		
		//Custom material example:
		//MATERIALNAME.material = EnumHelper.addToolMaterial("MATERIALNAME", 0, 32, 1.0F, 1.0F, 1);
	}
	
	public static void setRepairMaterials()
	{	
		COPPER.material.setRepairItem(new ItemStack(MSmithing.items.ingot.getInstance(), 1, 0));
		TIN.material.setRepairItem(new ItemStack(MSmithing.items.ingot.getInstance(), 1, 1));
		BRONZE.material.setRepairItem(new ItemStack(MSmithing.items.ingot.getInstance(), 1, 2));
		STEEL.material.setRepairItem(new ItemStack(MSmithing.items.ingot.getInstance(), 1, 3));
	}
}

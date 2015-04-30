package se.rimevel.FeudalFunctions.core.util;

import net.minecraft.item.Item;

/**
 * Utility class for tool materials. Add your own here so you have them in one place.
 */
public enum UtilToolMaterials
{
	STONE,
	IRON,
	GOLD,
	DIAMOND;
	
	private Item.ToolMaterial material;
	
	public Item.ToolMaterial getMaterial()
	{
		return this.material;	
	}
	
	public static void init()
	{
		STONE.material = Item.ToolMaterial.STONE;
		IRON.material = Item.ToolMaterial.IRON;
		GOLD.material = Item.ToolMaterial.GOLD;
		DIAMOND.material = Item.ToolMaterial.EMERALD;
		
		//Custom material example:
		//MATERIALNAME.material = EnumHelper.addToolMaterial("MATERIALNAME", 0, 32, 1.0F, 1.0F, 1);
	}
}

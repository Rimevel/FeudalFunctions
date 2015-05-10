package se.rimevel.FeudalFunctions.modules.smithing.items;

import net.minecraft.item.Item.ToolMaterial;
import se.rimevel.FeudalFunctions.core.items.ModItem;
import se.rimevel.FeudalFunctions.core.util.UtilToolMaterials;
import se.rimevel.FeudalFunctions.modules.smithing.items.tools.ItemToolHammer;

public class SmithingItems
{
	public final static ModItem<ItemIngot> ingot = new ModItem<ItemIngot>("ingot", ItemIngot.class);
	public final static ModItem<ItemHeated> heated_item = new ModItem<ItemHeated>("heated", ItemHeated.class);
	public final static ModItem<ItemParts> parts_copper = new ModItem<ItemParts>("parts_copper", ItemParts.class);
	public final static ModItem<ItemParts> parts_bronze = new ModItem<ItemParts>("parts_bronze", ItemParts.class);
	public final static ModItem<ItemParts> parts_iron = new ModItem<ItemParts>("parts_iron", ItemParts.class);
	//public final static ModItem<ItemParts> parts_gold = new ModItem<ItemParts>("parts_gold", ItemParts.class);
	public final static ModItem<ItemParts> parts_steel = new ModItem<ItemParts>("parts_steel", ItemParts.class);
	public final static ModItem<ItemToolHammer> tool_hammer_stone = new ModItem<ItemToolHammer>("tool_hammer_stone", ItemToolHammer.class, 1F, UtilToolMaterials.STONE, "smallStone");
}

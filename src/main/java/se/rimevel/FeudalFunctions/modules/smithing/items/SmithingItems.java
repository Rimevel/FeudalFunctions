package se.rimevel.FeudalFunctions.modules.smithing.items;

import net.minecraft.item.Item.ToolMaterial;
import se.rimevel.FeudalFunctions.core.items.ItemArmorBase;
import se.rimevel.FeudalFunctions.core.items.ModItem;
import se.rimevel.FeudalFunctions.core.util.UtilArmorMaterials;
import se.rimevel.FeudalFunctions.core.util.UtilToolMaterials;
import se.rimevel.FeudalFunctions.modules.smithing.items.tools.ItemToolAxe;
import se.rimevel.FeudalFunctions.modules.smithing.items.tools.ItemToolHammer;
import se.rimevel.FeudalFunctions.modules.smithing.items.tools.ItemToolHoe;
import se.rimevel.FeudalFunctions.modules.smithing.items.tools.ItemToolPickaxe;
import se.rimevel.FeudalFunctions.modules.smithing.items.tools.ItemToolShovel;
import se.rimevel.FeudalFunctions.modules.smithing.items.weapons.WeaponSword;

public class SmithingItems
{
	public final static ModItem<ItemIngot> ingot = new ModItem<ItemIngot>("ingot", ItemIngot.class);
	public final static ModItem<ItemHeated> heated_item = new ModItem<ItemHeated>("heated", ItemHeated.class);
	public final static ModItem<ItemBloom> bloom = new ModItem<ItemBloom>("bloom", ItemBloom.class); 
	
	public final static ModItem<ItemParts> parts_copper = new ModItem<ItemParts>("parts_copper", ItemParts.class);
	public final static ModItem<ItemParts> parts_bronze = new ModItem<ItemParts>("parts_bronze", ItemParts.class);
	public final static ModItem<ItemParts> parts_iron = new ModItem<ItemParts>("parts_iron", ItemParts.class);
	public final static ModItem<ItemParts> parts_gold = new ModItem<ItemParts>("parts_gold", ItemParts.class);
	public final static ModItem<ItemParts> parts_steel = new ModItem<ItemParts>("parts_steel", ItemParts.class);
	
	public final static ModItem<ItemToolHammer> tool_hammer_stone = new ModItem<ItemToolHammer>("tool_hammer_stone", ItemToolHammer.class, 1F, UtilToolMaterials.STONE, "smallStone");
	public final static ModItem<ItemToolHammer> tool_hammer_copper = new ModItem<ItemToolHammer>("tool_hammer_copper", ItemToolHammer.class, 1F, UtilToolMaterials.COPPER, "ingotCopper");
	public final static ModItem<ItemToolHammer> tool_hammer_bronze = new ModItem<ItemToolHammer>("tool_hammer_bronze", ItemToolHammer.class, 1F, UtilToolMaterials.BRONZE, "ingotBronze");
	public final static ModItem<ItemToolHammer> tool_hammer_iron = new ModItem<ItemToolHammer>("tool_hammer_iron", ItemToolHammer.class, 1F, UtilToolMaterials.IRON, "ingotIron");
	public final static ModItem<ItemToolHammer> tool_hammer_gold = new ModItem<ItemToolHammer>("tool_hammer_gold", ItemToolHammer.class, 1F, UtilToolMaterials.GOLD, "ingotGold");
	public final static ModItem<ItemToolHammer> tool_hammer_steel = new ModItem<ItemToolHammer>("tool_hammer_steel", ItemToolHammer.class, 1F, UtilToolMaterials.STEEL, "ingotSteel");
	public final static ModItem<ItemToolHammer> tool_hammer_diamond = new ModItem<ItemToolHammer>("tool_hammer_diamond", ItemToolHammer.class, 1F, UtilToolMaterials.DIAMOND, "ingotDiamond");
	
	public final static ModItem<ItemToolAxe> tool_axe_copper = new ModItem<ItemToolAxe>("tool_axe_copper", ItemToolAxe.class, 1F, UtilToolMaterials.COPPER, "ingotCopper");
	public final static ModItem<ItemToolAxe> tool_axe_bronze = new ModItem<ItemToolAxe>("tool_axe_bronze", ItemToolAxe.class, 1F, UtilToolMaterials.BRONZE, "ingotBronze");
	public final static ModItem<ItemToolAxe> tool_axe_steel = new ModItem<ItemToolAxe>("tool_axe_steel", ItemToolAxe.class, 1F, UtilToolMaterials.STEEL, "ingotSteel");
	
	public final static ModItem<ItemToolPickaxe> tool_pickaxe_copper = new ModItem<ItemToolPickaxe>("tool_pickaxe_copper", ItemToolPickaxe.class, 1F, UtilToolMaterials.COPPER, "ingotCopper");
	public final static ModItem<ItemToolPickaxe> tool_pickaxe_bronze = new ModItem<ItemToolPickaxe>("tool_pickaxe_bronze", ItemToolPickaxe.class, 1F, UtilToolMaterials.BRONZE, "ingotBronze");
	public final static ModItem<ItemToolPickaxe> tool_pickaxe_steel = new ModItem<ItemToolPickaxe>("tool_pickaxe_steel", ItemToolPickaxe.class, 1F, UtilToolMaterials.STEEL, "ingotSteel");
	
	public final static ModItem<ItemToolHoe> tool_hoe_copper = new ModItem<ItemToolHoe>("tool_hoe_copper", ItemToolHoe.class, 0F, UtilToolMaterials.COPPER, "ingotCopper");
	public final static ModItem<ItemToolHoe> tool_hoe_bronze = new ModItem<ItemToolHoe>("tool_hoe_bronze", ItemToolHoe.class, 0F, UtilToolMaterials.BRONZE, "ingotBronze");
	public final static ModItem<ItemToolHoe> tool_hoe_steel = new ModItem<ItemToolHoe>("tool_hoe_steel", ItemToolHoe.class, 0F, UtilToolMaterials.STEEL, "ingotSteel");
	
	public final static ModItem<ItemToolShovel> tool_shovel_copper = new ModItem<ItemToolShovel>("tool_shovel_copper", ItemToolShovel.class, 1F, UtilToolMaterials.COPPER, "ingotCopper");
	public final static ModItem<ItemToolShovel> tool_shovel_bronze = new ModItem<ItemToolShovel>("tool_shovel_bronze", ItemToolShovel.class, 1F, UtilToolMaterials.BRONZE, "ingotBronze");
	public final static ModItem<ItemToolShovel> tool_shovel_steel = new ModItem<ItemToolShovel>("tool_shovel_steel", ItemToolShovel.class, 1F, UtilToolMaterials.STEEL, "ingotSteel");
	
	public final static ModItem<WeaponSword> weapon_sword_copper = new ModItem<WeaponSword>("weapon_sword_copper", WeaponSword.class, 2F, UtilToolMaterials.COPPER, "ingotCopper");
	public final static ModItem<WeaponSword> weapon_sword_bronze = new ModItem<WeaponSword>("weapon_sword_bronze", WeaponSword.class, 2F, UtilToolMaterials.BRONZE, "ingotBronze");
	public final static ModItem<WeaponSword> weapon_sword_steel = new ModItem<WeaponSword>("weapon_sword_steel", WeaponSword.class, 2F, UtilToolMaterials.STEEL, "ingotSteel");
	
	public final static ModItem<ItemArmorBasic> armor_copper_helmet = new ModItem<ItemArmorBasic>("armor_copper_helmet", ItemArmorBasic.class, UtilArmorMaterials.COPPER, 0, "armor_copper");
	public final static ModItem<ItemArmorBasic> armor_copper_chest = new ModItem<ItemArmorBasic>("armor_copper_chest", ItemArmorBasic.class, UtilArmorMaterials.COPPER, 1, "armor_copper");
	public final static ModItem<ItemArmorBasic> armor_copper_legs = new ModItem<ItemArmorBasic>("armor_copper_legs", ItemArmorBasic.class, UtilArmorMaterials.COPPER, 2, "armor_copper");
	public final static ModItem<ItemArmorBasic> armor_copper_boots = new ModItem<ItemArmorBasic>("armor_copper_boots", ItemArmorBasic.class, UtilArmorMaterials.COPPER, 3, "armor_copper");
	
	public final static ModItem<ItemArmorBasic> armor_bronze_helmet = new ModItem<ItemArmorBasic>("armor_bronze_helmet", ItemArmorBasic.class, UtilArmorMaterials.BRONZE, 0, "armor_bronze");
	public final static ModItem<ItemArmorBasic> armor_bronze_chest = new ModItem<ItemArmorBasic>("armor_bronze_chest", ItemArmorBasic.class, UtilArmorMaterials.BRONZE, 1, "armor_bronze");
	public final static ModItem<ItemArmorBasic> armor_bronze_legs = new ModItem<ItemArmorBasic>("armor_bronze_legs", ItemArmorBasic.class, UtilArmorMaterials.BRONZE, 2, "armor_bronze");
	public final static ModItem<ItemArmorBasic> armor_bronze_boots = new ModItem<ItemArmorBasic>("armor_bronze_boots", ItemArmorBasic.class, UtilArmorMaterials.BRONZE, 3, "armor_bronze");
	
	public final static ModItem<ItemArmorBasic> armor_steel_helmet = new ModItem<ItemArmorBasic>("armor_steel_helmet", ItemArmorBasic.class, UtilArmorMaterials.STEEL, 0, "armor_steel");
	public final static ModItem<ItemArmorBasic> armor_steel_chest = new ModItem<ItemArmorBasic>("armor_steel_chest", ItemArmorBasic.class, UtilArmorMaterials.STEEL, 1, "armor_steel");
	public final static ModItem<ItemArmorBasic> armor_steel_legs = new ModItem<ItemArmorBasic>("armor_steel_legs", ItemArmorBasic.class, UtilArmorMaterials.STEEL, 2, "armor_steel");
	public final static ModItem<ItemArmorBasic> armor_steel_boots = new ModItem<ItemArmorBasic>("armor_steel_boots", ItemArmorBasic.class, UtilArmorMaterials.STEEL, 3, "armor_steel");
}

package se.rimevel.FeudalFunctions.modules.smithing.crafting.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.xml.internal.ws.api.server.Module;

import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.RecipeAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.HeatedStack;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemHeated;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemParts;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataAnvilArray;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataHeatableList;
import se.rimevel.FeudalFunctions.modules.smithing.util.EnumHeatableType;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeListAnvil
{
	static ArrayList<RecipeAnvil> recipes;
	
	public static final int WILDCARD_VALUE = 32767;
	
	public static ArrayList<RecipeAnvil> getRecipeList()
	{
		if(recipes == null)
		{
			recipes = new ArrayList<RecipeAnvil>();
		}
		return RecipeListAnvil.recipes;
	}
	
	public static void addDefaultRecipes()
	{
		DataAnvilArray data[] = {
			new DataAnvilArray(
				MSmithing.items.parts_copper.getInstance(),
				"Copper",
				20,
				new ItemStack(MSmithing.items.ingot.getInstance(), 1, 0)
			),
			new DataAnvilArray(
				MSmithing.items.parts_bronze.getInstance(),
				"Bronze",
				50,
				new ItemStack(MSmithing.items.ingot.getInstance(), 1, 2)
			),
			new DataAnvilArray(
				MSmithing.items.parts_iron.getInstance(),
				"Iron",
				60,
				new ItemStack(Items.iron_ingot, 1)
			),
			new DataAnvilArray(
				MSmithing.items.parts_gold.getInstance(),
				"Gold",
				40,
				new ItemStack(Items.gold_ingot, 1)
			),
			new DataAnvilArray(
				MSmithing.items.parts_steel.getInstance(),
				"Steel",
				80,
				new ItemStack(MSmithing.items.ingot.getInstance(), 1, 3)
			)
		};
		
		int listN = 0;
		EnumHeatableType type;
		String oreName;
		
		for (DataAnvilArray d : data)
		{
			//AXE_HEAD
			
			type = EnumHeatableType.AXE_HEAD;
			RecipeListAnvil.addRecipe(d.item.get().getPart(type),
				
				"ii",
				" i",
				
			new HeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "AxeHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//PICKAXE_HEAD
			
			type = EnumHeatableType.PICKAXE_HEAD;
			RecipeListAnvil.addRecipe(d.item.get().getPart(type),
				
				"iii",
				
			new HeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "PickaxeHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//HOE_HEAD
			
			type = EnumHeatableType.HOE_HEAD;
			RecipeListAnvil.addRecipe(d.item.get().getPart(type),
				
				"ii",
				
			new HeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "HoeHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//SHOVEL_HEAD
			
			type = EnumHeatableType.SHOVEL_HEAD;
			RecipeListAnvil.addRecipe(d.item.get().getPart(type),
				
				"i",
				
			new HeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "ShovelHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//SWORD_BLADE
			
			type = EnumHeatableType.SWORD_BLADE;
			RecipeListAnvil.addRecipe(d.item.get().getPart(type),
				
				" i",
				"i ",
				
			new HeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "SwordBlade";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//HAMMER_HEAD
					
			type = EnumHeatableType.HAMMER_HEAD;
			RecipeListAnvil.addRecipe(d.item.get().getPart(type),
				
				"iii",
				"iii",
				
			new HeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "HammerHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			listN++;
		}
		
		//COPPER
		
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_axe_copper.getInstance()),
				
			MSmithing.items.parts_copper.getInstance().getPart(EnumHeatableType.AXE_HEAD),
			new ItemStack(Items.stick)
			
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_pickaxe_copper.getInstance()),
					
			MSmithing.items.parts_copper.getInstance().getPart(EnumHeatableType.PICKAXE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hoe_copper.getInstance()),
					
			MSmithing.items.parts_copper.getInstance().getPart(EnumHeatableType.HOE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.weapon_sword_copper.getInstance()),
					
			MSmithing.items.parts_copper.getInstance().getPart(EnumHeatableType.SWORD_BLADE),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_shovel_copper.getInstance()),
					
			MSmithing.items.parts_copper.getInstance().getPart(EnumHeatableType.SHOVEL_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hammer_copper.getInstance()),
						
			MSmithing.items.parts_copper.getInstance().getPart(EnumHeatableType.HAMMER_HEAD),
			new ItemStack(Items.stick)
					
		);
		
		//BRONZE
		
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_axe_bronze.getInstance()),
				
			MSmithing.items.parts_bronze.getInstance().getPart(EnumHeatableType.AXE_HEAD),
			new ItemStack(Items.stick)
			
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_pickaxe_bronze.getInstance()),
					
			MSmithing.items.parts_bronze.getInstance().getPart(EnumHeatableType.PICKAXE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hoe_bronze.getInstance()),
					
			MSmithing.items.parts_bronze.getInstance().getPart(EnumHeatableType.HOE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.weapon_sword_bronze.getInstance()),
					
			MSmithing.items.parts_bronze.getInstance().getPart(EnumHeatableType.SWORD_BLADE),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_shovel_bronze.getInstance()),
					
			MSmithing.items.parts_bronze.getInstance().getPart(EnumHeatableType.SHOVEL_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hammer_bronze.getInstance()),
						
			MSmithing.items.parts_bronze.getInstance().getPart(EnumHeatableType.HAMMER_HEAD),
			new ItemStack(Items.stick)
					
		);
		
		//STEEL
		
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_axe_steel.getInstance()),
				
			MSmithing.items.parts_steel.getInstance().getPart(EnumHeatableType.AXE_HEAD),
			new ItemStack(Items.stick)
			
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_pickaxe_steel.getInstance()),
					
			MSmithing.items.parts_steel.getInstance().getPart(EnumHeatableType.PICKAXE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hoe_steel.getInstance()),
					
			MSmithing.items.parts_steel.getInstance().getPart(EnumHeatableType.HOE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.weapon_sword_steel.getInstance()),
					
			MSmithing.items.parts_steel.getInstance().getPart(EnumHeatableType.SWORD_BLADE),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_shovel_steel.getInstance()),
					
			MSmithing.items.parts_steel.getInstance().getPart(EnumHeatableType.SHOVEL_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hammer_steel.getInstance()),
						
			MSmithing.items.parts_steel.getInstance().getPart(EnumHeatableType.HAMMER_HEAD),
			new ItemStack(Items.stick)
					
		);
		
		//IRON
		
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.iron_axe),
				
			MSmithing.items.parts_iron.getInstance().getPart(EnumHeatableType.AXE_HEAD),
			new ItemStack(Items.stick)
			
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.iron_pickaxe),
					
			MSmithing.items.parts_iron.getInstance().getPart(EnumHeatableType.PICKAXE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.iron_hoe),
					
			MSmithing.items.parts_iron.getInstance().getPart(EnumHeatableType.HOE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.iron_sword),
					
			MSmithing.items.parts_iron.getInstance().getPart(EnumHeatableType.SWORD_BLADE),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.iron_shovel),
					
			MSmithing.items.parts_iron.getInstance().getPart(EnumHeatableType.SHOVEL_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hammer_iron.getInstance()),
						
			MSmithing.items.parts_iron.getInstance().getPart(EnumHeatableType.HAMMER_HEAD),
			new ItemStack(Items.stick)
					
		);
		
		//GOLD
		
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.golden_axe),
				
			MSmithing.items.parts_gold.getInstance().getPart(EnumHeatableType.AXE_HEAD),
			new ItemStack(Items.stick)
			
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.golden_pickaxe),
					
			MSmithing.items.parts_gold.getInstance().getPart(EnumHeatableType.PICKAXE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.golden_hoe),
					
			MSmithing.items.parts_gold.getInstance().getPart(EnumHeatableType.HOE_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.golden_sword),
					
			MSmithing.items.parts_gold.getInstance().getPart(EnumHeatableType.SWORD_BLADE),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(Items.golden_shovel),
					
			MSmithing.items.parts_gold.getInstance().getPart(EnumHeatableType.SHOVEL_HEAD),
			new ItemStack(Items.stick)
				
		);
		CraftingManager.getInstance().addShapelessRecipe(
				
			new ItemStack(MSmithing.items.tool_hammer_gold.getInstance()),
							
			MSmithing.items.parts_gold.getInstance().getPart(EnumHeatableType.HAMMER_HEAD),
			new ItemStack(Items.stick)
						
		);
	}
	
	public static RecipeAnvil addRecipe(ItemStack toBeCrafted, Object ... array)
	{
		String full = "";
		int i = 0;
		int x = 0;
		int y = 0;
		
		if(array[i] instanceof String[])
		{
			String[] string = (String[])((String[])array[i++]);
			
			for (int width = 0; width < string.length; width++)
			{
				String s_1 = string[width];
				y++;
				x = s_1.length();
				full = full + s_1;
			}
		}
		else
		{
			while(array[i] instanceof String)
			{
				String s_2 = (String)array[i++];
				y++;
				x = s_2.length();
				full = full + s_2;
			}
		}
		
		HashMap map;
		
		for (map = new HashMap(); i < array.length; i += 1) //1 or 2? Default 1.
		{
			HeatedStack stack = (HeatedStack)array[i];
			
			map.put(stack.symbol, stack);
		}
		
		HeatedStack[] matrix = new HeatedStack[x * y];
		
		for(int temp = 0; temp < x * y; temp++)
		{
			char c = full.charAt(temp);
			
			if(map.containsKey(Character.valueOf(c)))
			{
				matrix[temp] = ((HeatedStack)map.get(Character.valueOf(c))).copy();
			}
			else
			{
				matrix[temp] = null;
			}
		}
		
		RecipeAnvil recipe = new RecipeAnvil(x, y, matrix, toBeCrafted);
		RecipeListAnvil.getRecipeList().add(recipe);
		return recipe;
	}
	
	public static ItemStack matchRecipe(IInventory inventory, World world)
	{
		for(int i = 0; i < RecipeListAnvil.getRecipeList().size(); i++)
		{
			RecipeAnvil recipe = RecipeListAnvil.getRecipeList().get(i);
				
			if(recipe.matches(inventory, world))
			{
				return recipe.getCraftingResult(inventory);
			}
		}
			
		return null;
	}
}

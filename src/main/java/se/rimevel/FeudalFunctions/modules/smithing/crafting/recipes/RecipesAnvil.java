package se.rimevel.FeudalFunctions.modules.smithing.crafting.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.xml.internal.ws.api.server.Module;

import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.CraftingAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.CraftingHeatedStack;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemHeated;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemParts;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataAnvilArray;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataHeatableList;
import se.rimevel.FeudalFunctions.modules.smithing.util.EnumHeatableType;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesAnvil
{
	static ArrayList<CraftingAnvil> recipes;
	
	public static final int WILDCARD_VALUE = 32767;
	
	public static ArrayList<CraftingAnvil> getRecipeList()
	{
		if(recipes == null)
		{
			recipes = new ArrayList<CraftingAnvil>();
		}
		return RecipesAnvil.recipes;
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
			RecipesAnvil.addRecipe(d.item.get().getPart(type),
				
				"ii",
				" i",
				
			new CraftingHeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "AxeHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//PICKAXE_HEAD
			
			type = EnumHeatableType.PICKAXE_HEAD;
			RecipesAnvil.addRecipe(d.item.get().getPart(type),
				
				"iii",
				
			new CraftingHeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "PickaxeHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//HOE_HEAD
			
			type = EnumHeatableType.HOE_HEAD;
			RecipesAnvil.addRecipe(d.item.get().getPart(type),
				
				"ii",
				
			new CraftingHeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "HoeHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//SHOVEL_HEAD
			
			type = EnumHeatableType.SHOVEL_HEAD;
			RecipesAnvil.addRecipe(d.item.get().getPart(type),
				
				"i",
				
			new CraftingHeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "ShovelHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//SWORD_BLADE
			
			type = EnumHeatableType.SWORD_BLADE;
			RecipesAnvil.addRecipe(d.item.get().getPart(type),
				
				"i",
				"i",
				
			new CraftingHeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "SwordBlade";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			//HAMMER_HEAD
					
			type = EnumHeatableType.HAMMER_HEAD;
			RecipesAnvil.addRecipe(d.item.get().getPart(type),
				
				"iii",
				"iii",
				
			new CraftingHeatedStack('i', d.materialStack, true));
			oreName = "part" + d.materialName + "HammerHead";
			OreDictionary.registerOre(oreName, d.item.get().getPart(type));
			DataHeatableList.addItem(oreName, d.heatTime, type);
			
			listN++;
		}
	}
	
	public static CraftingAnvil addRecipe(ItemStack toBeCrafted, Object ... array)
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
		
		for (map = new HashMap(); i < array.length; i += 2) //1 or 2? Default 1.
		{
			CraftingHeatedStack stack = (CraftingHeatedStack)array[i];
			
			map.put(stack.symbol, stack);
		}
		
		CraftingHeatedStack[] matrix = new CraftingHeatedStack[x * y];
		
		for(int temp = 0; temp < x * y; temp++)
		{
			char c = full.charAt(temp);
			
			if(map.containsKey(Character.valueOf(c)))
			{
				matrix[temp] = ((CraftingHeatedStack)map.get(Character.valueOf(c))).copy();
			}
			else
			{
				matrix[temp] = null;
			}
		}
		
		CraftingAnvil recipe = new CraftingAnvil(x, y, matrix, toBeCrafted);
		RecipesAnvil.getRecipeList().add(recipe);
		return recipe;
	}
	
	public static ItemStack matchRecipe(IInventory inventory, World world)
	{
		for(int i = 0; i < RecipesAnvil.getRecipeList().size(); i++)
		{
			CraftingAnvil recipe = RecipesAnvil.getRecipeList().get(i);
				
			if(recipe.matches(inventory, world))
			{
				return recipe.getCraftingResult(inventory);
			}
		}
			
		return null;
	}
}

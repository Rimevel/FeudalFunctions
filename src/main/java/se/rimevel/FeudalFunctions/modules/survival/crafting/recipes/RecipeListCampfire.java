package se.rimevel.FeudalFunctions.modules.survival.crafting.recipes;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import se.rimevel.FeudalFunctions.modules.survival.crafting.RecipeCampfire;

public class RecipeListCampfire
{
	static ArrayList<RecipeCampfire> recipes;
	
	public static final int WILDCARD_VALUE = 32767;
	
	public static ArrayList<RecipeCampfire> getRecipeList()
	{
		if(recipes == null)
		{
			recipes = new ArrayList<RecipeCampfire>();
		}
		return RecipeListCampfire.recipes;
	}
	
	public static void addDefaultRecipes()
	{
		RecipeListCampfire.addRecipe(new ItemStack(Items.beef), new ItemStack(Items.cooked_beef), 60);
		RecipeListCampfire.addRecipe(new ItemStack(Items.porkchop), new ItemStack(Items.cooked_porkchop), 60);
		RecipeListCampfire.addRecipe(new ItemStack(Items.chicken), new ItemStack(Items.cooked_chicken), 60);
		RecipeListCampfire.addRecipe(new ItemStack(Items.fish, 1, 0), new ItemStack(Items.cooked_fished), 60);
		RecipeListCampfire.addRecipe(new ItemStack(Blocks.clay), new ItemStack(Blocks.hardened_clay), 120);
	}
	
	public static void addRecipe(ItemStack toCook, ItemStack result, int time)
	{
		if(toCook == null || result == null || time <= 0){return;}
		
		RecipeListCampfire.getRecipeList().add(new RecipeCampfire(toCook, result, time));
	}
}

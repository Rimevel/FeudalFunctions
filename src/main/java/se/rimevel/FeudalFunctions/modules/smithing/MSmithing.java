package se.rimevel.FeudalFunctions.modules.smithing;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;
import se.rimevel.FeudalFunctions.core.modules.MModule;
import se.rimevel.FeudalFunctions.core.ui.GuiWrapper;
import se.rimevel.FeudalFunctions.modules.smithing.blocks.SmithingBlocks;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.recipes.RecipeListAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemBloom;
import se.rimevel.FeudalFunctions.modules.smithing.items.SmithingItems;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.RenderAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.RenderBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.RenderCrucible;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelCrucible;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityCrucible;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityForge;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerForge;
import se.rimevel.FeudalFunctions.modules.smithing.ui.gui.GuiAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.ui.gui.GuiBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.ui.gui.GuiForge;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataHeatableList;
import se.rimevel.FeudalFunctions.modules.smithing.util.EnumHeatableType;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class MSmithing extends MModule
{
	public static SmithingBlocks blocks = new SmithingBlocks();
	public static SmithingItems items = new SmithingItems();
	
	@Override
	public void onClientProxy()
	{
		final ModelCrucible modelCrucible = new ModelCrucible();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCrucible.class, new RenderCrucible(modelCrucible));
		MinecraftForgeClient.registerItemRenderer(new ItemStack(blocks.crucible.getInstance()).getItem(), new RenderCrucible(modelCrucible));
		
		final ModelAnvil modelAnvil = new ModelAnvil();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnvil.class, new RenderAnvil(modelAnvil));
		MinecraftForgeClient.registerItemRenderer(new ItemStack(blocks.anvil.getInstance()).getItem(), new RenderAnvil(modelAnvil));
		
		final ModelBloomery modelBloomery = new ModelBloomery();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBloomery.class, new RenderBloomery(modelBloomery));
		MinecraftForgeClient.registerItemRenderer(new ItemStack(blocks.bloomery.getInstance()).getItem(), new RenderBloomery(modelBloomery));
	}
	
	@Override
	public void onPostInit(FMLPostInitializationEvent event)
	{
		DataHeatableList.addItem("ingotCopper", 20, EnumHeatableType.INGOT);
		DataHeatableList.addItem("ingotTin", 10, EnumHeatableType.INGOT);
		DataHeatableList.addItem("ingotBronze", 50, EnumHeatableType.INGOT);
		DataHeatableList.addItem("ingotIron", 60, EnumHeatableType.INGOT);
		DataHeatableList.addItem("ingotGold", 40, EnumHeatableType.INGOT);
		DataHeatableList.addItem("ingotSteel", 80, EnumHeatableType.INGOT);
		
		OreDictionary.registerOre("oreCopper", new ItemStack(blocks.ore.getInstance(), 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(blocks.ore.getInstance(), 1, 1));
		
		RecipeListAnvil.addDefaultRecipes();
		ItemBloom.initOreData();
		
		CraftingManager m = CraftingManager.getInstance();
		
		//Bloomery recipe
		m.addRecipe(new ItemStack(blocks.bloomery.getInstance()),
			
			"ccc",
			"c c",
			"ccc",
			
		'c', new ItemStack(Blocks.hardened_clay));
	}
	
	@Override
	public ArrayList<GuiWrapper> getGuis()
	{
		ArrayList<GuiWrapper> list = new ArrayList<GuiWrapper>();
		list.add(new GuiWrapper(0, ContainerForge.class, GuiForge.class, TileEntityForge.class));
		list.add(new GuiWrapper(1, ContainerAnvil.class, GuiAnvil.class, TileEntityAnvil.class));
		list.add(new GuiWrapper(2, ContainerBloomery.class, GuiBloomery.class, TileEntityBloomery.class));
		return list;
	}
}

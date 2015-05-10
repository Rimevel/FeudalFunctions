package se.rimevel.FeudalFunctions.modules.smithing;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import se.rimevel.FeudalFunctions.core.modules.MModule;
import se.rimevel.FeudalFunctions.core.ui.GuiWrapper;
import se.rimevel.FeudalFunctions.modules.smithing.blocks.SmithingBlocks;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.recipes.RecipeListAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.items.SmithingItems;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.RenderAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.RenderCrucible;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelCrucible;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityCrucible;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityForge;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerForge;
import se.rimevel.FeudalFunctions.modules.smithing.ui.gui.GuiAnvil;
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
		
		RecipeListAnvil.addDefaultRecipes();
	}
	
	@Override
	public ArrayList<GuiWrapper> getGuis()
	{
		ArrayList<GuiWrapper> list = new ArrayList<GuiWrapper>();
		list.add(new GuiWrapper(0, ContainerForge.class, GuiForge.class, TileEntityForge.class));
		list.add(new GuiWrapper(1, ContainerAnvil.class, GuiAnvil.class, TileEntityAnvil.class));
		return list;
	}
}

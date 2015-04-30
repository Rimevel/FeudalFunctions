package se.rimevel.FeudalFunctions.modules.survival;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import se.rimevel.FeudalFunctions.core.modules.MModule;
import se.rimevel.FeudalFunctions.core.ui.GuiWrapper;
import se.rimevel.FeudalFunctions.modules.survival.blocks.SurvivalBlocks;
import se.rimevel.FeudalFunctions.modules.survival.items.SurvivalItems;
import se.rimevel.FeudalFunctions.modules.survival.renderers.RenderCampfire;
import se.rimevel.FeudalFunctions.modules.survival.renderers.RenderGround;
import se.rimevel.FeudalFunctions.modules.survival.renderers.models.ModelCampfire;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityCampfire;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityGround;
import se.rimevel.FeudalFunctions.modules.survival.world.GenerationHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

public class MSurvival extends MModule
{
	public static SurvivalBlocks blocks = new SurvivalBlocks();
	public static SurvivalItems items = new SurvivalItems();
	
	@Override
	public void onInit(FMLInitializationEvent event)
	{
		new GenerationHandler();
	}
	
	@Override
	public void onClientProxy()
	{
		final ModelCampfire modelCampfire = new ModelCampfire();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new RenderCampfire(modelCampfire));
		MinecraftForgeClient.registerItemRenderer(new ItemStack(blocks.campfire.getInstance()).getItem(), new RenderCampfire(modelCampfire));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGround.class, new RenderGround());
	}
	
	@Override
	public ArrayList<GuiWrapper> getGuis()
	{
		ArrayList<GuiWrapper> guis = new ArrayList<GuiWrapper>();
		
		return guis;
	}
}

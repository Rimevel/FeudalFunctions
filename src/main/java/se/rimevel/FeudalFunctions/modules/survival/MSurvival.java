package se.rimevel.FeudalFunctions.modules.survival;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import se.rimevel.FeudalFunctions.core.modules.MModule;
import se.rimevel.FeudalFunctions.core.potions.PotionHandler;
import se.rimevel.FeudalFunctions.core.ui.GuiWrapper;
import se.rimevel.FeudalFunctions.core.util.UtilArmorMaterials;
import se.rimevel.FeudalFunctions.modules.survival.blocks.SurvivalBlocks;
import se.rimevel.FeudalFunctions.modules.survival.crafting.recipes.RecipeListCampfire;
import se.rimevel.FeudalFunctions.modules.survival.events.SurvivalWorldEvents;
import se.rimevel.FeudalFunctions.modules.survival.items.SurvivalItems;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerSurvivalEvents;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerTickTemperature;
import se.rimevel.FeudalFunctions.modules.survival.potions.PotionFrostbite;
import se.rimevel.FeudalFunctions.modules.survival.potions.PotionSunstroke;
import se.rimevel.FeudalFunctions.modules.survival.renderers.RenderCampfire;
import se.rimevel.FeudalFunctions.modules.survival.renderers.RenderGround;
import se.rimevel.FeudalFunctions.modules.survival.renderers.models.ModelCampfire;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityCampfire;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityGround;
import se.rimevel.FeudalFunctions.modules.survival.ui.hud.HudStatus;
import se.rimevel.FeudalFunctions.modules.survival.world.GenerationHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

public class MSurvival extends MModule
{
	public static SurvivalBlocks blocks = new SurvivalBlocks();
	public static SurvivalItems items = new SurvivalItems();
	
	@Override
	public void onClientProxy()
	{
		final ModelCampfire modelCampfire = new ModelCampfire();
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new RenderCampfire(modelCampfire));
		MinecraftForgeClient.registerItemRenderer(new ItemStack(blocks.campfire.getInstance()).getItem(), new RenderCampfire(modelCampfire));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGround.class, new RenderGround());
		
		MinecraftForge.EVENT_BUS.register(new HudStatus(Minecraft.getMinecraft()));
	}
	
	@Override
	public void onInit(FMLInitializationEvent event)
	{
		new GenerationHandler();
		
		UtilArmorMaterials.setTemperatureModifers();
		
		MinecraftForge.EVENT_BUS.register(new PlayerSurvivalEvents());
		FMLCommonHandler.instance().bus().register(new PlayerTickTemperature());
		MinecraftForge.EVENT_BUS.register(new SurvivalWorldEvents());
	}
	
	@Override
	public void onPostInit(FMLPostInitializationEvent event)
	{
		PlayerTickTemperature.initBlockTempValues();
		
		RecipeListCampfire.addDefaultRecipes();
		
		CraftingManager m = CraftingManager.getInstance();
		
		m.addRecipe(new ItemStack(blocks.campfire.getInstance()),
				
				"ss",
				"ss",
				
			's', new ItemStack(Items.stick));
		
		addPotions();
	}
	
	@Override
	public ArrayList<GuiWrapper> getGuis()
	{
		ArrayList<GuiWrapper> guis = new ArrayList<GuiWrapper>();
		
		return guis;
	}
	
	@Override
	public int customPotionCount()
	{
		return 2;
	}
	
	private void addPotions()
	{
		new PotionFrostbite(PotionHandler.getInstance().getNextPotionId());
		new PotionSunstroke(PotionHandler.getInstance().getNextPotionId());
	}
}

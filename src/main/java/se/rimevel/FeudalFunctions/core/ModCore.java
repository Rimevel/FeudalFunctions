package se.rimevel.FeudalFunctions.core;

import java.lang.reflect.InvocationTargetException;

import se.rimevel.FeudalFunctions.core.blocks.ModBlock;
import se.rimevel.FeudalFunctions.core.config.ConfigHandler;
import se.rimevel.FeudalFunctions.core.items.ModItem;
import se.rimevel.FeudalFunctions.core.keys.KeyHandler;
import se.rimevel.FeudalFunctions.core.proxies.CommonProxy;
import se.rimevel.FeudalFunctions.core.ui.GuiHandler;
import se.rimevel.FeudalFunctions.core.util.UtilToolMaterials;
import se.rimevel.FeudalFunctions.modules.Modules;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModSettings.ID, version = ModSettings.VERSION, name = ModSettings.NAME)

public class ModCore
{
	@Instance(value = ModSettings.ID)
	public static ModCore instance;
	
	@SidedProxy(clientSide = ModSettings.PACKAGE_PATH + ".core.proxies.ClientProxy", serverSide = ModSettings.PACKAGE_PATH + ".core.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	public static ConfigHandler config;
	
	//public static NetworkWrapper channel = NetworkRegistry.INSTANCE.newChannel(ModSettings.ID);
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws
	NoSuchMethodException,
	SecurityException,
	InstantiationException,
	IllegalAccessException,
	IllegalArgumentException,
	InvocationTargetException
	{
		for (Modules m : Modules.values())
		{
			m.getModule().onPreInit(event);
		}
		
		//Load the config file.
		config = new ConfigHandler(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		//Check the config for values to use.
		FMLCommonHandler.instance().bus().register(config);
		
		UtilToolMaterials.init();
		
		for (Modules m : Modules.values())
		{
			m.getModule().onInit(event);
		}
		
		ModBlock.buildAllBlocks();
		ModItem.buildAllItems();
		
		new GuiHandler();
		
		proxy.register();
		
		KeyHandler.instance = new KeyHandler();
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		for (Modules m : Modules.values())
		{
			m.getModule().onPostInit(event);
		}
	}
}

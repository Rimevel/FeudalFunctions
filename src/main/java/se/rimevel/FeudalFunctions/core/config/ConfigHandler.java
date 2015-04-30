package se.rimevel.FeudalFunctions.core.config;

import se.rimevel.FeudalFunctions.core.ModSettings;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler
{
	/**
	 * Static holder for the configuration file.
	 */
	public static Configuration configFile;
	
	/**
	 * Handles and holds all configs for the mod.
	 * @param event
	 */
	public ConfigHandler(FMLPreInitializationEvent event)
	{
		ConfigHandler.configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}
	
	/**
	 * Event that handles changes to the mod config. Will only update the config for this mod.
	 * @param event
	 */
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.modID.equals(ModSettings.ID))
		{
			syncConfig();
		}
	}
	
	/**
	 * Saves the config if any changes have been made since the last save.
	 */
	public void syncConfig()
	{
		if(configFile.hasChanged())
		{
			configFile.save();
		}
	}
}

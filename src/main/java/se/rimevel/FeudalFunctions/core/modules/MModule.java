package se.rimevel.FeudalFunctions.core.modules;

import java.util.ArrayList;

import se.rimevel.FeudalFunctions.core.ui.GuiWrapper;
import net.minecraft.client.settings.KeyBinding;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This class is the base class for all modules. Extend this to create your own modules.
 * Modules are meant to separate different parts of a mod. In this class you can have your items, blocks,
 * tile entites, guis, etc. This is very useful when making large mods or mods where you want the end user
 * to be able to enable/disable parts of the mod. All modules are meant to be more or less separate from each
 * other and reference the core for general tasks.
 */
public class MModule
{
	public MModule(){}
	
	/**
	 * Gets run during the preInit phase.
	 */
	public void onPreInit(FMLPreInitializationEvent event)
	{
		
	}
	
	/**
	 * Gets run during the init phase.
	 */
	public void onInit(FMLInitializationEvent event)
	{
		
	}
	
	/**
	 * Gets run during the postInit phase.
	 */
	public void onPostInit(FMLPostInitializationEvent event)
	{
		
	}
	
	/**
	 * Gets run by the client when the proxy is called.
	 * 
	 */
	@SideOnly(Side.CLIENT)
	public void onClientProxy()
	{
		
	}
	
	/**
	 * Gets run by the server when the proxy is called.
	 */
	@SideOnly(Side.SERVER)
	public void onCommonProxy()
	{
		
	}

	/**
	 * Here you provide an arraylist populated with new gui wrappers. This is meant to help organize guis.
	 * @return
	 */
	public ArrayList<GuiWrapper> getGuis()
	{
		//ArrayList<GuiWrapper> list = new ArrayList<GuiWrapper>();
		//list.add(new GuiWrapper(0, SomeGuiContainer.class, SomeGui.class, tileEntity.class));
		//return list;
		
		return null;
	}
	
	/**
	 * Here you provide an arraylist populated with new KeyBinding elements. 
	 * @return
	 */
	public ArrayList<KeyBinding> getKeys()
	{
		//ArrayList<KeyBinding> list = new ArrayList<KeyBinding>();
		//list.add(KeyHandler.getNewKeyBinding("key.keyname", Keyboard.KEY_0, "modkeys.modname"));
		//return list;
		
		return null;
	}
}

package se.rimevel.FeudalFunctions.core.keys;

import java.util.ArrayList;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import se.rimevel.FeudalFunctions.modules.Modules;
import cpw.mods.fml.client.registry.ClientRegistry;

public class KeyHandler
{
	public static ArrayList<KeyBinding> keyList;
	
	public static KeyHandler instance;
	
	public KeyHandler()
	{
		keyList = new ArrayList<KeyBinding>();
		 
		for (Modules m : Modules.values())
		{
			if(m.getModule().getKeys() == null){continue;}
			 
			keyList.addAll(m.getModule().getKeys());
		}
		 
		for (KeyBinding key : keyList)
		{
			ClientRegistry.registerKeyBinding(key);
		}
	}
	
	/**
	 * Slight helper function to make it easier to understand the parameters for registering keys.
	 * @param unlocalizedKeyName The unlocalized name of the key. Example: key.spawn
	 * @param key The integer representing the key itself. See {@link Keyboard} for easy to use key constants.
	 * @param category The category the key will be under in the key bindings menu. Example: key.modname
	 * @return
	 */
	public static KeyBinding getNewKeyBinding(String unlocalizedKeyName, int key, String category)
	{
		return new KeyBinding(unlocalizedKeyName, key, category + ".category");
	}
}

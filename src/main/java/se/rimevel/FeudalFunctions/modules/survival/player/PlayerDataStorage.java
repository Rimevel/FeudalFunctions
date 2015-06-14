package se.rimevel.FeudalFunctions.modules.survival.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.nbt.NBTTagCompound;

/**
 * This class can temporarily hold player data when needed. For example during death.
 */
public class PlayerDataStorage
{
	private static final Map<String, NBTTagCompound> playerData = new HashMap<String, NBTTagCompound>();
	
	public static void storePlayerData(UUID id, String key, NBTTagCompound compound)
	{
		playerData.put(id.toString() + "::" + key, compound);
	}
	
	public static NBTTagCompound readPlayerData(UUID id, String key)
	{
		return playerData.remove(id.toString() + "::" + key);
	}
}

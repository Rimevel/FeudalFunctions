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
	private static final Map<UUID, NBTTagCompound> playerData = new HashMap<UUID, NBTTagCompound>();
	
	public static void storePlayerData(UUID id, NBTTagCompound compound)
	{
		playerData.put(id, compound);
	}
	
	public static NBTTagCompound readPlayerData(UUID id)
	{
		return playerData.remove(id);
	}
}

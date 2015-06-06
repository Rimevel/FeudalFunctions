package se.rimevel.FeudalFunctions.modules.survival.player;

import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerSurvivalEvents
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		PlayerDataStats.register(event.entity);
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.world.isRemote && event.entity instanceof EntityPlayer)
		{
			NBTTagCompound playerData = PlayerDataStorage.readPlayerData(UtilPlayer.getPlayerId((EntityPlayer) event.entity));
			
			if(playerData != null)
			{
				PlayerDataStats.get((EntityPlayer) event.entity).loadNBTData(playerData);
			}
			
			PlayerDataStats.get((EntityPlayer) event.entity).dataSync();
		}
	}
	
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
			NBTTagCompound playerData = new NBTTagCompound();
			event.entity.getExtendedProperties(PlayerDataStats.EXT_PROP_NAME).saveNBTData(playerData);
			
			PlayerDataStorage.storePlayerData(UtilPlayer.getPlayerId((EntityPlayer) event.entity), playerData);
		}
	}
}

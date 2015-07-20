package se.rimevel.FeudalFunctions.modules.survival.player;

import java.util.List;
import java.util.Random;

import akka.japi.Util;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerSurvivalEvents
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		PlayerDataStats.register(event.entity);
	}
	
	/**
	 * Loads all extra data from PlayerDataStorage when the player respawns.
	 * @param event
	 */
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(!event.world.isRemote && event.entity instanceof EntityPlayer)
		{
			NBTTagCompound playerData = PlayerDataStorage.readPlayerData(UtilPlayer.getPlayerId(event.entity), "stats");
			
			if(playerData != null)
			{
				PlayerDataStats.get((EntityPlayer) event.entity).loadNBTData(playerData);
			}
			
			PlayerDataStats.get((EntityPlayer) event.entity).dataSync();
		}
	}
	
	/**
	 * Saves all extra data from the player to PlayerDataStorage when the player dies.
	 * @param event
	 */
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		if(!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
			NBTTagCompound playerData = new NBTTagCompound();
			event.entity.getExtendedProperties(PlayerDataStats.EXT_PROP_NAME).saveNBTData(playerData);
			
			PlayerDataStorage.storePlayerData(UtilPlayer.getPlayerId(event.entity), "stats", playerData);
		}
	}
	
	@SubscribeEvent
	public void onPlayerUseItem(PlayerUseItemEvent.Finish event)
	{
		if(event.item.getItem() == Items.potionitem && event.item.getItemDamage() == 0)
		{
			PlayerDataStats.get(event.entityPlayer).adjustBodyHydro(50F);
		}
	}
	
	/**
	 * Player can drink when using empty hand on water sources.
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if(event.world.isRemote){return;}
		
		if(UtilPlayer.isHoldingItem(event.entityPlayer)){return;}
		if(event.entityPlayer.isInWater()){return;}
		
		Block block = UtilPlayer.getBlockPlayerIsLookingAt(event.world, event.entityPlayer, 4D, true);
		
		if(Block.isEqualTo(block, Blocks.water) || Block.isEqualTo(block, Blocks.flowing_water))
		{
			event.world.playSoundAtEntity(event.entityPlayer, "random.drink", 0.7F, 1.0F * UtilMath.randomInRange(0.9F, 1.1F));
			PlayerDataStats.get(event.entityPlayer).adjustBodyHydro(2F);
			PlayerDataStats.get(event.entityPlayer).dataSync();
		}
	}
}

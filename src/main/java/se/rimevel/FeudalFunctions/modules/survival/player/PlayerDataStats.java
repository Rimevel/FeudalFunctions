package se.rimevel.FeudalFunctions.modules.survival.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import se.rimevel.FeudalFunctions.core.network.NetworkPacketSender;
import se.rimevel.FeudalFunctions.core.player.PlayerDataExtenderBase;
import se.rimevel.FeudalFunctions.modules.survival.network.packets.PacketPlayerStats;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class PlayerDataStats extends PlayerDataExtenderBase
{
	public final static String EXT_PROP_NAME = "PlayerDataStats";
	
	private float bodyTemp;
	private float bodyHydration;
	
	public PlayerDataStats()
	{
		super(EXT_PROP_NAME);
		this.bodyTemp = 50F;
		this.bodyHydration = 50F;
	}
	
	@Override
	public void writeData(NBTTagCompound compound)
	{
		compound.setFloat("BodyTemp", bodyTemp);
		compound.setFloat("BodyHydration", bodyHydration);
	}
	
	@Override
	public void readData(NBTTagCompound compound)
	{
		this.bodyTemp = compound.getFloat("BodyTemp");
		this.bodyHydration = compound.getFloat("BodyHydration");
	}
	
	public void dataSync()
	{
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.SERVER)
		{
			NetworkPacketSender.sendTo(new PacketPlayerStats(PlayerDataStats.get(this.player)), (EntityPlayerMP) this.player);
		}
	}
	
	public static final void register(Entity player)
	{
		if(player instanceof EntityPlayer && PlayerDataStats.get((EntityPlayer) player) == null)
		{
			player.registerExtendedProperties(EXT_PROP_NAME, new PlayerDataStats());
		}
	}
	
	public static final PlayerDataStats get(EntityPlayer player)
	{
		return (PlayerDataStats) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	public float adjustBodyTemp(float modifier)
	{
		if(modifier == 0F)
		{
			if(bodyTemp < 50F){bodyTemp += 1.0F;}
			if(bodyTemp > 50F){bodyTemp += -1.0F;}
			return bodyTemp;
		}
		if(bodyTemp + modifier > 100F)
		{
			bodyTemp = 100F;
			return bodyTemp;
		}
		if(bodyTemp + modifier < 0F)
		{
			bodyTemp = 0;
			return bodyTemp;
		}
		
		bodyTemp += modifier;
		return bodyTemp;
	}
	
	public void setTemperature(float temperature)
	{
		this.bodyTemp = temperature;
	}
	
	public float getTemperature()
	{
		return bodyTemp;
	}
	
	public float adjustBodyHydro(float modifier)
	{
		if(modifier == 0)
		{
			if(bodyHydration < 100F)
			{
				bodyHydration += 1F;
				return bodyHydration;
			}
		}
		if(bodyHydration + modifier > 100F)
		{
			bodyHydration = 100F;
			return bodyHydration;
		}
		if(bodyHydration + modifier < 0F)
		{
			bodyHydration = 0F;
			return bodyHydration;
		}
		
		bodyHydration += modifier;
		return bodyHydration;
	}
	
	public void setHydration(float bodyHydration)
	{
		this.bodyHydration = bodyHydration;
	}
	
	public float getHydration()
	{
		return bodyHydration;
	}
}

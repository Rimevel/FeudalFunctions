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
	
	private int bodyTemp;
	
	public PlayerDataStats()
	{
		super(EXT_PROP_NAME);
		this.bodyTemp = 50;
	}
	
	@Override
	public void writeData(NBTTagCompound compound)
	{
		compound.setShort("BodyTemp", (short) bodyTemp);
	}
	
	@Override
	public void readData(NBTTagCompound compound)
	{
		this.bodyTemp = compound.getShort("BodyTemp");
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
	
	public int adjustBodyTemp(int modifier)
	{
		if(modifier == 0)
		{
			if(bodyTemp <= 49){bodyTemp += 1;}
			if(bodyTemp >= 51){bodyTemp += -1;}
			dataSync();
			return bodyTemp;
		}
		if(bodyTemp + modifier > 100)
		{
			bodyTemp = 100;
			dataSync();
			return bodyTemp;
		}
		if(bodyTemp + modifier < 0)
		{
			bodyTemp = 0;
			dataSync();
			return bodyTemp;
		}
		
		bodyTemp += modifier;
		dataSync();
		return bodyTemp;
	}
	
	public void setTemperature(int temperature)
	{
		this.bodyTemp = temperature;
	}
	
	public int getTemperature()
	{
		return bodyTemp;
	}
}

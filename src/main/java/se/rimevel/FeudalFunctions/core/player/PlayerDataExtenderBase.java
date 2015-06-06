package se.rimevel.FeudalFunctions.core.player;

import se.rimevel.FeudalFunctions.core.util.UtilLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class PlayerDataExtenderBase implements IExtendedEntityProperties
{
	public final String name;
	
	protected EntityPlayer player;
	
	public PlayerDataExtenderBase(String name)
	{
		this.name = name;
	}
	
	public void writeData(NBTTagCompound compound)
	{
		
	}
	
	public void readData(NBTTagCompound compound)
	{
		
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeData(tag);
		compound.setTag(name, tag);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound tag = (NBTTagCompound) compound.getTag(name);
		readData(tag);
		//TODO: Remove this!
		UtilLog.info("[PLAYERDATA] Recived!");
	}

	@Override
	public void init(Entity entity, World world)
	{
		if(entity instanceof EntityPlayer)
		{
			this.player = (EntityPlayer) entity;
		}
	}
}

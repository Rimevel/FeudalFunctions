package se.rimevel.FeudalFunctions.modules.smithing.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;

public class TileEntityAnvil extends TileEntityContainerBase
{
	public boolean slotsLocked;

	private boolean doOnce;
	
	public TileEntityAnvil()
	{
		super(9);
	}
	
	public void onHammering(EntityPlayer player)
	{
		if(worldObj.isRemote){return;}
	}
	
	@Override
	public void updateEntity()
	{
		if(!doOnce)
		{
			doOnce = true; //TODO
		}
	}
	
	@Override
	protected void writeSyncData(NBTTagCompound compound)
	{
		compound.setBoolean("Locked", this.slotsLocked);
	}
	
	@Override
	protected void readSyncData(NBTTagCompound compound)
	{
		this.slotsLocked = compound.getBoolean("Locked");
	}
}

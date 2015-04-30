package se.rimevel.FeudalFunctions.core.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBase extends TileEntity
{
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeSyncData(syncData);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readSyncData(pkt.func_148857_g());
	}
	
	protected void writeSyncData(NBTTagCompound compound)
	{
		
	}
	
	protected void readSyncData(NBTTagCompound compound)
	{
		
	}
	
	public void syncData()
	{
		this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	protected void writeExtraData(NBTTagCompound compound)
	{
		
	}
	
	protected void readExtraData(NBTTagCompound compound)
	{
		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		writeSyncData(compound);
		writeExtraData(compound);
	
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		readSyncData(compound);
		readExtraData(compound);
	}
}

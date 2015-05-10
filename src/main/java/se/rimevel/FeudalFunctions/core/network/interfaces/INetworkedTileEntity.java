package se.rimevel.FeudalFunctions.core.network.interfaces;

import net.minecraft.nbt.NBTTagCompound;

public interface INetworkedTileEntity
{
	public void handlePacket(NBTTagCompound compound);
}

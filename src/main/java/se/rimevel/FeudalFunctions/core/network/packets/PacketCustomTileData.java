package se.rimevel.FeudalFunctions.core.network.packets;

import se.rimevel.FeudalFunctions.core.network.NetworkMsgClient;
import se.rimevel.FeudalFunctions.core.network.interfaces.INetworkedTileEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCustomTileData implements IMessage
{
	int worldId, x, y, z;
	NBTTagCompound compound;
	
	public PacketCustomTileData(){}
	
	public PacketCustomTileData(World world, int x, int y, int z, NBTTagCompound compound)
	{
		this.worldId = world.provider.dimensionId;
		this.x = x;
		this.y = y;
		this.z = z;
		this.compound = compound;
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.worldId = ByteBufUtils.readVarShort(buffer);
		this.x = ByteBufUtils.readVarInt(buffer, 20);
		this.y = ByteBufUtils.readVarInt(buffer, 20);
		this.z = ByteBufUtils.readVarInt(buffer, 20);
		this.compound = ByteBufUtils.readTag(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeVarShort(buffer, worldId);
		ByteBufUtils.writeVarInt(buffer, x, 20);
		ByteBufUtils.writeVarInt(buffer, y, 20);
		ByteBufUtils.writeVarInt(buffer, z, 20);
		ByteBufUtils.writeTag(buffer, compound);
	}
	
	public static class PacketCustomTileDataHandler extends NetworkMsgClient<IMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, IMessage message, MessageContext ctx)
		{
			PacketCustomTileData m = (PacketCustomTileData) message;
			
			World world = player.worldObj;
			int dimId = world.provider.dimensionId;
			if(dimId == m.worldId)
			{
				TileEntity tile = world.getTileEntity(m.x, m.y, m.z);
				if(tile != null && tile instanceof INetworkedTileEntity)
				{
					((INetworkedTileEntity) tile).handlePacket(m.compound);
				}
			}
			
			return null;
		}
	}
}
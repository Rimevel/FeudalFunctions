package se.rimevel.FeudalFunctions.core.network.packets;

import se.rimevel.FeudalFunctions.core.ModCore;
import se.rimevel.FeudalFunctions.core.network.NetworkMsgServer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketAccessGui implements IMessage
{
	int id;
	
	public PacketAccessGui(){}
	
	public PacketAccessGui(int id)
	{
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.id = ByteBufUtils.readVarShort(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeVarShort(buffer, this.id);
	}
	
	public static class PacketAccessGuiHandler extends NetworkMsgServer
	{
		@Override
		public IMessage handleServerMessage(EntityPlayer player, IMessage message, MessageContext ctx)
		{
			PacketAccessGui m = (PacketAccessGui) message;
			
			player.openGui(ModCore.instance, m.id, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
			
			return null;
		}
	}
}
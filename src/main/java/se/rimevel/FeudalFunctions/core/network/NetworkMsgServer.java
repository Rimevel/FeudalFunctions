package se.rimevel.FeudalFunctions.core.network;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public abstract class NetworkMsgServer<T extends IMessage> extends NetworkMsgHandler<T>
{
	public final IMessage handleClientMessage(EntityPlayer player, T message, MessageContext ctx)
	{
		return null;
	}
}
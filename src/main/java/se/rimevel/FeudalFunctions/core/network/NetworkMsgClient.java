package se.rimevel.FeudalFunctions.core.network;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public abstract class NetworkMsgClient<T extends IMessage> extends NetworkMsgHandler<T>
{
	public final IMessage handleServerMessage(EntityPlayer player, T message, MessageContext ctx)
	{
		return null;
	}
}

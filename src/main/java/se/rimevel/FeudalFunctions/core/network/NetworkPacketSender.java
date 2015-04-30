package se.rimevel.FeudalFunctions.core.network;

import se.rimevel.FeudalFunctions.core.ModSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

/**
 * This class works as a wrapper for SimpleNetworkWrapper. Easier and more convenient methods that remove the need
 * for a lot of extra code.
 * 
 * Credit goes to coolAlias for this system. Thanks a lot!
 */

public class NetworkPacketSender
{
	// a simple counter will allow us to get rid of 'magic' numbers used during packet registration
	private static byte packetId = 0;
 
	/**
	* The SimpleNetworkWrapper instance is used both to register and send packets. Accessed trough the wrapper methods.
	*/
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(ModSettings.ID);

	/**
	* Call this during pre-init or loading and register all of your packets (messages) here
	*/
	public static final void registerPackets()
	{
		// Using an incrementing field instead of hard-coded numerals, I don't need to think
		// about what number comes next or if I missed on should I ever rearrange the order
		// of registration (for instance, if you wanted to alphabetize them... yeah...)
		// It's even easier if you create a convenient 'registerMessage' method:
		
		for (NetworkPacketList list : NetworkPacketList.values())
		{
			NetworkPacketSender.registerMessage(list.HANDLER, list.PACKET, list.side);
		}
	}

	/**
	 * Registers a message and message handler
	 */
	
	private static final void registerMessage(Class handlerClass, Class messageClass, Side side)
	{
		NetworkPacketSender.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}

	private static final void registerMessage(Class handlerClass, Class messageClass)
	{
		Side side = NetworkMsgClient.class.isAssignableFrom(handlerClass) ? Side.CLIENT : Side.SERVER;
		NetworkPacketSender.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}
	
	//========================================================//
	// The following methods are the 'wrapper' methods; again,
	// this just makes sending a message slightly more compact
	// and is purely a matter of stylistic preference
	//========================================================//
 
	/**
	* Send this message to the specified player.
	* See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	*/
	public static final void sendTo(IMessage message, EntityPlayerMP player)
	{
		NetworkPacketSender.dispatcher.sendTo(message, player);
	}

	/**
	 * Send this message to everyone within a certain range of a point.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, NetworkRegistry.TargetPoint)}
	 */
	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point)
	{
		NetworkPacketSender.dispatcher.sendToAllAround(message, point);
	}

	/**
	 * Sends a message to everyone within a certain range of the coordinates in the same dimension.
	 */
	public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range)
	{
		NetworkPacketSender.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
	}

	/**
	* Sends a message to everyone within a certain range of the player provided.
	*/
	public static final void sendToAllAround(IMessage message, EntityPlayer player, double range)
	{
		NetworkPacketSender.sendToAllAround(message, player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, range);
	}

	/**
	* Send this message to everyone within the supplied dimension.
	* See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	*/
	public static final void sendToDimension(IMessage message, int dimensionId)
	{
		NetworkPacketSender.dispatcher.sendToDimension(message, dimensionId);
	}

	/**
	 * Send this message to the server.
	 * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 */
	public static final void sendToServer(IMessage message)
	{
		NetworkPacketSender.dispatcher.sendToServer(message);
	}
}

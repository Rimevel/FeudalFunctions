package se.rimevel.FeudalFunctions.core.network;

import se.rimevel.FeudalFunctions.core.network.packets.PacketAccessGui;
import se.rimevel.FeudalFunctions.core.network.packets.PacketAccessGui.PacketAccessGuiHandler;
import cpw.mods.fml.relauncher.Side;

public enum NetworkPacketList
{
	ACCESS_GUI(PacketAccessGuiHandler.class, PacketAccessGui.class, Side.SERVER);
	
	public final Class<?> HANDLER;
	public final Class<?> PACKET;
	public final Side side;
	
	private NetworkPacketList(Class<?> handlerClass, Class<?> packetClass, Side side)
	{
		this.HANDLER = handlerClass;
		this.PACKET = packetClass;
		this.side = side;
	}
}

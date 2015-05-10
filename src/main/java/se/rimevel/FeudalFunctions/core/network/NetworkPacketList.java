package se.rimevel.FeudalFunctions.core.network;

import se.rimevel.FeudalFunctions.core.network.packets.PacketAccessGui;
import se.rimevel.FeudalFunctions.core.network.packets.PacketCustomTileData;
import se.rimevel.FeudalFunctions.core.network.packets.PacketAccessGui.PacketAccessGuiHandler;
import se.rimevel.FeudalFunctions.core.network.packets.PacketCustomTileData.PacketCustomTileDataHandler;
import cpw.mods.fml.relauncher.Side;

public enum NetworkPacketList
{
	ACCESS_GUI(PacketAccessGuiHandler.class, PacketAccessGui.class, Side.SERVER),
	CUSTOM_TILE_DATA(PacketCustomTileDataHandler.class, PacketCustomTileData.class, Side.CLIENT);
	
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

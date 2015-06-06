package se.rimevel.FeudalFunctions.modules.survival.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.rimevel.FeudalFunctions.core.ModCore;
import se.rimevel.FeudalFunctions.core.network.NetworkMsgClient;
import se.rimevel.FeudalFunctions.core.network.NetworkMsgServer;
import se.rimevel.FeudalFunctions.core.network.interfaces.INetworkedTileEntity;
import se.rimevel.FeudalFunctions.core.network.packets.PacketAccessGui;
import se.rimevel.FeudalFunctions.core.network.packets.PacketCustomTileData;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerDataStats;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketPlayerStats implements IMessage
{
	int bodyTemp;
	
	public PacketPlayerStats(){}
	
	public PacketPlayerStats(PlayerDataStats stats)
	{
		this.bodyTemp = stats.getTemperature();
	}
	
	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.bodyTemp = ByteBufUtils.readVarShort(buffer);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeVarShort(buffer, this.bodyTemp);
	}
	
	public static class PacketPlayerStatsHandler extends NetworkMsgClient
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, IMessage message, MessageContext ctx)
		{
			PacketPlayerStats m = (PacketPlayerStats) message;
			
			PlayerDataStats data = PlayerDataStats.get(player);
			data.setTemperature(m.bodyTemp);
			
			return null;
		}
	}
}

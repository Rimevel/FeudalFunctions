package se.rimevel.FeudalFunctions.modules.survival.events;

import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerTickTemperature;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SurvivalWorldEvents
{
	@SubscribeEvent
	public void onPlacingBlock(BlockEvent.PlaceEvent event)
	{
		if(event.world.isRemote){return;}
		
		if(PlayerTickTemperature.getTemperatureAtLocation(event.world, event.x, event.y, event.z) > 0)
		{
			if(event.block == Blocks.ice || event.block == Blocks.packed_ice)
			{
				event.world.setBlock(event.x, event.y, event.z, Blocks.water);
				return;
			}
			if(event.block == Blocks.snow || event.block == Blocks.snow_layer)
			{
				event.world.setBlock(event.x, event.y, event.z, Blocks.flowing_water);
				event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, 7, 2);
				return;
			}
		}
		
		
	}
}

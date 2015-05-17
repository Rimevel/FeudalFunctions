package se.rimevel.FeudalFunctions.modules.smithing.ui.container;

import net.minecraft.entity.player.InventoryPlayer;
import se.rimevel.FeudalFunctions.core.ui.container.ContainerBase;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityBloomery;

public class ContainerBloomery extends ContainerBase
{
	public ContainerBloomery(InventoryPlayer invPlayer, TileEntityBloomery tile)
	{
		super(tile);
		addPlayerSlots(invPlayer, 8, 104);
		addSlot(71, 32);
		addSlot(89, 32);
		addSlotOutput(80, 72);
	}
}

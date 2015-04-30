package se.rimevel.FeudalFunctions.modules.smithing.ui.container;

import net.minecraft.entity.player.InventoryPlayer;
import se.rimevel.FeudalFunctions.core.ui.container.ContainerBase;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityAnvil;

public class ContainerAnvil extends ContainerBase
{
	public ContainerAnvil(InventoryPlayer invPlayer, TileEntityAnvil tile)
	{
		super(tile);
		addPlayerSlots(invPlayer, 8, 104);
		addSlot(62, 22);
		addSlot(80, 22);
		addSlot(98, 22);
		addSlot(62, 40);
		addSlot(80, 40);
		addSlot(98, 40);
		addSlot(62, 58);
		addSlot(80, 58);
		addSlot(98, 58);
	}
}

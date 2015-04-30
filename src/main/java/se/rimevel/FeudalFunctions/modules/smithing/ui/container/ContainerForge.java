package se.rimevel.FeudalFunctions.modules.smithing.ui.container;

import net.minecraft.entity.player.InventoryPlayer;
import se.rimevel.FeudalFunctions.core.ui.container.ContainerBase;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityForge;

public class ContainerForge extends ContainerBase
{
	public ContainerForge(InventoryPlayer invPlayer, TileEntityForge tile)
	{
		super(tile);
		addPlayerSlots(invPlayer, 8, 104);
		
		//Fuel area
		addSlot(39, 70);
		
		//Item area
		addSlotSingle(80, 40);
		addSlotSingle(71, 21);
		addSlotSingle(89, 21);
	}
}

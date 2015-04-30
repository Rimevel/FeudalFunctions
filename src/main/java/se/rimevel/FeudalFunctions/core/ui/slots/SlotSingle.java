package se.rimevel.FeudalFunctions.core.ui.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotSingle extends Slot
{
	public SlotSingle(IInventory inventory, int index, int x, int y)
	{
		super(inventory, index, x, y);
	}
	
	@Override
	public int getSlotStackLimit()
	{
		return 1;
	}
}

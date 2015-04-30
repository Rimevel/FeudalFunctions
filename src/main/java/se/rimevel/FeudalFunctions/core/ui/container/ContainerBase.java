package se.rimevel.FeudalFunctions.core.ui.container;

import se.rimevel.FeudalFunctions.core.ui.slots.SlotOutput;
import se.rimevel.FeudalFunctions.core.ui.slots.SlotSingle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerBase extends Container
{
	public TileEntity tile;
	public int slotNumber;
	
	public ContainerBase(TileEntity tile)
	{
		this.tile = tile;
		this.slotNumber = 0;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		if(tile instanceof IInventory)
		{
			return ((IInventory) tile).isUseableByPlayer(player);
		}
		else
		{
			if(player.getDistanceSq(tile.xCoord + 0.5D, tile.yCoord + 0.5D, tile.zCoord + 0.5D) < 64)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	/**
	 * Add all the player slots to the container.
	 * @param invPlayer
	 * @param topLeftX
	 * @param topLeftY
	 */
	public void addPlayerSlots(InventoryPlayer invPlayer, int topLeftX, int topLeftY)
	{
		for (int x = 0; x < 9; x++)
		{
			addSlotToContainer(new Slot(invPlayer, x, topLeftX + 18 * x, topLeftY + 58));
		}
		
		for (int y = 0; y < 3; y++)
		{
			for (int x = 0; x < 9; x++)
			{
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, topLeftX + 18 * x, topLeftY + y * 18));
			}
		}
	}
	
	/**
	 * Add a normal slot.
	 * @param x
	 * @param y
	 */
	public void addSlot(int x, int y)
	{
		if(tile instanceof IInventory)
		{
			addSlotToContainer(new Slot((IInventory) tile, this.slotNumber, x, y));
			this.slotNumber++;
		}
	}
	
	/**
	 * Add a slot that can only contain one item stack.
	 * @param x
	 * @param y
	 */
	public void addSlotSingle(int x, int y)
	{
		if(tile instanceof IInventory)
		{
			addSlotToContainer(new SlotSingle((IInventory) tile, this.slotNumber, x, y));
			this.slotNumber++;
		}
	}
	
	/**
	 * Add a slot that do not accept items from the players inventory.
	 * @param x
	 * @param y
	 */
	public void addSlotOutput(int x, int y)
	{
		if(tile instanceof IInventory)
		{
			addSlotToContainer(new SlotOutput((IInventory) tile, this.slotNumber, x, y));
			this.slotNumber++;
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i)
	{
		ItemStack stack = null;
		Slot slot = (Slot)inventorySlots.get(i);
		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			stack = slotStack.copy();
			if(i < slotNumber)
			{
				if(!mergeItemStack(slotStack, 108, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if(!mergeItemStack(slotStack, 0, slotNumber, false))
			{
				return null;
			}
			
			if(slotStack.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}
			
			if(slotStack.stackSize == stack.stackSize)
			{
				return null;
			}
			
			slot.onPickupFromSlot(player, slotStack);
		}
		
		return stack;
	}
}

package se.rimevel.FeudalFunctions.modules.smithing.tiles;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.blocks.BlockForge;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemHeated;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataHeatable;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataHeatableList;

public class TileEntityForge extends TileEntityContainerBase
{
	public static final int MAX_FUEL = 120;
	public int fuel;
	
	private boolean active;
	private int[] slotHeat;
	
	private int counter;
	
	public TileEntityForge()
	{
		super(4);
		slotHeat = new int[3];
	}
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote){return;}
		
		if(active)
		{
			if(counter >= 20)
			{
				counter = 0;
				
				if(fuel > 0)
				{
					fuel--;
					
					for (int i = 1; i < getSizeInventory(); i++)
					{
						ItemStack stack = getStackInSlot(i);
						int heatTime = 0;
						
						if(stack != null)
						{
							//Check if item has heat data
							heatTime = DataHeatableList.getItemHeatTime(stack);
							
							//If data exists
							if(heatTime > 0)
							{
								slotHeat[i - 1] += 1;
								
								if(slotHeat[i - 1] >= heatTime)
								{
									setInventorySlotContents(i, ItemHeated.createHeatedItem(stack));
								}
							}
							else
							{
								slotHeat[i - 1] = 0;
							}
						}
					}
					
					syncData();
				}
				else
				{
					fuel = 0;
					setActive(false);
				}
			}
			
			counter++;
		}
		
		if(getStackInSlot(0) != null && getStackInSlot(0).getItem() == Items.coal)
		{
			if(fuel + 120 <= MAX_FUEL)
			{
				fuel += 120;
				
				decrStackSize(0, 1);
				syncData();
				return;
			}
		}
	}
	
	public void setActive(boolean state)
	{
		if(state && fuel > 0)
		{
			this.active = true;
			BlockForge.updateBlockMeta(true, worldObj, xCoord, yCoord, zCoord);
			return;
		}
		
		if(active)
		{
			this.active = false;
			BlockForge.updateBlockMeta(false, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	@Override
	protected void writeSyncData(NBTTagCompound compound)
	{
		compound.setBoolean("Active", this.active);
		compound.setInteger("Fuel", this.fuel);
	}
	
	@Override
	protected void readSyncData(NBTTagCompound compound)
	{
		this.active = compound.getBoolean("Active");
		this.fuel = compound.getInteger("Fuel");
	}
}
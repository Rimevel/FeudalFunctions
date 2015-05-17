package se.rimevel.FeudalFunctions.modules.smithing.tiles;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import scala.xml.persistent.SetStorage;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import se.rimevel.FeudalFunctions.modules.smithing.blocks.BlockBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemBloom;

public class TileEntityBloomery extends TileEntityContainerBase
{
	public boolean active;
	public int progress, fuel;
	
	public TileEntityBloomery()
	{
		super(3);
		this.progress = 0;
	}
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote){return;}
		
		if(!getHasFuel() && active == true)
		{
			setActive(false);
			progress = 0;
			syncData();
			return;
		}
		else if(getHasFuel() && active == true)
		{
			fuel++;
			if(fuel > 800)
			{
				decrStackSize(getFuelSlot(), 1);
				fuel = 0;
			}
		}
		
		if(isCorrectMix() && active == true)
		{
			progress++;
			
			if(progress > 800)
			{
				int oreSlot = getOreSlot();
				
				ItemStack newStack;
				newStack = ItemBloom.createBloom(getStackInSlot(oreSlot));
				
				if(getStackInSlot(2) == null)
				{
					setInventorySlotContents(2, newStack);
					decrStackSize(getOreSlot(), 1);
				}
				else
				{
					if(getStackInSlot(2).getItem() instanceof ItemBloom)
					{
						if(ItemBloom.isValidOre(getStackInSlot(oreSlot)) && ItemBloom.isSameType(getStackInSlot(oreSlot), getStackInSlot(2)))
						{
							if(ItemBloom.increaseAmount(getStackInSlot(2), ItemBloom.getBloomData(newStack).amount))
							{
								decrStackSize(getOreSlot(), 1);
							}
						}
					}
				}
				
				progress = 0;
			}
			
			syncData();
		}
		
		
	}
	
	@Override
	protected void writeSyncData(NBTTagCompound compound)
	{
		compound.setInteger("Progress", progress);
		compound.setBoolean("Active", active);
	}
	
	@Override
	protected void readSyncData(NBTTagCompound compound)
	{
		progress = compound.getInteger("Progress");
		active = compound.getBoolean("Active");
	}
	
	public boolean getHasFuel()
	{
		if(isValidFuel(getStackInSlot(0)))
		{
			return true;
		}
		
		if(isValidFuel(getStackInSlot(1)))
		{
			return true;
		}
		
		return false;
	}

	public int getFuelSlot()
	{
		if(isValidFuel(getStackInSlot(0)))
		{
			return 0;
		}
		
		if(isValidFuel(getStackInSlot(1)))
		{
			return 1;
		}
		
		return -1;
	}
	
	private int getOreSlot()
	{
		ItemStack slotOne = getStackInSlot(0);
		ItemStack slotTwo = getStackInSlot(1);
		
		if(slotOne != null && !isValidFuel(slotOne))
		{
			return 0;
		}
		
		if(slotTwo != null && !isValidFuel(slotTwo))
		{
			return 1;
		}
		
		return -1;
	}
	
	private boolean isCorrectMix()
	{
		ItemStack slotOne = getStackInSlot(0);
		ItemStack slotTwo = getStackInSlot(1);
		
		if(slotOne != null && slotTwo != null)
		{
			if(isValidFuel(slotOne) && ItemBloom.isValidOre(slotTwo))
			{
				return true;
			}
			
			if(isValidFuel(slotTwo) && ItemBloom.isValidOre(slotOne))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isValidFuel(ItemStack stack)
	{
		if(stack != null && stack.getItem() == Items.coal){return true;}
		
		return false;
	}

	public boolean isBurning()
	{
		return active;
	}

	public void setActive(boolean state)
	{
		if(state == true && active == false && getHasFuel())
		{
			active = true;
			BlockBloomery.updateBlockMeta(true, worldObj, xCoord, yCoord, zCoord);
			return;
		}
		else if(state == false && active == true)
		{
			active = false;
			BlockBloomery.updateBlockMeta(false, worldObj, xCoord, yCoord, zCoord);
			return;
		}
	}
}

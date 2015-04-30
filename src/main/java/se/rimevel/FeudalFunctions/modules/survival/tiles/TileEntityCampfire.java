package se.rimevel.FeudalFunctions.modules.survival.tiles;

import net.minecraft.nbt.NBTTagCompound;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityBase;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.survival.blocks.BlockCampfire;

public class TileEntityCampfire extends TileEntityContainerBase
{
	private int fuel, counter;
	private boolean active;

	/**
	 * Maximum possible amount of fuel in the campfire at the same time.
	 */
	private static final int MAX_FUEL = 1280;
	
	public TileEntityCampfire()
	{
		super(2);
		fuel = 0;
		active = false;
	}
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote){return;}
		{
			if(active)
			{
				if(counter <= 0)
				{
					if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isRaining())
					{
						fuel -= 2;
					}
					else
					{
						fuel -= 1;
					}
					
					counter = 20;
				}
				else
				{
					counter--;
				}
				
				if(fuel <= 0)
				{
					fuel = 0;
					setActive(false);
				}
			}
		}
	}
	
	public boolean addFuel(int amount)
	{
		if(amount + fuel <= MAX_FUEL)
		{
			fuel += amount;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Set the state of the campfire. True or false if it has fuel. Only false otherwise.
	 * @param state
	 */
	public void setActive(boolean state)
	{
		if(fuel > 0)
		{
			this.active = state;
			BlockCampfire.updateBlockMeta(state, worldObj, xCoord, yCoord, zCoord);
		}
		else
		{
			this.active = false;
			BlockCampfire.updateBlockMeta(false, worldObj, xCoord, yCoord, zCoord);
		}
	}
	
	@Override
	protected void readSyncData(NBTTagCompound compound)
	{
		this.fuel = compound.getShort("fuel");
		this.active = compound.getBoolean("active");
	}
	
	@Override
	protected void writeSyncData(NBTTagCompound compound)
	{
		compound.setShort("fuel", (short)this.fuel);
		compound.setBoolean("active", this.active);
	}
}

package se.rimevel.FeudalFunctions.modules.survival.tiles;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityBase;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.survival.blocks.BlockCampfire;
import se.rimevel.FeudalFunctions.modules.survival.crafting.RecipeCampfire;
import se.rimevel.FeudalFunctions.modules.survival.crafting.recipes.RecipeListCampfire;

public class TileEntityCampfire extends TileEntityContainerBase
{
	private int fuel, counter, timer;
	private boolean active;
	private RecipeCampfire recipe;
	
	public float rotation;
	
	/**
	 * Maximum possible amount of fuel in the campfire at the same time.
	 */
	private static final int MAX_FUEL = 1280;
	
	public TileEntityCampfire()
	{
		super(1);
		fuel = 0;
		active = false;
	}
	
	@Override
	public void updateEntity()
	{
		if(worldObj.isRemote)
		{
			rotation += 2.5F;
			if(rotation >= 360F)
			{
				rotation = 0F;
			}
		}
		else
		{
			if(active)
			{
				if(counter >= 20)
				{
					if(worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) && worldObj.isRaining())
					{
						fuel -= 4;
					}
					else
					{
						fuel -= 1;
					}
					
					if(getStackInSlot(0) != null && recipe == null)
					{
						for (RecipeCampfire r : RecipeListCampfire.getRecipeList())
						{
							if(r.checkMatch(getStackInSlot(0)))
							{
								this.recipe = r;
								break;
							}
						}
					}
					else if(getStackInSlot(0) != null && recipe != null)
					{
						timer += 1;
						if(timer >= recipe.time)
						{
							setInventorySlotContents(0, recipe.result.copy());
							timer = 0;
							recipe = null;
						}
					}
					
					counter = 0;
				}
				else
				{
					counter++;
				}
				
				if(!hasFuel())
				{
					fuel = 0;
					setActive(false);
				}
				
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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
	
	public boolean hasFuel()
	{
		if(fuel > 0)
		{
			return true;
		}
		
		return false;
	}

	private boolean isValidFuel(ItemStack stack)
	{
		if(stack != null && stack.getItem() == Items.stick){return true;}
		
		return false;
	}
	
	@Override
	protected void readSyncData(NBTTagCompound compound)
	{
		this.fuel = compound.getShort("fuel");
		this.active = compound.getBoolean("active");
		this.timer = compound.getShort("timer");
	}
	
	@Override
	protected void writeSyncData(NBTTagCompound compound)
	{
		compound.setShort("fuel", (short)this.fuel);
		compound.setBoolean("active", this.active);
		compound.setShort("timer", (short)this.timer);
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	public ItemStack getItem()
	{
		return getStackInSlot(0);
	}
}

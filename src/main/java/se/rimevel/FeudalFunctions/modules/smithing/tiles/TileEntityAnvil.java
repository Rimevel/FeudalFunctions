package se.rimevel.FeudalFunctions.modules.smithing.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.recipes.RecipeListAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.items.ItemBloom;
import se.rimevel.FeudalFunctions.modules.smithing.util.DataBloomType;

public class TileEntityAnvil extends TileEntityContainerBase
{
	public boolean slotsLocked;
	
	public ItemStack result;
	public int progress;
	
	public boolean isRepair;
	public int repairSlot;
	
	public boolean isBloom;
	public int bloomSlot;
	
	public TileEntityAnvil()
	{
		super(9);
	}
	
	public void onHammering(EntityPlayer player)
	{
		if(worldObj.isRemote){return;}
		
		if(!slotsLocked)
		{
			repairSlot = getPotentialWeaponRepair();
			bloomSlot = getPotentialBloomRefining();
			
			if(repairSlot > -1)
			{
				slotsLocked = true;
				isRepair = true;
				isBloom = false;
				progress++;
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.anvil_land", 0.7F, 0.8F);
				syncData();
				return;
			}
			
			if(bloomSlot > -1)
			{
				slotsLocked = true;
				isRepair = false;
				isBloom = true;
				progress++;
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.anvil_land", 0.7F, 0.8F);
				syncData();
				return;
			}
			
			ItemStack stack = RecipeListAnvil.matchRecipe(this, worldObj);
			if(stack != null)
			{
				slotsLocked = true;
				result = stack;
				isRepair = false;
				isBloom = false;
				progress++;
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.anvil_land", 0.7F, 0.8F);
				syncData();
				return;
			}
		}
		else
		{
			if(progress <= 20)
			{
				progress++;
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.anvil_land", 0.7F, 0.8F);
				syncData();
				return;
			}
			
			if(progress > 20)
			{
				progress = 0;
				slotsLocked = false;
				if(isRepair)
				{
					doRepair(repairSlot);
					this.repairSlot = -1;
				}
				else if(isBloom)
				{
					doBloomOperation(bloomSlot);
					this.bloomSlot = -1;
				}
				else
				{
					doCrafting(player);
					this.result = null;
				}
				worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "random.levelup", 0.7F, 0.8F);
				syncData();
				return;
			}
		}
	}

	@Override
	protected void writeSyncData(NBTTagCompound compound)
	{
		compound.setBoolean("Locked", this.slotsLocked);
		compound.setInteger("Progress", this.progress);
	}
	
	@Override
	protected void readSyncData(NBTTagCompound compound)
	{
		this.slotsLocked = compound.getBoolean("Locked");
		this.progress = compound.getInteger("Progress");
	}
	
	/**
	 * Craft the result item for the current grid.
	 * @param player Player doing the crafting.
	 */
	private void doCrafting(EntityPlayer player)
	{
		if(result != null)
		{
			for (int i = 0; i < content.length; i++)
			{
				decrStackSize(i, 1);
			}
			
			setInventorySlotContents(4, result);
		}
	}
	
	/**
	 * Do an actual repair on the tool in the given slot.
	 * @param toolSlot Slot containing the tool to be repaired.
	 */
	private void doRepair(int toolSlot)
	{
		ItemStack repairedTool;
		
		if(getStackInSlot(toolSlot).getItem() instanceof ItemTool)
		{
			repairedTool = getStackInSlot(toolSlot).copy();
			repairedTool.setItemDamage(0);
			
			for (int i = 0; i < getSizeInventory(); i++)
			{
				if(getStackInSlot(i) != null)
				{
					decrStackSize(i, 1);
				}
			}
			
			setInventorySlotContents(4, repairedTool);
		}
	}
	
	/**
	 * Refine the bloom in the given slot.
	 * @param bloomSlot
	 */
	private void doBloomOperation(int bloomSlot)
	{
		ItemStack bloomStack = getStackInSlot(bloomSlot);
		
		if(bloomStack != null && bloomStack.getItem() instanceof ItemBloom)
		{
			DataBloomType data = ItemBloom.getBloomData(bloomStack);
			
			if(data.amount >= 1000)
			{
				ItemStack result = ItemBloom.getBloomData(bloomStack).result.copy();
				
				for (int i = 0; i < getSizeInventory(); i++)
				{
					if(getStackInSlot(i) != null)
					{
						decrStackSize(i, 1);
					}
				}
				
				setInventorySlotContents(4, result);
			}
		}
	}
	
	/**
	 * Check if the items on the grid consist of only a tool and its material.
	 * @return Returns the slot that contains the tool to be repaired. -1 if no valid repair recipe was found.
	 */
	private int getPotentialWeaponRepair()
	{
		int stackToRepair = -1;
		int ingot = -1;
		
		//Find slot with tool.
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(getStackInSlot(i) != null && getStackInSlot(i).getItem() instanceof ItemTool)
			{
				stackToRepair = i;
			}
		}
		
		//If no tool was found.
		if(stackToRepair == -1){return -1;}
		
		//Get repair material of the tool.
		//ItemStack repairMaterial = ((ItemTool)getStackInSlot(stackToRepair).getItem()).func_150913_i().getRepairItemStack();
		
		//See if the oreDict name of the tools repair material equals the oreDict name of any of the slots.
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(i == stackToRepair){continue;}
			
			if(getStackInSlot(i) == null){continue;}
			
			if(((ItemTool)getStackInSlot(stackToRepair).getItem()).getIsRepairable(getStackInSlot(stackToRepair), getStackInSlot(i)))
			{
				ingot = i;
			}
		}
		
		//If repair material was not found then return default value.
		if(ingot == -1){return -1;}
		
		//Makes sure no other part of the grid has an item.
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(i == stackToRepair || i == ingot){continue;}
			
			if(getStackInSlot(i) != null){return -1;}
		}
		
		return stackToRepair;
	}
	
	/**
	 * Check if the items on the grid consist of only one bloom and nothing else.
	 * @return Returns the slot containing the bloom if found. Otherwise -1;
	 */
	private int getPotentialBloomRefining()
	{
		int bloomStackSlot = -1;
		
		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(getStackInSlot(i) != null && getStackInSlot(i).getItem() instanceof ItemBloom)
			{
				bloomStackSlot = i;
			}
		}
		
		for(int i = 0; i < getSizeInventory(); i++)
		{
			if(i == bloomStackSlot){continue;}
			
			if(getStackInSlot(i) != null){return -1;}
		}
		
		return bloomStackSlot;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}
}

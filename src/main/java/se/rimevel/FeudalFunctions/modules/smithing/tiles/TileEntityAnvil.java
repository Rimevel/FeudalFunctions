package se.rimevel.FeudalFunctions.modules.smithing.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import se.rimevel.FeudalFunctions.modules.smithing.crafting.recipes.RecipeListAnvil;

public class TileEntityAnvil extends TileEntityContainerBase
{
	public boolean slotsLocked;
	
	public ItemStack result;
	public int progress;
	
	public boolean isRepair;
	public int repairSlot;
	
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
			
			if(repairSlot > -1)
			{
				slotsLocked = true;
				isRepair = true;
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
	
	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}
}

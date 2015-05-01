package se.rimevel.FeudalFunctions.modules.smithing.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;

public class TileEntityAnvil extends TileEntityContainerBase
{
	public boolean slotsLocked;

	public TileEntityAnvil()
	{
		super(9);
	}
	
	public void onHammering(EntityPlayer player)
	{
		if(worldObj.isRemote){return;}
		
		if(!slotsLocked)
		{
			int repairSlot = getPotentialWeaponRepair();
			
			if(repairSlot > -1)
			{
				doRepair(repairSlot);
				syncData();
			}
		}
	}
	
	@Override
	public void updateEntity()
	{
		//worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
	
	@Override
	protected void writeSyncData(NBTTagCompound compound)
	{
		compound.setBoolean("Locked", this.slotsLocked);
	}
	
	@Override
	protected void readSyncData(NBTTagCompound compound)
	{
		this.slotsLocked = compound.getBoolean("Locked");
	}
	
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

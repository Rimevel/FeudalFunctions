package se.rimevel.FeudalFunctions.core.tiles;

import se.rimevel.FeudalFunctions.core.util.UtilLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.util.Constants;

public class TileEntityContainerBase extends TileEntityBase implements IInventory
{
	public ItemStack[] content;
	
	public TileEntityContainerBase(int contentSize)
	{
		content = new ItemStack[contentSize];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		
		NBTTagList items = new NBTTagList();
		for (int i = 0; i < getSizeInventory(); i++)
		{
			ItemStack stack = getStackInSlot(i);
			if(stack != null)
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		compound.setTag("Items", items);
		
		writeSyncData(compound);
		writeExtraData(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		NBTTagList items = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < items.tagCount(); i++)
		{
			NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			
			if(slot >= 0 && slot < getSizeInventory())
			{
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
		
		readSyncData(compound);
		readExtraData(compound);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound compound = new NBTTagCompound();
		writeSyncData(compound);
		
		NBTTagList items = new NBTTagList();
		
		for (int i = 0; i < getSizeInventory(); i++)
		{
			ItemStack stack = getStackInSlot(i);
			if(stack != null)
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(item);
				items.appendTag(item);
			}
		}
		
		compound.setTag("Items", items);
		
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		NBTTagCompound compound = pkt.func_148857_g();
		
		readSyncData(compound);
		
		NBTTagList items = compound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		
		this.content = new ItemStack[getSizeInventory()];
		
		for (int i = 0; i < items.tagCount(); i++)
		{
			NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			
			if(slot >= 0 && slot < getSizeInventory())
			{
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
		this.worldObj.updateLightByType(EnumSkyBlock.Block, xCoord, yCoord, zCoord);
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public int getSizeInventory()
	{
		return content.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return content[i];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack itemstack = getStackInSlot(slot);
		
		if(itemstack != null)
		{
			if(itemstack.stackSize <= amount)
			{
				setInventorySlotContents(slot, null);
			}
			else
			{
				itemstack = itemstack.splitStack(amount);
				//syncData();
			}
		}
		
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack item = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		content[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
		//this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}

	@Override
	public String getInventoryName()
	{
		return "None";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		if(entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) < 64)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void openInventory()
	{
	
	}

	@Override
	public void closeInventory()
	{
	
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	public boolean slotHasItem(int slot)
	{
		return content[slot] != null;
	}
}

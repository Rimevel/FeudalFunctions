package se.rimevel.FeudalFunctions.core.util;

import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class UtilPlayer
{
	/**
	 * Quick method to get the ItemStack the player is holding.
	 * @param player Player to get the item from.
	 * @return ItemStack the player is holding. Returns null if hand is empty or player does not exist.
	 */
	public static ItemStack getHeldItem(EntityPlayer player)
	{
		if(player != null)
		{
			ItemStack stack = player.inventory.getCurrentItem();
			if(stack != null)
			{
				return stack;
			}
		}
		
		return null;
	}
	
	/**
	 * Is the player holding an item?
	 * @param player The player instance.
	 * @return Returns true if the player is holding an item. Otherwise false.
	 */
	public static boolean isHoldingItem(EntityPlayer player)
	{
		return getHeldItem(player) != null;
	}
	
	/**
	 * Decrease the amount of the item the player is holding.
	 * @param player The player instance.
	 * @param amount The amount to decrease the item stack.
	 */
	public static void decrHeldStack(EntityPlayer player, int amount)
	{
		player.inventory.decrStackSize(player.inventory.currentItem, amount);
	}
	
	/**
	 * Move items from the target inventory to the players inventory. Either a whole stack or a certain amount.
	 * @param player The player instance.
	 * @param tile The target tile entity. Should have an inventory.
	 * @param slot Target slot in the target inventory.
	 * @param amount Amount of items to move. 0 means the same as the whole stack.
	 * @return Returns true if successful. False on failure to move the items.
	 */
	public static boolean transferStackContainerToInventory(EntityPlayer player, TileEntityContainerBase tile, int slot, int amount)
	{
		if(player.inventory.addItemStackToInventory(tile.getStackInSlot(slot)))
		{
			if(amount == 0 || amount == tile.getInventoryStackLimit())
			{
				tile.decrStackSize(slot, tile.getInventoryStackLimit());
				
				return true;
			}
			tile.decrStackSize(slot, amount);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Move items from a slot in the players inventory to a slot in the target tile entities inventory.
	 * @param player The player instance.
	 * @param tile The target tile entity. Should have an inventory.
	 * @param playerSlot A number representing a slot in the players inventory.
	 * @param tileSlot A number representing a slot in the target tile entities inventory.
	 * @param amount Amount of items to move. 0 means the same as the whole stack.
	 * @return Returns true if successful. False on failure to move the items.
	 */
	public static boolean transferStackInventoryToContainer(EntityPlayer player, TileEntityContainerBase tile, int playerSlot, int tileSlot, int amount)
	{
		ItemStack stack = player.inventory.getStackInSlot(playerSlot);
		
		if(tileSlot <= tile.getSizeInventory() && stack != null)
		{
			if(!tile.slotHasItem(tileSlot))
			{
				if(amount <= 0)
				{
					tile.setInventorySlotContents(tileSlot, stack.copy());
					UtilPlayer.emptySlot(player, playerSlot);
					return true;
				}
				{
					tile.setInventorySlotContents(tileSlot, stack.splitStack(amount));
					return true;
				}
			}
			else
			{
				ItemStack tileStack = tile.getStackInSlot(tileSlot);
				
				if(tileStack.getItem() == stack.getItem() && tileStack.getItemDamage() == stack.getItemDamage())
				{
					int stackSpaceLeft = tileStack.getMaxStackSize() - tileStack.stackSize;
					int size = tileStack.stackSize + stack.stackSize;
					
					if(amount <= 0)
					{
						if(size > tileStack.getMaxStackSize())
						{
							tileStack.stackSize += stackSpaceLeft;
							player.inventory.decrStackSize(playerSlot, stackSpaceLeft);
							return true;
						}
						else
						{
							tile.getStackInSlot(tileSlot).stackSize = size;
							UtilPlayer.emptySlot(player, playerSlot);
							return true;
						}
					}
					else
					{
						if(amount > stack.stackSize){amount = stack.stackSize;}
						
						if(tileStack.stackSize + amount > tileStack.getMaxStackSize())
						{
							
							tileStack.stackSize += stackSpaceLeft;
							player.inventory.decrStackSize(playerSlot, stackSpaceLeft);
							return true;
						}
						else
						{
							tileStack.stackSize += amount;
							player.inventory.decrStackSize(playerSlot, amount);
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
	public static void emptySlot(EntityPlayer player, int slot)
	{
		player.inventory.decrStackSize(slot, player.inventory.getInventoryStackLimit());
	}
	
	/**
	 * Get the point in the world where the players line of sight collide with something.
	 * @param world World to get data from.
	 * @param player Player to check.
	 * @param distance Max distance to check.
	 * @return MovingObjectPosition with the collision point. Null if no collision happened.
	 */
	public static MovingObjectPosition getPointPlayerIsLookingAt(World world, EntityPlayer player, double distance)
	{
		Vec3 lookingPos = player.getLookVec();
		
		Vec3 playerPos = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3 targetPos = Vec3.createVectorHelper(player.posX + lookingPos.xCoord * distance, (player.posY + player.getEyeHeight()) + lookingPos.yCoord * distance, player.posZ + lookingPos.zCoord * distance);
		
		MovingObjectPosition pos = UtilVector.clip(world, playerPos, targetPos);
		
		return pos;
	}
	
	/**
	 * Get the point in the world where the players line of sight collide with something. This version is sensitive
	 * to liquids.
	 * @param world World to get data from.
	 * @param player Player to check.
	 * @param distance Max distance to check.
	 * @param collideWithLiquids If true then line of sight will collide with any liquid.
	 * @return MovingObjectPosition with the collision point. Null if no collision happened.
	 */
	public static MovingObjectPosition getPointPlayerIsLookingAt(World world, EntityPlayer player, double distance, boolean collideWithLiquids)
	{
		Vec3 lookingPos = player.getLookVec();
		
		Vec3 playerPos = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
		Vec3 targetPos = Vec3.createVectorHelper(player.posX + lookingPos.xCoord * distance, (player.posY + player.getEyeHeight()) + lookingPos.yCoord * distance, player.posZ + lookingPos.zCoord * distance);
		
		MovingObjectPosition pos = UtilVector.clip(world, playerPos, targetPos, collideWithLiquids);
		
		return pos;
	}
	
	/**
	 * Get the block the player is looking at.
	 * @param world World to get data from.
	 * @param player Player to check.
	 * @param distance Max distance to check.
	 * @return The Block the player is looking at. Null if no block is found.
	 */
	public static Block getBlockPlayerIsLookingAt(World world, EntityPlayer player, double distance)
	{
		MovingObjectPosition pos = getPointPlayerIsLookingAt(world, player, distance);
		if(pos != null)
		{
			return world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
		}
		return null;
	}
}

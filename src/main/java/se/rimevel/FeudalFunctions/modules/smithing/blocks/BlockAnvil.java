package se.rimevel.FeudalFunctions.modules.smithing.blocks;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import scala.actors.threadpool.Arrays;
import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.ui.GuiHandler;
import se.rimevel.FeudalFunctions.core.util.UtilEntity;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import se.rimevel.FeudalFunctions.modules.smithing.items.tools.ItemToolHammer;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityAnvil;

public class BlockAnvil extends BlockContainerBase
{
	public BlockAnvil()
	{
		super(Material.anvil);
		setCreativeTab(CreativeTabs.tabDecorations);
		setCustom3d(true);
		setHardness(2.0F);
		setStepSound(soundTypeAnvil);
		setResistance(8.0F);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z)
	{
		int meta = block.getBlockMetadata(x, y, z);
		
		if(meta % 2 == 0)
		{
			this.setBlockBounds(0F, 0.0F, 0.1875F, 1F, 1F, 0.8125F);
		}
		else
		{
			this.setBlockBounds(0.1875F, 0F, 0F, 0.8125F, 1F, 1F);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return new TileEntityAnvil();
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		ForgeDirection dir = UtilEntity.getDirectionEntityIsFacing(entity);
		world.setBlockMetadataWithNotify(x, y, z, UtilEntity.translateToCompassMeta(dir), 2);
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntityAnvil tile = (TileEntityAnvil)world.getTileEntity(x, y, z);
		if(world.isRemote || !UtilPlayer.isHoldingItem(player)){return;}
		
		if(UtilPlayer.getHeldItem(player).getItem() instanceof ItemToolHammer)
		{
			tile.onHammering(player);
			if(player.inventory.getCurrentItem().attemptDamageItem(1, world.rand))
			{
				player.inventory.decrStackSize(player.inventory.currentItem, 1);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntityAnvil tile = (TileEntityAnvil)world.getTileEntity(x, y, z);
		ItemStack held = UtilPlayer.getHeldItem(player);
		
		if(player.isSneaking())
		{
			if(!world.isRemote)
			{
				GuiHandler.openGui(player, 1, world, x, y, z);
			}
		}
		else
		{
			int slotHit = calculateSlotFromHit(hitX, hitY, hitZ, world.getBlockMetadata(x, y, z));
			if(slotHit == -1 || tile.slotsLocked){return false;}
			
			if(UtilPlayer.isHoldingItem(player) && !tile.slotHasItem(slotHit))
			{
				tile.setInventorySlotContents(slotHit, held.copy());
				UtilPlayer.decrHeldStack(player, 1);
				world.markBlockForUpdate(x, y, z);
				return true;
			}
			else if(!UtilPlayer.isHoldingItem(player) && tile.slotHasItem(slotHit))
			{
				UtilPlayer.transferStackContainerToInventory(player, tile, slotHit, 0);
				world.markBlockForUpdate(x, y, z);
				return true;
			}
		}
		
		return false;
	}
	
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
	{
			TileEntityContainerBase tile = (TileEntityContainerBase)world.getTileEntity(x, y, z);
			if(tile != null)
			{
				dropStacks(world, x, y, z, meta, tile.content, isChangingState);
			}
	}
	
	public int calculateSlotFromHit(float hitX, float hitY, float hitZ, int meta)
	{
		if(hitY > 0.9F && hitX < 0.875F && hitX > 0.125F && hitZ < 0.875F && hitZ > 0.125F)
		{
			if(hitX > 0.125F && hitX < 0.375F)
			{
				if(hitZ > 0.125F && hitZ < 0.375F){return getSlotMappingIndex(meta, 8);}
				if(hitZ > 0.375F && hitZ < 0.625F){return getSlotMappingIndex(meta, 5);}
				if(hitZ > 0.625F && hitZ < 0.875F){return getSlotMappingIndex(meta, 2);}
			}
			else if(hitX > 0.375F && hitX < 0.625F)
			{
				if(hitZ > 0.125F && hitZ < 0.375F){return getSlotMappingIndex(meta, 7);}
				if(hitZ > 0.375F && hitZ < 0.625F){return getSlotMappingIndex(meta, 4);}
				if(hitZ > 0.625F && hitZ < 0.875F){return getSlotMappingIndex(meta, 1);}
			}
			else if(hitX > 0.625F && hitX < 0.875F)
			{
				if(hitZ > 0.125F && hitZ < 0.375F){return getSlotMappingIndex(meta, 6);}
				if(hitZ > 0.375F && hitZ < 0.625F){return getSlotMappingIndex(meta, 3);}
				if(hitZ > 0.625F && hitZ < 0.875F){return getSlotMappingIndex(meta, 0);}
			}
		}
		
		return -1;
	}
	
	public static int getSlotMappingIndex(int rotation, int slot)
	{
		int[] south = new int[]{0, 1, 2,
						   	    3, 4, 5,
								6, 7, 8};
		
		int[] west =  new int[]{6, 3, 0,
		   	    				7, 4, 1,
		   	    				8, 5, 2};
		
		int[] north = new int[]{8, 7, 6,
		   	    				5, 4, 3,
		   	    				2, 1, 0};
		
		int[] east =  new int[]{2, 5, 8,
		   	    				1, 4, 7,
		   	    				0, 3, 6};
		
		if(rotation == 0){return south[slot];}
		if(rotation == 1){return west[slot];}
		if(rotation == 2){return north[slot];}
		if(rotation == 3){return east[slot];}
		
		return -1;
	}
}

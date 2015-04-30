package se.rimevel.FeudalFunctions.modules.survival.blocks;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import se.rimevel.FeudalFunctions.modules.survival.MSurvival;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityCampfire;

public class BlockCampfire extends BlockContainerBase
{
	private static int instanceNumber;
	
	public BlockCampfire()
	{
		super(Material.wood);
		setStepSound(soundTypeWood);
		setLightOpacity(1);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		setTickRandomly(true);
		setResistance(0.4F);
		
		if(this.instanceNumber == 1)
		{
			setLightLevel(1F);
		}
		else
		{
			setCreativeTab(CreativeTabs.tabDecorations);
		}
		
		instanceNumber++;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return new TileEntityCampfire();
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		ItemStack held = UtilPlayer.getHeldItem(player);
		TileEntityCampfire tile = (TileEntityCampfire)world.getTileEntity(x, y, z);
		
		if(UtilPlayer.isHoldingItem(player) && !player.isSneaking())
		{
			if(world.isRemote){return false;}
			
			if(getFuelValue(held) > 0)
			{
				if(tile.addFuel(getFuelValue(held)))
				{
					UtilPlayer.decrHeldStack(player, 1);
					return true;
				}
			}
			
			if(held.getItem() == Items.flint_and_steel)
			{
				tile.setActive(true);
			}
		}
		
		return false;
	}
	
	private int getFuelValue(ItemStack stack)
	{
		if(stack != null)
		{
			if(stack.getItem() == Items.stick){return 200;}
			if(UtilOreDict.compareItem(stack, "treeWood")){return 800;}
		}
		
		return 0;
	}
	
	public static void updateBlockMeta(boolean active, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if (active)
		{
			world.setBlock(x, y, z, MSurvival.blocks.campfire_active.getInstance());
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}
		else
		{
			world.setBlock(x, y, z, MSurvival.blocks.campfire.getInstance());
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}
		
		if (tile != null)
		{
			tile.validate();
			world.setTileEntity(x, y, z, tile);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if(world.getBlockMetadata(x, y, z) == 1)
		{
			world.spawnParticle("smoke", (double)(x + 0.4F + (random.nextFloat() / 4)), (double)(y + 0.1F + (random.nextFloat() / 4)), (double)(z + 0.4F + (random.nextFloat() / 4)), 0.0D, 0.1D, 0.0D);
		    world.spawnParticle("flame", (double)(x + 0.4F + (random.nextFloat() / 4)), (double)(y + 0.1F + (random.nextFloat() / 4)), (double)(z + 0.4F + (random.nextFloat() / 4)), 0.0D, 0.0D, 0.0D);
		    if (random.nextInt(24) == 0)
	        {
	            world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
	        }
		}
	}
}

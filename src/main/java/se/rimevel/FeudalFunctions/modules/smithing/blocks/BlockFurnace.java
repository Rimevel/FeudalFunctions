package se.rimevel.FeudalFunctions.modules.smithing.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilEntity;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import se.rimevel.FeudalFunctions.core.util.UtilVector;
import se.rimevel.FeudalFunctions.core.util.helpers.HelperVector;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityFurnace;

public class BlockFurnace extends BlockContainerBase
{
	static int instanceNumber;
	
	public BlockFurnace()
	{
		super(Material.rock);
		if(instanceNumber == 1)
		{
			this.setLightLevel(1F);
		}
		else
		{
			setCreativeTab(CreativeTabs.tabDecorations);
		}
		setStepSound(soundTypeStone);
		setLightOpacity(1);
		setTickRandomly(true);
		setResistance(0.4F);
		setHardness(1F);
		
		setTextureNames("single", "topleft", "top", "topright", "left", "middle", "right",
						"bottomleft", "bottom", "bottomright", "input", "output_off", "output");
		
		instanceNumber++;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int something)
	{
		return new TileEntityFurnace();
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		
		
	}
	
	private TileEntityFurnace getTile(World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		if(world.getTileEntity(x, y, z) != null)
		{
			return (TileEntityFurnace) tile;
		}
		
		return null;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if(!UtilPlayer.isHoldingItem(player))
		{
			if(world.getBlockMetadata(x, y, z) > 0)
			{
				TileEntityFurnace tile = getTile(world, x, y, z);
				if(tile != null && tile.isWhole() == true)
				{
					tile.getController().checkIfWhole();
				}
				
				return false;
			}
			
			ForgeDirection dir = UtilEntity.getDirectionEntityIsFacing(player);
			if(dir != ForgeDirection.UNKNOWN)
			{
				buildFurnace(world, x, y, z, dir);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public IIcon getIcon(IBlockAccess bA, int x, int y, int z, int side)
	{
		int meta = bA.getBlockMetadata(x, y, z);
		
		if(meta == 0){return getTexture(0);}
		
		if(meta == 2)
		{
			if(side == 2) //North
			{
				if(isCore(bA, x, y, z + 1))	{return getTexture(10);}
				return getTexture(5);
			}
			if(side == 3) //South
			{
				if(isCore(bA, x, y, z - 1))	{return getTexture(10);}
				return getTexture(5);
			}
			if(side == 4) //West
			{
				if(isCore(bA, x + 1, y, z))	{return getTexture(10);}
				return getTexture(5);
			}
			if(side == 5) //East
			{
				if(isCore(bA, x - 1, y, z))	{return getTexture(10);}
				return getTexture(5);
			}
		}
		
		if(meta == 3)
		{
			if(side == 2) //North
			{
				if(isCore(bA, x, y + 1, z + 1))	{return getTexture(11);}
				return getTexture(5);
			}
			if(side == 3) //South
			{
				if(isCore(bA, x, y + 1, z - 1))	{return getTexture(11);}
				return getTexture(5);
			}
			if(side == 4) //West
			{
				if(isCore(bA, x + 1, y + 1, z))	{return getTexture(11);}
				return getTexture(5);
			}
			if(side == 5) //East
			{
				if(isCore(bA, x - 1, y + 1, z))	{return getTexture(11);}
				return getTexture(5);
			}
		}
		
		if(side == 0) //Bottom
		{
			if(isCore(bA, x + 1, y + 1, z - 1))	{return getTexture(7);}
			if(isCore(bA, x, y + 1, z - 1))		{return getTexture(8);}
			if(isCore(bA, x - 1, y + 1, z - 1))	{return getTexture(9);}
			if(isCore(bA, x + 1, y + 1, z))		{return getTexture(4);}
			if(isCore(bA, x, y + 1, z))			{return getTexture(5);}
			if(isCore(bA, x - 1, y + 1, z))		{return getTexture(6);}
			if(isCore(bA, x + 1, y + 1, z + 1))	{return getTexture(1);}
			if(isCore(bA, x, y + 1, z + 1))		{return getTexture(2);}
			if(isCore(bA, x - 1, y + 1, z + 1))	{return getTexture(3);}
		}
		if(side == 1) //Top
		{
			if(isCore(bA, x + 1, y - 1, z - 1))	{return getTexture(7);}
			if(isCore(bA, x, y - 1, z - 1))		{return getTexture(8);}
			if(isCore(bA, x - 1, y - 1, z - 1))	{return getTexture(9);}
			if(isCore(bA, x + 1, y - 1, z))		{return getTexture(4);}
			if(isCore(bA, x, y - 1, z))			{return getTexture(5);}
			if(isCore(bA, x - 1, y - 1, z))		{return getTexture(6);}
			if(isCore(bA, x + 1, y - 1, z + 1))	{return getTexture(1);}
			if(isCore(bA, x, y - 1, z + 1))		{return getTexture(2);}
			if(isCore(bA, x - 1, y - 1, z + 1))	{return getTexture(3);}
		}
		if(side == 2) //North
		{
			if(isCore(bA, x + 1, y - 1, z + 1))	{return getTexture(3);}
			if(isCore(bA, x, y - 1, z + 1))		{return getTexture(2);}
			if(isCore(bA, x - 1, y - 1, z + 1))	{return getTexture(1);}
			if(isCore(bA, x + 1, y, z + 1))		{return getTexture(6);}
			if(isCore(bA, x, y, z + 1))			{return getTexture(5);}
			if(isCore(bA, x - 1, y, z + 1))		{return getTexture(4);}
			if(isCore(bA, x + 1, y + 1, z + 1))	{return getTexture(9);}
			if(isCore(bA, x, y + 1, z + 1))		{return getTexture(8);}
			if(isCore(bA, x - 1, y + 1, z + 1))	{return getTexture(7);}
		}
		if(side == 3) //South
		{
			if(isCore(bA, x + 1, y - 1, z - 1))	{return getTexture(1);}
			if(isCore(bA, x, y - 1, z - 1))		{return getTexture(2);}
			if(isCore(bA, x - 1, y - 1, z - 1))	{return getTexture(3);}
			if(isCore(bA, x + 1, y, z - 1))		{return getTexture(4);}
			if(isCore(bA, x, y, z - 1))			{return getTexture(5);}
			if(isCore(bA, x - 1, y, z - 1))		{return getTexture(6);}
			if(isCore(bA, x + 1, y + 1, z - 1))	{return getTexture(7);}
			if(isCore(bA, x, y + 1, z - 1))		{return getTexture(8);}
			if(isCore(bA, x - 1, y + 1, z - 1))	{return getTexture(9);}
		}
		if(side == 4) //West
		{
			if(isCore(bA, x + 1, y - 1, z + 1))	{return getTexture(1);}
			if(isCore(bA, x + 1, y - 1, z))		{return getTexture(2);}
			if(isCore(bA, x + 1, y - 1, z - 1))	{return getTexture(3);}
			if(isCore(bA, x + 1, y, z + 1))		{return getTexture(4);}
			if(isCore(bA, x + 1, y, z))			{return getTexture(5);}
			if(isCore(bA, x + 1, y, z - 1))		{return getTexture(6);}
			if(isCore(bA, x + 1, y + 1, z + 1))	{return getTexture(7);}
			if(isCore(bA, x + 1, y + 1, z))		{return getTexture(8);}
			if(isCore(bA, x + 1, y + 1, z - 1))	{return getTexture(9);}
		}
		if(side == 5) //East
		{
			if(isCore(bA, x - 1, y - 1, z - 1))	{return getTexture(1);}
			if(isCore(bA, x - 1, y - 1, z))		{return getTexture(2);}
			if(isCore(bA, x - 1, y - 1, z + 1))	{return getTexture(3);}
			if(isCore(bA, x - 1, y, z - 1))		{return getTexture(4);}
			if(isCore(bA, x - 1, y, z))			{return getTexture(5);}
			if(isCore(bA, x - 1, y, z + 1))		{return getTexture(6);}
			if(isCore(bA, x - 1, y + 1, z - 1))	{return getTexture(7);}
			if(isCore(bA, x - 1, y + 1, z))		{return getTexture(8);}
			if(isCore(bA, x - 1, y + 1, z + 1))	{return getTexture(9);}
		}
		
		return getTexture(5);
	}
	
	private boolean isCore(IBlockAccess world, int x, int y, int z)
	{
		final int CORE_META = 15;
		
		return world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == CORE_META;
	}
	
	private boolean buildFurnace(World world, int x, int y, int z, ForgeDirection dir)
	{
		if(world.isRemote){return false;}
		
		int oX = x, oY = y, oZ = z;
		
		if(dir == ForgeDirection.NORTH)
		{
			z -= 1;
		}
		
		if(dir == ForgeDirection.SOUTH)
		{
			z += 1;
		}
		
		if(dir == ForgeDirection.EAST)
		{
			x += 1;
		}
		
		if(dir == ForgeDirection.WEST)
		{
			x -= 1;
		}
		
		for (int h = 1; h > -2; h--)
		{
			for (int l = -1; l < 2; l++)
			{
				for (int w = -1; w < 2; w++)
				{
					if(world.getBlock(x + w, y + h, z + l) != this || world.getBlockMetadata(x + w, y + h, z + l) != 0)
					{
						UtilLog.info("INSIDE: " + "x(" + (x + w) + ") y(" + (y + h) + ") z(" + (z + l) + ")");
						return false;
					}
				}
			}
		}
		
		for (int h = 1; h > -2; h--)
		{
			for (int l = -1; l < 2; l++)
			{
				for (int w = -1; w < 2; w++)
				{
					if(w == 0 && h == 0 && l == 0)
					{
						world.setBlockMetadataWithNotify(x + w, y + h, z + l, 15, 2);
						getTile(world, x, y, z).setAsTileController();
					}
					else
					{
						world.setBlockMetadataWithNotify(x + w, y + h, z + l, 1, 2);
						getTile(world, x + w, y + h, z + l).setControllerLocation(x, y, z);
					}
				}
			}
		}
		
		world.setBlockMetadataWithNotify(oX, oY, oZ, 2, 2);
		world.setBlockMetadataWithNotify(oX, oY - 1, oZ, 3, 2);
		
		return true;
	}
}

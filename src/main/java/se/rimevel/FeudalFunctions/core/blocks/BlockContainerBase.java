package se.rimevel.FeudalFunctions.core.blocks;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;

public class BlockContainerBase extends BlockBase implements ITileEntityProvider
{
	/**
	 * This class is a tile entity specific extension of BlockBase.
	 */
	public BlockContainerBase()
	{
		super();
	}
	
	public BlockContainerBase(Material material, String ... names)
	{
		super(material);
		setTextureNames(names);
	}
	
	public BlockContainerBase(Material material)
	{
		super(material);
	}
	
	public BlockContainerBase(String ... names)
	{
		super();
		setTextureNames(names);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return null;
	}
	
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
	{
		TileEntityContainerBase tile = (TileEntityContainerBase)world.getTileEntity(x, y, z);
		if(tile != null)
		{
			dropStacks(world, x, y, z, meta, tile.content);
		}
	}
	
	public static void dropStacks(World world, int x, int y, int z, int meta, ItemStack[] stacks)
	{
		float f3 = 0.05F;		
		
	    Random rand = new Random();
	    float f = rand.nextFloat() * 0.8F + 0.1F;
	    float f1 = rand.nextFloat() * 0.8F + 0.3F;
	    float f2 = rand.nextFloat() * 0.8F + 0.1F;
		
		for (int i = 0; i < stacks.length; i++)
		{
			if(stacks[i] != null)
			{
				EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, stacks[i]);
				
				entityitem.motionX = ((float)rand.nextGaussian() * f3);
			    entityitem.motionY = ((float)rand.nextGaussian() * f3 + 0.2F);
			    entityitem.motionZ = ((float)rand.nextGaussian() * f3);
			    world.spawnEntityInWorld(entityitem);
			}
		}
	}
}

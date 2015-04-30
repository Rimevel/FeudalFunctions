package se.rimevel.FeudalFunctions.modules.smithing.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityCrucible;

public class BlockCrucible extends BlockContainerBase
{
	public BlockCrucible()
	{
		super(Material.clay);
		setCreativeTab(CreativeTabs.tabDecorations);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.625F, 0.8125F);
		setCustom3d(true);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return new TileEntityCrucible();
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
}

package se.rimevel.FeudalFunctions.modules.survival.blocks;

import java.util.ArrayList;
import java.util.Random;

import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.modules.survival.MSurvival;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityGround;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGround extends BlockContainerBase
{
	public BlockGround()
	{
		super(Material.ground);
		setBlockBounds(0F, 0F, 0F, 1F, 0.1F, 1F);
		setCreativeTab(CreativeTabs.tabDecorations);
		setCustom3d(true);
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata)
	{
		return new TileEntityGround();
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
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
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		
		switch (metadata)
		{
			case 0:
				items.add(new ItemStack(Items.stick));
			break;

			default:
				items.add(new ItemStack(MSurvival.items.rock.getInstance()));
			break;
		}
		
		return items;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		world.setBlockMetadataWithNotify(x, y, z, (int)UtilMath.randomInRange(0, 2), 2);
	}
}

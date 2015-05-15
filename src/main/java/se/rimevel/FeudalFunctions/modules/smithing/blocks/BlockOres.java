package se.rimevel.FeudalFunctions.modules.smithing.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import se.rimevel.FeudalFunctions.core.blocks.BlockBase;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOres extends BlockBase
{
	public BlockOres()
	{
		super("copper", "tin");
		setCreativeTab(CreativeTabs.tabBlock);
		
		setHarvestLevel("pickaxe", 0, 0);
		setHarvestLevel("pickaxe", 1, 1);
		
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypeStone);
	}
	
	/*@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		if(UtilMath.randomInRange(0, 10) >= 7)
		{
			drops.add(new ItemStack(MSmithing, 1, metadata));
			drops.add(new ItemStack(ItemRegistry.ORES_POOR.INSTANCE, 1, metadata));
			return drops;
		}
		drops.add(new ItemStack(ItemRegistry.ORES.INSTANCE, 1, metadata));
		return drops;
	}*/
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < 2; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
}

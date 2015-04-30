package se.rimevel.FeudalFunctions.modules.survival.world;

import java.util.Random;

import se.rimevel.FeudalFunctions.modules.survival.MSurvival;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class GeneratorRocks extends WorldGenerator
{
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
	    if(world.isAirBlock(x, y, z))
		{
			if(isNaturalMaterial(world, x, y - 1, z) && world.isAirBlock(x, y + 1, z) && world.isBlockNormalCubeDefault(x, y - 1, z, true))
			{
				world.setBlock(x, y, z, MSurvival.blocks.groundPickup.getInstance());
				world.setBlockMetadataWithNotify(x, y, z, 1, 2);
				world.markBlockForUpdate(x, y, z);
				return true;
			}
		}
	    else if(block != null && block.isFoliage(world, x, y, z))
	    {
	    	if(isNaturalMaterial(world, x, y - 1, z) && world.isAirBlock(x, y + 1, z) && world.isBlockNormalCubeDefault(x, y - 1, z, true))
			{
				world.setBlock(x, y, z, MSurvival.blocks.groundPickup.getInstance());
				world.setBlockMetadataWithNotify(x, y, z, 1, 2);
				world.markBlockForUpdate(x, y, z);
				return true;
			}
	    }
	    return false;
	}
	
	public static boolean isNaturalMaterial(World world, int x, int y, int z)
	{
		if(world.getBlock(x, y, z).getMaterial() == Material.ground)
		{
			return true;
		}
		if(world.getBlock(x, y, z).getMaterial() == Material.sand)
		{
			return true;
		}
		if(world.getBlock(x, y, z).getMaterial() == Material.rock)
		{
			return true;
		}
		if(world.getBlock(x, y, z).getMaterial() == Material.grass)
		{
			return true;
		}
		
		return false;
	}
}

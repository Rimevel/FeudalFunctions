package se.rimevel.FeudalFunctions.modules.survival.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class GenerationHandler implements IWorldGenerator
{
	private WorldGenerator smallRockGen;
	private WorldGenerator stickGen;
	
	public GenerationHandler()
	{
		GameRegistry.registerWorldGenerator(this, 1000);
		
		smallRockGen = new GeneratorRocks();
		stickGen = new GeneratorSticks();
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		generateRocks(random, chunkX, chunkZ, world, 80, smallRockGen, 20, 128);
		generateSticks(random, chunkX, chunkZ, world, 80, smallRockGen, 60, 128);
	}
	
	private void generateRocks(Random random, int chunkX, int chunkZ, World world, int iterations, WorldGenerator smallRockGen, int lowestY, int highestY)
	{
		for (int i = 0; i < iterations; i++)
		{
			int x = (chunkX * 16) + random.nextInt(16);
			int y = random.nextInt(highestY - lowestY) + lowestY;
			int z = (chunkZ * 16) + random.nextInt(16);
			if(smallRockGen.generate(world, random, x, y, z)) {}
		}
	}
	
	private void generateSticks(Random random, int chunkX, int chunkZ, World world, int iterations, WorldGenerator smallRockGen, int lowestY, int highestY)
	{
		for (int i = 0; i < iterations; i++)
		{
			int x = (chunkX * 16) + random.nextInt(16);
			int y = random.nextInt(highestY - lowestY) + lowestY;
			int z = (chunkZ * 16) + random.nextInt(16);
			
			if(BiomeDictionary.isBiomeOfType(world.getWorldChunkManager().getBiomeGenAt(x, z), Type.FOREST))
		    {
				if(stickGen.generate(world, random, x, y, z)) {}
		    }
		}
	}
}

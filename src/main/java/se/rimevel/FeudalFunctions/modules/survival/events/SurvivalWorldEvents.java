package se.rimevel.FeudalFunctions.modules.survival.events;

import java.util.List;

import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import se.rimevel.FeudalFunctions.core.util.helpers.HelperVector;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerDataStats;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerTickTemperature;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SurvivalWorldEvents
{
	@SubscribeEvent
	public void onPlacingBlock(BlockEvent.PlaceEvent event)
	{
		if(event.world.isRemote){return;}
		
		BiomeGenBase biome = event.world.getBiomeGenForCoords(event.x, event.z);
		
		if(BiomeDictionary.isBiomeOfType(biome, Type.NETHER) || BiomeDictionary.isBiomeOfType(biome, Type.HOT))
		{
			//if(Block.isEqualTo(event.block, Blocks.ice))
			//{
			//	event.world.setBlock(event.x, event.y, event.z, Blocks.water);
			//	return;
			//}
			if(event.block == Blocks.snow || event.block == Blocks.snow_layer)
			{
				event.world.setBlock(event.x, event.y, event.z, Blocks.flowing_water);
				event.world.setBlockMetadataWithNotify(event.x, event.y, event.z, 7, 2);
				return;
			}
		}	
	}
	
	@SubscribeEvent
	public void onPlayerBreakSpeed(BreakSpeed event)
	{
		if(event.entity == null){return;}
		
		World world = event.entity.worldObj;
		Block block = event.block;
		ItemStack held = UtilPlayer.getHeldItem(event.entityPlayer);
		
		if(!(event.entity instanceof EntityPlayer)){return;}
		
		//Stone
		if(Block.isEqualTo(event.block , Blocks.stone))
		{
			if(held != null && ((ItemTool) held.getItem()).getHarvestLevel(held, "pickaxe") > 3)
			{
				return;
			}	
			else
			{
				event.newSpeed = 0.05F;
			}
		}
		//Logs
		if(UtilOreDict.compareBlock(event.block, "logWood"))
		{
			if(held != null && ForgeHooks.canToolHarvestBlock(block, 0, held))
			{
				return;
			}
			else
			{
				event.newSpeed = 0.1F;
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerBlockBreak(HarvestDropsEvent event)
	{
		if(event.harvester == null){return;}
		
		World world = event.harvester.worldObj;
		Block block = event.block;
		ItemStack held = UtilPlayer.getHeldItem(event.harvester);
		
		if(!(event.harvester instanceof EntityPlayer) || world.isRemote){return;}
		
		//Stone
		if(Block.isEqualTo(event.block , Blocks.stone))
		{
			if(held != null && ((ItemTool) held.getItem()).getHarvestLevel(held, "pickaxe") > 3)
			{
				return;
			}	
			else
			{
				event.drops.clear();
			}
		}
		//Logs
		if(UtilOreDict.compareBlock(event.block, "logWood"))
		{
			if(held != null && ForgeHooks.canToolHarvestBlock(block, 0, held))
			{
				return;
			}
			else
			{
				event.dropChance = 0F;
			}
		}
	}
	
	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Detonate event)
	{
		List<ChunkPosition> blocks = event.explosion.affectedBlockPositions;
		
		for(ChunkPosition pos : blocks)
		{
			int x = pos.chunkPosX;
			int y = pos.chunkPosY;
			int z = pos.chunkPosZ;
			
			if(event.world.getBlock(x, y + 1, z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(x, y + 1, z, Blocks.cobblestone);
			}
			if(event.world.getBlock(x - 1, y, z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(x - 1, y, z, Blocks.cobblestone);
			}
			if(event.world.getBlock(x + 1, y, z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(x + 1, y, z, Blocks.cobblestone);
			}
			if(event.world.getBlock(x, y, z + 1) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(x, y, z + 1, Blocks.cobblestone);
			}
			if(event.world.getBlock(x, y, z - 1) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(x, y, z - 1, Blocks.cobblestone);
			}
			if(event.world.getBlock(x, y - 1, z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(x, y - 1, z, Blocks.cobblestone);
			}
		}
	}
	
	@SubscribeEvent
	public void onBlockBreak(BreakEvent event)
	{
		ItemStack held = UtilPlayer.getHeldItem(event.getPlayer());
		
		if(UtilOreDict.compareBlock(event.block, "ore"))
		{
			if(event.world.getBlock(event.x, event.y + 1, event.z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(event.x, event.y + 1, event.z,  Blocks.cobblestone);
			}
			if(event.world.getBlock(event.x - 1, event.y, event.z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(event.x - 1, event.y, event.z,  Blocks.cobblestone);
			}
			if(event.world.getBlock(event.x + 1, event.y, event.z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(event.x + 1, event.y, event.z,  Blocks.cobblestone);
			}
			if(event.world.getBlock(event.x, event.y, event.z + 1) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(event.x, event.y, event.z + 1,  Blocks.cobblestone);
			}
			if(event.world.getBlock(event.x, event.y, event.z - 1) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(event.x, event.y, event.z - 1,  Blocks.cobblestone);
			}
			if(event.world.getBlock(event.x, event.y - 1, event.z) == Blocks.stone && UtilMath.randomInRange(0, 1) > 0.7F)
			{
				event.world.setBlock(event.x, event.y - 1, event.z,  Blocks.cobblestone);
			}
		}
	}
}

package se.rimevel.FeudalFunctions.modules.survival.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import se.rimevel.FeudalFunctions.core.util.UtilArmorMaterials;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import se.rimevel.FeudalFunctions.modules.survival.interfaces.ITemperatureModifier;
import se.rimevel.FeudalFunctions.modules.survival.potions.PotionFrostbite;
import se.rimevel.FeudalFunctions.modules.survival.potions.PotionSunstroke;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;

public class PlayerTickTemperature
{	
	/**
	 * List of all blocks with temperature modifiers.
	 */
	public static HashMap<Block, Float> blockMod; 
	
	/**
	 * Ticks once for each player every tick.
	 * @param event
	 */
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{	
		if(event.side == Side.SERVER)
		{
			EntityPlayer player = event.player;
			
			if((player.worldObj.getTotalWorldTime() & (128-1)) != 0){return;}
			
			int x = (int) player.posX;
			int y = (int) player.posY;
			int z = (int) player.posZ;
			
			float temp = getTemperatureAtLocation(player.worldObj, x, y, z);
			
			if(player.isBurning())
			{
				PlayerDataStats.get(player).adjustBodyTemp(100);
			}
			else if(player.isInWater())
			{
				if(temp < 0)
				{
					temp -= 1.0F;
				}
				else
				{
					temp -= 0.5F;
				}
			}
			else
			{
				temp += getArmorTempModifier(player, temp);
			}
			
			float hydro;
			
			if(temp > 5.0F)
			{
				hydro = -1F;
			}
			else if(temp > 1F)
			{
				hydro = -0.5F;
			}
			else if(temp > 0F)
			{
				hydro = -0.2F;
			}
			else
			{
				hydro = -0.1F;
			}
			
			if(player.isSprinting())
			{
				hydro *= 2;
			}
			
			PlayerDataStats.get(player).adjustBodyHydro(hydro);
			
			UtilLog.info("TempMod:" + temp);
			
			if(!player.isBurning() && temp < 7.0F && temp > 0F && PlayerDataStats.get(player).getHydration() > 0F)
			{
				float t = PlayerDataStats.get(player).getTemperature();
				if(t <= 75F)
				{
					PlayerDataStats.get(player).adjustBodyTemp(temp);
				}
			}
			else
			{
				PlayerDataStats.get(player).adjustBodyTemp(temp);
			}
			
			PlayerDataStats.get(player).dataSync();
			
			if(PlayerDataStats.get(player).getTemperature() >= 100)
			{
				player.addPotionEffect(new PotionEffect(PotionSunstroke.INSTANCE.id, 140, 0));
			}
			
			if(PlayerDataStats.get(player).getTemperature() <= 0)
			{
				player.addPotionEffect(new PotionEffect(PotionFrostbite.INSTANCE.id, 140, 0));
			}
		}
	}
	
	public static void initBlockTempValues()
	{	
		if(blockMod != null){return;}
		
		blockMod = new HashMap<Block, Float>();
		
		blockMod.put(Blocks.fire, 1.0F);
		blockMod.put(Blocks.lava, 7.5F);
		blockMod.put(Blocks.snow, -1.0F);
		blockMod.put(Blocks.snow_layer, -0.2F);
		blockMod.put(Blocks.ice, -0.7F);
		blockMod.put(Blocks.packed_ice, -2.0F);
	}
	
	public static float getTemperatureAtLocation(World world, int x, int y, int z)
	{
		float tempMod = 0;
		float biomeTempMod = 0;
		
		
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		
		if(BiomeDictionary.isBiomeOfType(biome, Type.NETHER))
		{biomeTempMod += 10.0F;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.HOT))
		{biomeTempMod += 1.0F;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.COLD))
		{biomeTempMod += -1.0F;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.WET))
		{biomeTempMod += -0.5F;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.DRY))
		{biomeTempMod += 0.5F;}
		
		if(!world.canBlockSeeTheSky(x, y, z))
		{
			if(y < 50)
			{
				tempMod = 0F;
			}
			else if(y < 25)
			{
				tempMod = -0.25F;
			}
		}
		else
		{
			tempMod += biomeTempMod;
			
			if(!world.isDaytime())
			{
				tempMod += -0.5F;
			}
			if(world.isRaining())
			{
				tempMod += -0.5F;
			}
		}
		
		final int SCAN_RADIUS = 4;
		
		int pX;
		int pY;
		int pZ;
		
		ArrayList<Float> values = new ArrayList<Float>();
		
		for(pY = y + (SCAN_RADIUS * -1); pY < (y + SCAN_RADIUS + 1); pY++)
		{
			for (pX = x + (SCAN_RADIUS * -1); pX < (x + SCAN_RADIUS + 1); pX++)
			{
				for (pZ = z + (SCAN_RADIUS * -1); pZ < (z + SCAN_RADIUS + 1); pZ++)
				{
					Block worldBlock = world.getBlock(pX, pY, pZ);
					if(worldBlock == null || world.isAirBlock(pX, pY, pZ)){continue;}
					
					if(worldBlock instanceof ITemperatureModifier)
					{
						values.add(((ITemperatureModifier)worldBlock).getTempMod());
						continue;
					}
					
					for (Entry e : blockMod.entrySet())
					{
						Block block = (Block) e.getKey();
						float mod = (Float) e.getValue();
						
						if(Block.isEqualTo(block, worldBlock))
						{
							values.add(mod);
						}
					}
				}
			}
		}
		
		tempMod += UtilMath.calculateAvarage(values);
		
		return tempMod;
	}
	
	public static float getBlockTempModifier(Block block)
	{
		if(block instanceof ITemperatureModifier)
		{
			return((ITemperatureModifier)block).getTempMod();
		}
		
		for (Entry e : blockMod.entrySet())
		{
			Block eBlock = (Block) e.getKey();
			float mod = (Float) e.getValue();
			
			if(Block.isEqualTo(block, eBlock))
			{
				return mod;
			}
		}
		return 0F;
	}
	
	public static float getArmorTempModifier(EntityPlayer player, float temp)
	{
		float tempMod = 0;
		
		for(ItemStack stack : UtilPlayer.getArmorArray(player))
		{
			if(stack == null){continue;}
			
			if(stack.getItem() instanceof ITemperatureModifier)
			{
				tempMod += ((ITemperatureModifier)stack.getItem()).getTempMod();
				continue;
			}
			
			if(!(stack.getItem() instanceof ItemArmor)){continue;}
			
			for (UtilArmorMaterials armor : UtilArmorMaterials.values())
			{
				if(armor.getMaterial() == ((ItemArmor)stack.getItem()).getArmorMaterial())
				{
					tempMod += armor.getTemperatureModifier(temp);
				}
			}
		}

		return tempMod / player.inventory.armorInventory.length;
	}
}

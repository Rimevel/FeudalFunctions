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
	 * List of all armors with temperature modifiers.
	 */
	public static ArrayList<UtilArmorMaterials> armorMod;
	
	/**
	 * List of all blocks with temperature modifiers.
	 */
	public static HashMap<Block, Integer> blockMod; 
	
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
			
			int temp = getTemperatureAtLocation(player.worldObj, x, y, z);
			
			//UtilLog.info("NoArmor:" + temp);
			temp += getArmorTempModifier(player, temp);
			//UtilLog.info("WithArmor:" + temp);
			PlayerDataStats.get(player).adjustBodyTemp(temp);
			
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
		
		blockMod = new HashMap<Block, Integer>();
		
		blockMod.put(Blocks.fire, 3);
		blockMod.put(Blocks.lava, 5);
		blockMod.put(Blocks.snow, -2);
		blockMod.put(Blocks.snow_layer, -1);
		blockMod.put(Blocks.ice, -2);
		blockMod.put(Blocks.packed_ice, -2);
	}
	
	public static int getTemperatureAtLocation(World world, int x, int y, int z)
	{
		int tempMod = 0;
		
		BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
		
		if(BiomeDictionary.isBiomeOfType(biome, Type.NETHER))
		{tempMod += 8;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.HOT))
		{tempMod += 2;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.COLD))
		{tempMod += -2;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.WET))
		{tempMod += -1;}
		if(BiomeDictionary.isBiomeOfType(biome, Type.DRY))
		{tempMod += 1;}
		if(!world.canBlockSeeTheSky(x, y, z))
		{
			if(y < 50)
			{
				tempMod = 0;
			}
			else if(y < 25)
			{
				tempMod = -2;
			}
			else if(!world.isDaytime())
			{
				tempMod /= 2;
			}
		}
		else
		{
			if(world.isRaining())
			{tempMod += -1;}
		}
		
		final int SCAN_RADIUS = 4;
		
		int pX;
		int pY;
		int pZ;
		
		ArrayList<Integer> values = new ArrayList<Integer>();
		
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
						int mod = (Integer) e.getValue();
						
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
	
	public static int getBlockTempModifier(Block block)
	{
		if(block instanceof ITemperatureModifier)
		{
			return((ITemperatureModifier)block).getTempMod();
		}
		
		for (Entry e : blockMod.entrySet())
		{
			Block eBlock = (Block) e.getKey();
			int mod = (Integer) e.getValue();
			
			if(Block.isEqualTo(block, eBlock))
			{
				return mod;
			}
		}
		return 0;
	}
	
	public static int getArmorTempModifier(EntityPlayer player, int currentTemp)
	{
		Integer tempMod = 0;
		
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
					tempMod += armor.getTemperatureModifier(currentTemp);
				}
			}
		}

		return (int)(tempMod.floatValue() / player.inventory.armorInventory.length);
	}
}

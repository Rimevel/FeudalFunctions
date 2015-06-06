package se.rimevel.FeudalFunctions.modules.survival.player;

import net.minecraft.world.biome.BiomeGenBase;

public enum EnumBiomeTemperature
{
	HELLISH("Hellish", 10.0F),
	HOT("Hot", 2.0F),
	WARM("Warm", 1.2F),
	NORMAL("Normal", 0.2F),
	COLD("Cold", 0.05F),
	ICY("Icy", 0.0F);
	
	public String temp;
	public float tempValue;
	
	private EnumBiomeTemperature(String temp, float tempValue)
	{
		this.temp = temp;
		this.tempValue = tempValue;
	}
	
	public static EnumBiomeTemperature getTempFromBiome(BiomeGenBase biome)
	{
		EnumBiomeTemperature value = NORMAL;
		
		for (EnumBiomeTemperature t : EnumBiomeTemperature.values())
		{
			if(biome.temperature >= t.tempValue)
			{
				value = t;
				return value;
			}
		}
		
		return value;
	}
}

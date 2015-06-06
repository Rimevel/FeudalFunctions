package se.rimevel.FeudalFunctions.modules.survival.player;

import net.minecraft.world.biome.BiomeGenBase;

public enum EnumBiomeHumidity
{
	DAMP("Damp", 0.9F),
	NORMAL("Normal", 0.3F),
	ARID("Arid", 0.0F);
	
	public String humidity;
	public float humidityValue;
	
	private EnumBiomeHumidity(String humidity, float humidityValue)
	{
		this.humidity = humidity;
		this.humidityValue = humidityValue;
	}
	
	public static EnumBiomeHumidity getHumidityFromBiome(BiomeGenBase biome)
	{
		EnumBiomeHumidity value = NORMAL;
		
		for (EnumBiomeHumidity h : EnumBiomeHumidity.values())
		{
			if(biome.rainfall >= h.humidityValue)
			{
				value = h;
				return value;
			}
		}
		
		return value;
	}
}

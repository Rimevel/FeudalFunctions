package se.rimevel.FeudalFunctions.core.util;

import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public enum UtilArmorMaterials
{
	CLOTH,
	LEATHER,
	COPPER,
	BRONZE,
	IRON,
	GOLD,
	CHAINMAIL,
	STEEL,
	DIAMOND;
	
	private ArmorMaterial material;
	private float tempModHot;
	private float tempModCold;
	
	private UtilArmorMaterials()
	{
		
	}
	
	public ArmorMaterial getMaterial()
	{
		return this.material;
	}
	
	public float getTemperatureModifier(float currentTemp)
	{
		if(currentTemp < 0F)
		{
			return tempModCold;
		}
		else
		{
			return tempModHot;
		}
	}
	
	public static void init()
	{
		CLOTH.material = ArmorMaterial.CLOTH;
		LEATHER.material = EnumHelper.addArmorMaterial("LEATHER", 8,new int[]{1, 3, 2, 1}, 17);
		COPPER.material = EnumHelper.addArmorMaterial("COPPER", 10,new int[]{2, 4, 3, 1}, 12);
		BRONZE.material = EnumHelper.addArmorMaterial("BRONZE", 12,new int[]{2, 4, 3, 1}, 12);
		IRON.material = ArmorMaterial.IRON;
		GOLD.material = ArmorMaterial.GOLD;
		CHAINMAIL.material = ArmorMaterial.CHAIN;
		STEEL.material = EnumHelper.addArmorMaterial("STEEL", 22,new int[]{3, 7, 4, 2}, 10);
		DIAMOND.material = ArmorMaterial.DIAMOND;
	}
	
	public static void setTemperatureModifers()
	{
		CLOTH.tempModCold 		= 0.2F; 	CLOTH.tempModHot 		= -1.2F;
		LEATHER.tempModCold		= 0.5F;		LEATHER.tempModHot 		= -0.5F;
		COPPER.tempModCold 		= -0.2F;	COPPER.tempModHot		= 0.5F;
		BRONZE.tempModCold 		= -0.2F;	BRONZE.tempModHot		= 0.2F;
		IRON.tempModCold 		= -0.3F;	IRON.tempModHot			= 0.3F;
		GOLD.tempModCold 		= -0.3F;	GOLD.tempModHot			= 0.3F;
		CHAINMAIL.tempModCold 	= 0F;		CHAINMAIL.tempModHot	= 0.1F;
		STEEL.tempModCold 		= -0.5F;	STEEL.tempModHot		= 0.5F;
		DIAMOND.tempModCold 	= -1.0F;	DIAMOND.tempModHot		= -1.0F;
	}
}

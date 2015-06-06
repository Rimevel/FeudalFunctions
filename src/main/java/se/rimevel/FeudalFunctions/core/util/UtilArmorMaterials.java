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
	private int tempModHot;
	private int tempModCold;
	
	private UtilArmorMaterials()
	{
		
	}
	
	public ArmorMaterial getMaterial()
	{
		return this.material;
	}
	
	public int getTemperatureModifier(int currentTemp)
	{
		if(currentTemp < 0)
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
		CLOTH.tempModCold 		= 1; 	CLOTH.tempModHot 		= 0;
		LEATHER.tempModCold		= 2;	LEATHER.tempModHot 		= 1;
		COPPER.tempModCold 		= -1;	COPPER.tempModHot		= 2;
		BRONZE.tempModCold 		= -1;	BRONZE.tempModHot		= 2;
		IRON.tempModCold 		= -2;	IRON.tempModHot			= 2;
		GOLD.tempModCold 		= -1;	GOLD.tempModHot			= 3;
		CHAINMAIL.tempModCold 	= 0;	CHAINMAIL.tempModHot	= 1;
		STEEL.tempModCold 		= -3;	STEEL.tempModHot		= 3;
		DIAMOND.tempModCold 	= -2;	DIAMOND.tempModHot		= -1;
	}
}

package se.rimevel.FeudalFunctions.modules.smithing.util;

import java.util.ArrayList;

import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DataHeatableList
{
	public static ArrayList<DataHeatable> list;
	
	public DataHeatableList()
	{
		init();
	}
	
	private static void init()
	{
		if(list == null)
		{
			list = new ArrayList<DataHeatable>();
		}
	}
	
	public static void addItem(String oreDictName, int heatTime, EnumHeatableType type)
	{
		init();
		
		list.add(new DataHeatable(oreDictName, heatTime, type));
	}
	
	public static int getItemHeatTime(ItemStack stack)
	{
		for (DataHeatable data : list)
		{
			if(UtilOreDict.compareItem(stack, data.oreDictName))
			{
				return data.heatTime;
			}
		}
		
		return 0;
	}
	
	public static EnumHeatableType getType(ItemStack stack)
	{
		for (DataHeatable data : list)
		{
			if(UtilOreDict.compareItem(stack, data.oreDictName))
			{
				return data.type;
			}
		}
		
		return null;
	}
}

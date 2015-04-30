package se.rimevel.FeudalFunctions.modules.smithing.util;

public class DataHeatable
{
	public String oreDictName;
	public int heatTime;
	public EnumHeatableType type;
	
	public DataHeatable(String oreDictName, int heatTime, EnumHeatableType type)
	{
		this.oreDictName = oreDictName;
		this.heatTime = heatTime;
		this.type = type;
	}
}

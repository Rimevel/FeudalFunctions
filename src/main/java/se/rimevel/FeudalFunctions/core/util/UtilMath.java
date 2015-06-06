package se.rimevel.FeudalFunctions.core.util;

import java.util.List;

public class UtilMath
{
	public static float randomInRange(int min, int max)
	{
		return (float)Math.random() * (max-min) + min;
	}
	
	public static int scaleFloat(float value, float minValue, float maxValue, int pixelLength)
	{
		value -= minValue;
		if(value < 0F)
		{
			value = 0F;
		}
		return (int)(value / maxValue * pixelLength);
	}
	
	public static float calculateAvarage(List<Integer> values)
	{
		Integer sum = 0;
		if(!values.isEmpty())
		{
			for(int v : values)
			{
				sum += v;
			}
			return sum.floatValue() / values.size();
		}
		return sum;
	}
}

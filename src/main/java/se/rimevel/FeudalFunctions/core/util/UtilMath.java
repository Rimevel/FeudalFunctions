package se.rimevel.FeudalFunctions.core.util;

import java.util.List;

public class UtilMath
{
	public static float randomInRange(int min, int max)
	{
		return (float)Math.random() * (max-min) + min;
	}
	
	public static float randomInRange(float min, float max)
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
	
	public static float calculateAvarage(List<Float> values)
	{
		float sum = 0;
		if(!values.isEmpty())
		{
			for(float v : values)
			{
				sum += v;
			}
			return sum / values.size();
		}
		return sum;
	}
}

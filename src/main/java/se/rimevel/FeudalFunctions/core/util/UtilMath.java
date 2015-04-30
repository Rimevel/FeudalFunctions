package se.rimevel.FeudalFunctions.core.util;

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
}

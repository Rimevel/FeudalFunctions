package se.rimevel.FeudalFunctions.core.util;

import org.apache.logging.log4j.Level;

import se.rimevel.FeudalFunctions.core.ModSettings;
import cpw.mods.fml.common.FMLLog;

public class UtilLog
{
	public static void log(Level logLevel, Object object)
	{
		FMLLog.log(ModSettings.NAME, logLevel, String.valueOf(object));
	}
	
	public static void error(Object object)
	{
		log(Level.ERROR, object);
	}
	
	public static void fatal(Object object)
	{
		log(Level.FATAL, object);
	}
	
	public static void info(Object object)
	{
		log(Level.INFO, object);
	}
	
	public static void off(Object object)
	{
		log(Level.OFF, object);
	}
	
	public static void warn(Object object)
	{
		log(Level.WARN, object);
	}
}

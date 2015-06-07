package se.rimevel.FeudalFunctions.core.util;

import java.lang.reflect.Field;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class UtilReflection
{
	public static <T, E> void setPrivateFinalValue(Class<? super T> classToAccess, T instance, E value, String... fieldNames)
	{
		Field field = ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
		try
		{
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
			field.set(instance, value);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

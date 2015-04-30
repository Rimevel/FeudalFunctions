package se.rimevel.FeudalFunctions.core.util;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class UtilEntity
{
	/**
	 * Get the compass direction the given entity is facing.
	 * @param entity Entity to check.
	 * @return ForgeDirection for given entity.
	 */
	public static ForgeDirection getDirectionEntityIsFacing(EntityLiving entity)
	{
		int dir = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if(dir == 0){return ForgeDirection.SOUTH;}
		if(dir == 1){return ForgeDirection.WEST;}
		if(dir == 2){return ForgeDirection.NORTH;}
		if(dir == 3){return ForgeDirection.EAST;}
		
		return ForgeDirection.UNKNOWN;
	}
	
	public static ForgeDirection getDirectionEntityIsFacing(EntityLivingBase entity)
	{
		int dir = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if(dir == 0){return ForgeDirection.SOUTH;}
		if(dir == 1){return ForgeDirection.WEST;}
		if(dir == 2){return ForgeDirection.NORTH;}
		if(dir == 3){return ForgeDirection.EAST;}
		
		return ForgeDirection.UNKNOWN;
	}
	
	/**
	 * Translate ForgeDirection to metadata direction.
	 * @param dir ForgeDirection to translate.
	 * @return Meta integer for the direction.
	 */
	public static int translateToCompassMeta(ForgeDirection dir)
	{
		if(dir == ForgeDirection.SOUTH){return 0;}
		if(dir == ForgeDirection.WEST){return 1;}
		if(dir == ForgeDirection.NORTH){return 2;}
		if(dir == ForgeDirection.EAST){return 3;}
		
		return 0;
	}
}

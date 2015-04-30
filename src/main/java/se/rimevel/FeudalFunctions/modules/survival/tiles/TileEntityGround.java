package se.rimevel.FeudalFunctions.modules.survival.tiles;

import se.rimevel.FeudalFunctions.core.tiles.TileEntityBase;
import se.rimevel.FeudalFunctions.core.util.UtilMath;

public class TileEntityGround extends TileEntityBase
{
	public float rotation;
	
	public TileEntityGround()
	{
		rotation = UtilMath.randomInRange(0, 360);
	}
	
	@Override
	public boolean canUpdate()
	{
		return false;
	}
}

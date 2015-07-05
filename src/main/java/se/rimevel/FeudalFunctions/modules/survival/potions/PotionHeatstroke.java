package se.rimevel.FeudalFunctions.modules.survival.potions;

import net.minecraft.entity.EntityLivingBase;
import se.rimevel.FeudalFunctions.core.potions.PotionBase;

public class PotionHeatstroke extends PotionBase
{
	public static PotionHeatstroke INSTANCE = null;
	private int statusIconIndex = -1;
	
	public PotionHeatstroke(int id)
	{
		super(id, "potion.heatstroke", true, 77222200, 1.0D, 0,1);
		INSTANCE = this;
	}
	
	@Override
	public boolean isBadEffect()
	{
		return true;
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int par2)
	{
		
	}
}

package se.rimevel.FeudalFunctions.modules.survival.potions;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.potions.PotionBase;
import se.rimevel.FeudalFunctions.core.potions.PotionHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionFrostbite extends PotionBase
{
	public static PotionFrostbite INSTANCE = null;
	private int statusIconIndex = -1;
	
	public PotionFrostbite(int id)
	{
		super(id, "potion.frostbite", true, 16308399, 1.0D, 0, 2);
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
		entity.motionX *= 0.02D;
		entity.motionZ *= 0.02D;
		//entity.setFire(6);
	}
	
	@Override
	public boolean isReady(int par1, int par2)
	{
		return true;
	}
}
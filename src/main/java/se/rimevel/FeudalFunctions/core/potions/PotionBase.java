package se.rimevel.FeudalFunctions.core.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class PotionBase extends Potion
{
	public PotionBase(int id, String potionName, boolean isBad, int liquidColor, double effectiveness, int indexX, int indexY)
	{
		super(id, isBad, liquidColor);
		setPotionName(potionName);
		setIconIndex(indexX, indexY);
		setEffectiveness(effectiveness);
	}
	
	public PotionBase(int id)
	{
		super(id, false, 00000000);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(PotionHandler.getPotionTexture());
		return super.getStatusIconIndex();
	}
	
	@Override
	public boolean isReady(int par1, int par2)
	{
		return par1 % 40 == 0;
	}
}

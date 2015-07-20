package se.rimevel.FeudalFunctions.modules.smithing.items;

import net.minecraft.creativetab.CreativeTabs;
import se.rimevel.FeudalFunctions.core.items.ItemArmorBase;

public class ItemArmorBasic extends ItemArmorBase
{
	public ItemArmorBasic(ArmorMaterial armorMaterial, int type, String textureName)
	{
		super(armorMaterial, type, textureName);
		setCreativeTab(CreativeTabs.tabCombat);
	}
}

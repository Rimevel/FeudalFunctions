package se.rimevel.FeudalFunctions.modules.smithing.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import se.rimevel.FeudalFunctions.core.items.ItemBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOres extends ItemBase
{
	public ItemOres()
	{
		super("iron", "gold", "copper", "tin");
		setCreativeTab(CreativeTabs.tabMaterials);
		setHasSubtypes(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for (int i = 0; i < names.length; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
}
package se.rimevel.FeudalFunctions.modules.smithing.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import se.rimevel.FeudalFunctions.core.items.ItemBase;
import se.rimevel.FeudalFunctions.modules.smithing.util.EnumHeatableType;

public class ItemParts extends ItemBase
{
	public ItemParts()
	{
		super("axe_head", "pickaxe_head", "hoe_head", "shovel_head", "sword_blade", "hammer_head");
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
	
	public ItemStack getPart(EnumHeatableType type)
	{
		switch (type)
		{
			case AXE_HEAD: return new ItemStack(this, 1, 0);
			case PICKAXE_HEAD: return new ItemStack(this, 1, 0);
			case HOE_HEAD: return new ItemStack(this, 1, 0);
			case SHOVEL_HEAD: return new ItemStack(this, 1, 0);
			case SWORD_BLADE: return new ItemStack(this, 1, 0);
			case HAMMER_HEAD: return new ItemStack(this, 1, 0);
			default: return new ItemStack(this, 1, 0);
		}
	}
}

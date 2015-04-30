package se.rimevel.FeudalFunctions.modules.smithing.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import se.rimevel.FeudalFunctions.core.items.ItemBase;

public class ItemIngot extends ItemBase
{
	public ItemIngot()
	{
		super("copper", "tin", "bronze", "steel");
		setCreativeTab(CreativeTabs.tabMaterials);
		setHasSubtypes(true);
		
		OreDictionary.registerOre("ingotCopper", new ItemStack(this, 1, 0));
		OreDictionary.registerOre("ingotTin", new ItemStack(this, 1, 1));
		OreDictionary.registerOre("ingotBronze", new ItemStack(this, 1, 2));
		OreDictionary.registerOre("ingotSteel", new ItemStack(this, 1, 3));
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
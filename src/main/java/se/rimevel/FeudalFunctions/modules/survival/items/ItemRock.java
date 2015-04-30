package se.rimevel.FeudalFunctions.modules.survival.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import se.rimevel.FeudalFunctions.core.items.ItemBase;

public class ItemRock extends ItemBase
{
	public ItemRock()
	{
		setCreativeTab(CreativeTabs.tabMaterials);
		
		OreDictionary.registerOre("smallStone", new ItemStack(this, 1, 0));
	}
}

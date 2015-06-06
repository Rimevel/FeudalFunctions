package se.rimevel.FeudalFunctions.modules.survival.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import se.rimevel.FeudalFunctions.core.items.ItemBase;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerDataStats;

public class ItemRock extends ItemBase
{
	public ItemRock()
	{
		setCreativeTab(CreativeTabs.tabMaterials);
		
		OreDictionary.registerOre("smallStone", new ItemStack(this, 1, 0));
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		PlayerDataStats stats = PlayerDataStats.get(player);
		
		if(world.isRemote)
		{
			//UtilLog.info("CLIENT " + stats.getTemperature());
		}
		else
		{
			if(player.isSneaking())
			{
				stats.adjustBodyTemp(-5);
			}
			else
			{
				stats.adjustBodyTemp(5);
			}
			UtilLog.info("SERVER " + stats.getTemperature());
		}
		
		return stack;
	}
}

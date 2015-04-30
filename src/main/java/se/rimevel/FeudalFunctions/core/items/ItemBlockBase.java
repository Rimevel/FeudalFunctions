package se.rimevel.FeudalFunctions.core.items;

import se.rimevel.FeudalFunctions.core.ModSettings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockBase extends ItemBlockWithMetadata
{
	public String[] subnames;
	
	public ItemBlockBase(Block block, String ... subnames)
	{
		super(block, block);
		setSubnames(subnames);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("tile.%s%s", ModSettings.ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()) + "." + subnames[stack.getItemDamage()]);
	}
	
	public void setSubnames(String ... names)
	{
		subnames = new String[names.length];
		
		for (int i = 0; i < names.length; i++)
		{
			subnames[i] = names[i];
		}
	}
	
	private String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1);
	}
}
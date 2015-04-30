package se.rimevel.FeudalFunctions.modules.smithing.items.tools;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import se.rimevel.FeudalFunctions.core.items.ItemToolBase;

public class ItemToolHammer extends ItemToolBase
{
	public static Set<Block> effectiveBlocks = new HashSet();
	public static Set<Material> effectiveMaterials = new HashSet();
	
	public ItemToolHammer(float damage, ToolMaterial toolMaterial, String repairMaterial)
	{
		super(damage, toolMaterial, repairMaterial);
		setMaxDamage(toolMaterial.getMaxUses());
		setCreativeTab(CreativeTabs.tabTools);
	}

	@Override
	public Set<Block> getEffectiveBlocks(ItemStack stack)
	{
		return effectiveBlocks;
	}

	@Override
	public Set<Material> getEffectiveMaterials(ItemStack stack)
	{
		return effectiveMaterials;
	}
}

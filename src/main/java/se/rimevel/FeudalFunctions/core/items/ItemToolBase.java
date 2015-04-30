package se.rimevel.FeudalFunctions.core.items;

import java.util.Set;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemToolBase extends ItemTool
{
	/**
	 * Should return a set of blocks that the tool can break.
	 * @param stack 
	 * @return
	 */
	public abstract Set<Block> getEffectiveBlocks(ItemStack stack);
	
	public abstract Set<Material> getEffectiveMaterials(ItemStack stack);
	
	/**
	 * oreDict name for the material used to repair this tool.
	 */
	public String repairMaterial = "";
	
	public String[] names;
	
	@SideOnly(Side.CLIENT)
	public IIcon[] textures;
	private boolean noTexture = false;
	
	public ItemToolBase(float damage, Item.ToolMaterial toolMaterial, String repairMaterial)
	{
		super(damage, toolMaterial, null);
		this.setRepairMaterial(repairMaterial);
	}
	
	public ItemToolBase(float damage, Item.ToolMaterial toolMaterial, String repairMaterial, String ... names)
	{
		super(damage, toolMaterial, null);
		this.setRepairMaterial(repairMaterial);
		this.setnames(names);
	}
	
	/**
	 * Get the tools efficiency on the proper materials.
	 * @return Float number representing efficiency.
	 */
	
	public float getEfficiency(ItemStack stack)
	{
		return this.efficiencyOnProperMaterial;
	}
	
	/**
	 * Set the repair material of the tool.
	 * @param material oreDict name of the material that the tool can be repaired with.
	 */
	public void setRepairMaterial(String material)
	{
		this.repairMaterial = material;
	}
	
	/**
	 * Is this tool repairable with the given material?
	 * @return True if the given material is of the right oreDict type.
	 */
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material)
	{
		return UtilOreDict.compareItem(material, this.repairMaterial);
	}
	
	@Override
	public float func_150893_a(ItemStack stack, Block block)
	{
		return getEffectiveMaterials(stack).contains(block.getMaterial()) || getEffectiveBlocks(stack).contains(block) ? getEfficiency(stack) : block == null ? 1.0F : 1.0F;
	}
	
	//ItemBase Methods
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("item.%s%s", ModSettings.TEXTURE_LOCATION, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s", ModSettings.TEXTURE_LOCATION, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	/**
	 * A custom registerIcons sensitive to the amount of textures and with the option to use a blank dummy texture.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		if(noTexture == true)
		{
			itemIcon = register.registerIcon("item." + ModSettings.TEXTURE_LOCATION + "none");
			return;
		}
		
		itemIcon = register.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()));
		return;
	}
	
	/**
	 * Returns an IIcon from the given index in the items texture array.
	 * @param index Integer representing the index of the IIcon to get.
	 * @return
	 */
	public IIcon getTexture(int index)
	{
		if(!(index > textures.length))
		{
			return textures[index];
		}
		return null;
	}
	
	/**
	 * Set one or more texture names.
	 * @param names
	 */
	public void setnames(String ... names)
	{
		if(this.names == null)
		{
			this.names = new String[names.length];
			
			for (int i = 0; i < names.length; i++)
			{
				this.names[i] = names[i];
			}
		}
	}
	
	/**
	 * Do this tool have a texture or should it use a blank dummy?
	 * @param state
	 */
	public void hasTexture(boolean state)
	{
		noTexture = (state != true) ? true : false ;
	}
	
	/**
	 * Get a unwrapped version of the unlocalized name.
	 * @param unlocalizedName
	 * @return
	 */
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
}

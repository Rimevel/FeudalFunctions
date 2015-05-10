package se.rimevel.FeudalFunctions.modules.smithing.items.weapons;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilOreDict;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.IIcon;

public class WeaponSword extends ItemSword
{
	public String repairMaterial = "";
	public String[] names;
	
	@SideOnly(Side.CLIENT)
	public IIcon[] textures;
	private boolean noTexture = false;
	
	public WeaponSword(float damage, ToolMaterial toolMaterial, String repairMaterial)
	{
		super(toolMaterial);
		setMaxDamage(toolMaterial.getMaxUses());
		setCreativeTab(CreativeTabs.tabCombat);
	}

	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack material)
	{
		return UtilOreDict.compareItem(material, this.repairMaterial);
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

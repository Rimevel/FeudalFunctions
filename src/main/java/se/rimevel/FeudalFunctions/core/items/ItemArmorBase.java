package se.rimevel.FeudalFunctions.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemArmorBase extends ItemArmor
{
	public String textureName;
	private IIcon icon;
	
	public ItemArmorBase(ArmorMaterial armorMaterial, int type, String textureName)
	{
		super(armorMaterial, 0, type);
		setTexture(textureName);
	}
	
	/**
	 * Get the full unlocalized name of the item.
	 */
	@Override
	public String getUnlocalizedName()
	{
		return String.format("item.%s%s", ModSettings.TEXTURE_LOCATION, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	/**
	 * Get the full unlocalized name of the itemstack.
	 */
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return String.format("item.%s%s", ModSettings.TEXTURE_LOCATION, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	protected String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}
	
	/**
	 * Set the unlocalized subnames for the block.
	 * @param names
	 */
	public void setTexture(String textureName)
	{
		this.textureName = textureName;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		return ModSettings.TEXTURE_LOCATION + "textures/models/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		icon = register.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()));
		return;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		return icon;
	}
}

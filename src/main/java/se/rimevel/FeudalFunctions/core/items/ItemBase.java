package se.rimevel.FeudalFunctions.core.items;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBase extends Item
{
	public String[] names;
	
	@SideOnly(Side.CLIENT)
	private IIcon[] textures;
	private boolean noTexture = false;
	
	public ItemBase()
	{
		super();
	}
	
	public ItemBase(String ... names)
	{
		super();
		setTextureNames(names);
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
		if(hasSubtypes)
		{
			return String.format("item.%s%s", ModSettings.TEXTURE_LOCATION, getUnwrappedUnlocalizedName(super.getUnlocalizedName()) + "." + names[stack.getItemDamage()]);
		}
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
	public void setTextureNames(String ... names)
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register)
	{
		if(!noTexture)
		{
			if(names != null)
			{
				textures = new IIcon[names.length];
			}
			else
			{
				textures = new IIcon[1];
			}
			
			if(noTexture == true)
			{
				textures[0] = register.registerIcon("item." + ModSettings.TEXTURE_LOCATION + "none");
				return;
			}
			
			if(names == null)
			{
				textures[0] = register.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()));
				return;
			}
			
			for (int i = 0; i < names.length; i++)
			{
				textures[i] = register.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()) + "_" + names[i]);
			}
		}
		else
		{
			
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage)
	{
		if(damage < textures.length)
		{
			return textures[damage];
		}
		return textures[0];
	}
	
	public void setNoTexture(boolean state)
	{
		noTexture = state ;
	}
}

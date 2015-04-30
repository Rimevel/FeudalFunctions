package se.rimevel.FeudalFunctions.core.blocks;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBase extends Block
{
	private String[] names;
	private IIcon[] textures;
	
	private boolean custom3d = false;
	
	/**
	 * BlockBase is a custom version of Block adding some extra features to make modding less tedious.
	 * @param material Material that the block use.
	 * @param names Subnames for the block. Also name of the texture. block_one, block_two etc.
	 */
	public BlockBase(Material material, String ... names)
	{
		super(material);
		setTextureNames(names);
	}
	
	public BlockBase(String ... names)
	{
		super(Material.rock);
		setTextureNames(names);
	}
	
	public BlockBase(Material material)
	{
		super(material);
	}
	
	public BlockBase()
	{
		super(Material.rock);
	}
	
	/**
	 * Get the full unlocalized name of the block.
	 */
	public String getUnlocalizedName()
	{
		return String.format("tile.%s%s", ModSettings.TEXTURE_LOCATION, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	/**
	 * Get only the actual name part of the unlocalized name and nothing else.
	 * @param unlocalizedName
	 * @return
	 */
	private String getUnwrappedUnlocalizedName(String unlocalizedName)
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
	
	public void setCustom3d(boolean value)
	{
		this.custom3d = value;
	}
	
	public boolean isCustom3d()
	{
		return custom3d;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		if(!isCustom3d())
		{
			if(names != null)
			{
				textures = new IIcon[names.length];
			}
			else
			{
				textures = new IIcon[1];
			}
			
			if(textures.length == 1)
			{
				textures[0] = register.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()));
				return;
			}
			
			for (int i = 0; i < textures.length; i++)
			{
				textures[i] = register.registerIcon(getUnwrappedUnlocalizedName(getUnlocalizedName()) + "_" + names[i]);
			}
		}
		else
		{
			textures = new IIcon[1];
			textures[0] = register.registerIcon(ModSettings.TEXTURE_LOCATION + "none");
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta < textures.length)
		{
			return textures[meta];
		}
		return textures[0];
	}
	
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
    public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
    {
		//If there are no textures at all then hide hit particles to prevent graphical bugs. Important with custom models.
        return isCustom3d();
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return isCustom3d();
	}
}

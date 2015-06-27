package se.rimevel.FeudalFunctions.modules.smithing.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import se.rimevel.FeudalFunctions.core.ui.GuiHandler;
import se.rimevel.FeudalFunctions.core.util.UtilEntity;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityBloomery;
import se.rimevel.FeudalFunctions.modules.survival.interfaces.IFireTool;
import se.rimevel.FeudalFunctions.modules.survival.interfaces.ITemperatureModifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloomery extends BlockContainerBase implements ITemperatureModifier
{
	static int instanceNumber;
	
	public BlockBloomery()
	{
		super(Material.clay);
		if(instanceNumber == 1)
		{
			this.setLightLevel(1F);
		}
		else
		{
			setCreativeTab(CreativeTabs.tabDecorations);
		}
		setStepSound(soundTypeStone);
		setLightOpacity(1);
		setTickRandomly(true);
		setResistance(0.4F);
		setHardness(1F);
		
		instanceNumber++;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int something)
	{
		return new TileEntityBloomery();
	}
	
	@Override
	public int getRenderType()
	{
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntityBloomery tile = (TileEntityBloomery)world.getTileEntity(x, y, z);
		ItemStack held = UtilPlayer.getHeldItem(player);
		
		if(world.isRemote){return true;}
		
		if(held != null)
		{
			Item heldItem = held.getItem();
			
			if(heldItem == Items.flint_and_steel)
			{
				tile.setActive(true);
				return true;
			}
				
			if(heldItem instanceof IFireTool)
			{
				if(UtilMath.randomInRange(0, 100) > ((IFireTool)heldItem).getFireProbability())
				{
					tile.setActive(true);
					return true;
				}
			}
		}
		
		GuiHandler.openGui(player, 2, world, x, y, z);
		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
		ForgeDirection dir = UtilEntity.getDirectionEntityIsFacing(entity);
		world.setBlockMetadataWithNotify(x, y, z, UtilEntity.translateToCompassMeta(dir), 2);
	}
	
	public static void updateBlockMeta(boolean active, World world, int x, int y, int z)
	{
	    isChangingState = true;
		
		TileEntity tileentity = world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		if(active)
		{
			world.setBlock(x, y, z, MSmithing.blocks.bloomery_active.getInstance());
			world.setBlockMetadataWithNotify(x, y, z, meta + 4, 2);
		}
		else
		{
			world.setBlock(x, y, z, MSmithing.blocks.bloomery.getInstance());
			world.setBlockMetadataWithNotify(x, y, z, meta - 4, 2);
		}
		
		if(tileentity != null)
		{
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
		
		isChangingState = false;
	}
	
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta)
	{
		TileEntityContainerBase tile = (TileEntityContainerBase)world.getTileEntity(x, y, z);
		if(tile != null)
		{
			dropStacks(world, x, y, z, meta, tile.content, isChangingState);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if(world.getBlockMetadata(x, y, z) > 3)
		{
			world.spawnParticle("largesmoke", (double)(x + 0.5F + (random.nextFloat() / 8)), (double)(y + 0.8F + (random.nextFloat() / 8)), (double)(z + 0.5F + (random.nextFloat() / 8)), 0.0D, 0.1D, 0.0D);
		    world.spawnParticle("flame", (double)(x + 0.2F + (random.nextFloat() / 2)), (double)(y + 0.7F + (random.nextFloat() / 4)), (double)(z + 0.2F + (random.nextFloat() / 2)), 0.0D, 0.0D, 0.0D);
		    if (random.nextInt(8) == 0)
	        {
	            world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
	        }
		}
	}
	
	@Override
	public float getTempMod()
	{
		if(this.lightValue > 0)
		{
			return 1.7F;
		}
		return 0;
	}
}

package se.rimevel.FeudalFunctions.modules.smithing.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import se.rimevel.FeudalFunctions.core.blocks.BlockContainerBase;
import se.rimevel.FeudalFunctions.core.ui.GuiHandler;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.core.util.UtilPlayer;
import se.rimevel.FeudalFunctions.modules.smithing.MSmithing;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityForge;
import se.rimevel.FeudalFunctions.modules.survival.util.IFireTool;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockForge extends BlockContainerBase
{
	private static int instanceNumber;
	
	public BlockForge()
	{
		super(Material.ground);
		setStepSound(soundTypeGravel);
		setResistance(0.4F);
		setHardness(1F);
		setLightOpacity(1);
		
		if(this.instanceNumber == 1)
		{
			setLightLevel(1F);
		}
		else
		{
			setCreativeTab(CreativeTabs.tabDecorations);
		}
		
		instanceNumber++;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_)
	{
		return new TileEntityForge();
	}
	
	public static void updateBlockMeta(boolean active, World world, int x, int y, int z)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(active)
		{
			world.setBlock(x, y, z, MSmithing.blocks.forge_active.getInstance());
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}
		else
		{
			world.setBlock(x, y, z, MSmithing.blocks.forge.getInstance());
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}
		
		if(tileentity != null)
		{
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TileEntityForge tile = (TileEntityForge)world.getTileEntity(x, y, z);
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
		
		GuiHandler.openGui(player, 0, world, x, y, z);
		
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if(world.getBlockMetadata(x, y, z) == 1)
		{
			world.spawnParticle("largesmoke", (double)(x + 0.2F + (random.nextFloat() / 2)), (double)(y + 0.95F + (random.nextFloat() / 4)), (double)(z + 0.2F + (random.nextFloat() / 2)), 0.0D, 0.1D, 0.0D);
			world.spawnParticle("flame", (double)(x + 0.2F + (random.nextFloat() / 2)), (double)(y + 0.95F + (random.nextFloat() / 4)), (double)(z + 0.2F + (random.nextFloat() / 2)), 0.0D, 0.0D, 0.0D);
			if (random.nextInt(8) == 0)
			{
				world.playSound((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "fire.fire", 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
			}
		}
	}
}
package se.rimevel.FeudalFunctions.modules.survival.renderers;

import org.lwjgl.opengl.GL11;

import se.rimevel.FeudalFunctions.core.rendering.RenderItem3d;
import se.rimevel.FeudalFunctions.modules.survival.MSurvival;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityGround;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderGround extends TileEntitySpecialRenderer
{
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f)
	{
		render((TileEntityGround)tileentity, x, y, z, f);
	}
	
	public void render(TileEntityGround tile, double x, double y, double z, float f)
	{
		ItemStack stone = new ItemStack(MSurvival.items.rock.getInstance(), 1, 0);
		ItemStack stick = new ItemStack(Items.stick);
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.567F, (float)z + 0.5F);
		GL11.glScalef(1F, 1F, 1F);
		GL11.glRotatef(90F, -1F, 0F, 0F);
		
		int meta = tile.getBlockMetadata();
		
		GL11.glRotatef(tile.rotation, 0F, 0F, 1F);
		
		if(meta == 0)
		{
			RenderItem3d.render3d(stick);
		}
		if(meta > 0)
		{
			GL11.glScalef(0.5F, 0.5F, 1F);
			RenderItem3d.render3d(stone);
		}
		
		GL11.glPopMatrix();
	}
}

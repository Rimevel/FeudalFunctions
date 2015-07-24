package se.rimevel.FeudalFunctions.core.util;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class UtilRender
{
	public static void renderFullCube(IBlockAccess blockAccess, int x, int y, int z, Block block, IIcon texture, RenderBlocks renderer)
	{
		renderer.renderFaceXPos(block, x, y, z, texture);
		renderer.renderFaceXNeg(block, x, y, z, texture);
		renderer.renderFaceYPos(block, x, y, z, texture);
		renderer.renderFaceYNeg(block, x, y, z, texture);
		renderer.renderFaceZPos(block, x, y, z, texture);
		renderer.renderFaceZNeg(block, x, y, z, texture);
	}
	
	public static void setBlockLight(IBlockAccess blockAccess, int x, int y, int z, Block block)
	{
		Tessellator t = Tessellator.instance;
		
		int ll = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
		t.setBrightness(ll);
		float f = 1.0F;
		int colorM = block.colorMultiplier(blockAccess, x, y, z);
		float f1 = (colorM >> 16 & 0xFF) / 255.0F;
		float f2 = (colorM >> 8 & 0xFF) / 255.0F;
		float f3 = (colorM & 0xFF) / 255.0F;
		
		t.setColorOpaque_F(f * f1, f * f2, f * f3);
	}
}

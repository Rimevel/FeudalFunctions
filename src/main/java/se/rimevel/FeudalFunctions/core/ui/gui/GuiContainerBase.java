package se.rimevel.FeudalFunctions.core.ui.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiContainerBase extends GuiContainer
{
	public GuiContainerBase(Container container, int xSize, int ySize)
	{
		super(container);
		this.xSize = xSize;
		this.ySize = ySize;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		
	}
	
	/**
	 * Quick shortcut function to draw the main gui background.
	 */
	public void drawBase(ResourceLocation texture)
	{
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}

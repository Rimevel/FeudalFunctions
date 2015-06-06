package se.rimevel.FeudalFunctions.modules.survival.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerDataStats;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HudStatus extends Gui
{
	private Minecraft mc;
	private int width, height, xStart, yStart, xSize, ySize;
	
	public HudStatus(Minecraft mc)
	{
		super();
		this.mc = mc;
	}
	
	private static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/gui/hud_status.png");
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent event)
	{
		int mouseX = event.mouseX;
		int mouseY = event.mouseY;
		
		if(event.type != ElementType.HOTBAR){return;}
		{
			width = event.resolution.getScaledWidth();
			height = event.resolution.getScaledHeight();
			
			xSize = 67;
			ySize = 13;
			
			xStart = (width - xSize) / 2;
			yStart = height - 70;
			
			GL11.glEnable(GL11.GL_BLEND);
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawOverlay();
			drawBar(PlayerDataStats.get(this.mc.thePlayer).getTemperature());
			drawPins();
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	private void drawOverlay()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(xStart, yStart, 0, 6, xSize, ySize);
	}
	
	private void drawBar(int value)
	{
		int result = UtilMath.scaleFloat((float)value, 0, 100, 62);
				
		setTemperatureColor(value);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(xStart + 1, yStart + 2, 1, 19, result, 8);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void drawPins()
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(xStart + 13, yStart + 2, 13, 1, 51, 4);
	}
	
	private void setTemperatureColor(int value)
	{
		if(value >= 80){GL11.glColor4f(1.0F, 0.2F, 0.2F, 1.0F); return;}
		if(value >= 60){GL11.glColor4f(1.0F, 0.4F, 0.2F, 1.0F); return;}
		if(value >= 40){GL11.glColor4f(1.0F, 0.5F, 0.2F, 1.0F); return;}
		if(value >= 0){GL11.glColor4f(0.2F, 0.2F, 0.7F, 1.0F); return;}
	}
}

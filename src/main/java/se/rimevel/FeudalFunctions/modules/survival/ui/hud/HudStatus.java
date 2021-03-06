package se.rimevel.FeudalFunctions.modules.survival.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.opengl.GL11;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.modules.survival.player.PlayerDataStats;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HudStatus extends Gui
{
	private Minecraft mc;
	private int width, height, xStart, yStart, middle;
	private int horseOffset;
	private int horseHealth;
	
	public HudStatus(Minecraft mc)
	{
		super();
		this.mc = mc;
	}
	
	private static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/gui/hud_status.png");
	
	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent.Pre event)
	{
		int mouseX = event.mouseX;
		int mouseY = event.mouseY;
		
		width = event.resolution.getScaledWidth();
		height = event.resolution.getScaledHeight();
		
		middle = (width - 80) / 2;
		
		xStart = (middle) + 53;
		yStart = height - 53;
		
		Entity mount = this.mc.thePlayer.ridingEntity;
		
		if(mount != null && mount instanceof EntityHorse)
		{
			horseHealth = (int)((EntityHorse)mount).getMaxHealth();
			horseOffset = 1;
			
				 if(horseHealth > 20){horseOffset = 2;}
			else if(horseHealth > 30){horseOffset = 3;}
		}
		
		if(event.type == ElementType.HOTBAR && !this.mc.thePlayer.capabilities.isCreativeMode)
		{	
			float temp = PlayerDataStats.get(this.mc.thePlayer).getTemperature();
			
			if(temp > 49F && temp < 51F){return;}
			
			GL11.glEnable(GL11.GL_BLEND);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			
			if(this.mc.thePlayer.isRidingHorse()){yStart -= 10 * horseOffset;}
			
			drawOverlay(event.type);
			drawTemperatureBar(temp);
			
			if(this.mc.thePlayer.isRidingHorse()){yStart += 10 * horseOffset;}
			
			GL11.glDisable(GL11.GL_BLEND);
		}
		
		if(event.type == ElementType.FOOD)
		{
			event.setCanceled(true);
			
			GL11.glEnable(GL11.GL_BLEND);
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawOverlay(event.type);
			drawFoodBar(this.mc.thePlayer.getFoodStats().getFoodLevel());
			drawHydrationBar(PlayerDataStats.get(this.mc.thePlayer).getHydration());
			
			GL11.glDisable(GL11.GL_BLEND);
		}
		
		if(event.type == ElementType.AIR)
		{
			event.setCanceled(true);
			
			int air = this.mc.thePlayer.getAir();
			if(air < 270)
			{
				GL11.glEnable(GL11.GL_BLEND);
				
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				drawOverlay(event.type);
				drawAirBar(air);
				
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
	}
	
	@SubscribeEvent
	public void onRenderPost(RenderGameOverlayEvent.Post event)
	{
		if(event.type == ElementType.HEALTHMOUNT)
		{
			GL11.glEnable(GL11.GL_BLEND);
			
			if(this.mc.thePlayer.isRidingHorse()){yStart -= 10 * horseOffset;}
			
			Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
			drawOverlay(event.type);
			drawFoodBar(this.mc.thePlayer.getFoodStats().getFoodLevel());
			drawHydrationBar(PlayerDataStats.get(this.mc.thePlayer).getHydration());
			
			if(this.mc.thePlayer.isRidingHorse()){yStart += 10 * horseOffset;}
			
			GL11.glDisable(GL11.GL_BLEND);
		}
	}
	
	private void drawOverlay(ElementType type)
	{
		if(type == ElementType.FOOD)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			//Food and Hydration
			drawTexturedModalRect(xStart, yStart + 14, 0, 32, 80, 9);
		}
		if(type == ElementType.HOTBAR)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			//Temperature
			drawTexturedModalRect(xStart, yStart + 1, 0, 6, 80, 12);
		}
		if(type == ElementType.AIR)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			//Air
			drawTexturedModalRect(middle, yStart - 20, 0, 50, 68, 9);
		}
		if(type == ElementType.HEALTHMOUNT)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			//Food and Hydration
			drawTexturedModalRect(xStart, yStart + 14, 0, 32, 80, 9);
		}
	}
	
	private void drawTemperatureBar(float value)
	{
		int result = UtilMath.scaleFloat((float)value, 0, 100, 64);
				
		setTemperatureColor(value);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(xStart + 2, yStart + 3, 2, 19, 10, 8);
		drawTexturedModalRect(xStart + 12, yStart + 6, 11, 22, result, 2);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(xStart + 13, yStart + 3, 13, 0, 61, 6);
	}
	
	private void drawFoodBar(int value)
	{
		int result = UtilMath.scaleFloat(value, 0, 20, 27);
		
		GL11.glColor4f(1.0F, 0.2F, 0.2F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(xStart + 12, yStart + 17, 12, 41, result, 10);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void drawHydrationBar(float value)
	{
		int result = UtilMath.scaleFloat(value, 0, 100, 27);
		
		GL11.glColor4f(0.2F, 0.2F, 0.7F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(xStart + 41 + (27 - result), yStart + 17, 12, 41, result, 10);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void drawAirBar(float value)
	{
		int result = UtilMath.scaleFloat(value, 0, 260, 56);
		
		GL11.glDisable(GL11.GL_LIGHTING);
		drawTexturedModalRect(middle + 11, yStart - 18, 11, 59, result, 5);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	private void setTemperatureColor(float value)
	{
		if(value >= 80F){GL11.glColor4f(1.0F, 0.2F, 0.2F, 1.0F); return;}
		if(value >= 60F){GL11.glColor4f(1.0F, 0.4F, 0.2F, 1.0F); return;}
		if(value >= 40F){GL11.glColor4f(1.0F, 0.5F, 0.2F, 1.0F); return;}
		if(value >= 0F){GL11.glColor4f(0.2F, 0.2F, 0.7F, 1.0F); return;}
	}
}

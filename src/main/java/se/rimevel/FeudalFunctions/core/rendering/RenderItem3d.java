package se.rimevel.FeudalFunctions.core.rendering;

import javax.swing.Icon;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderItem3d extends Render
{
	private ItemStack itemToRender;
	private static final TextureManager manager = Minecraft.getMinecraft().getTextureManager();
	
	public RenderItem3d(Item item)
	{
		if(item == null){return;}
		itemToRender = new ItemStack(item);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTickTime)
	{
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glScalef(-0.5F, -0.5F, 0.5F);
		
		render3d(itemToRender);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
	
	public static void render3d(ItemStack stack)
	{
		if(manager == null) return;
		
		Item item = stack.getItem();
		
		GL11.glPushMatrix();
		
		Tessellator tessellator = Tessellator.instance;
		
		//GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		int passes = item.getRenderPasses(stack.getItemDamage());
		for (int pass = 0; pass < passes; pass++) {
			manager.bindTexture(((stack.getItemSpriteNumber() == 0) ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture));
			IIcon icon = item.getIcon(stack, pass);
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
		
		//GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		GL11.glPopMatrix();
	}
	
	public static void render3d(ItemStack stack, boolean colorAllowed)
	{
		if(manager == null) return;
		
		Item item = stack.getItem();
		
		GL11.glPushMatrix();
		
		Tessellator tessellator = Tessellator.instance;
		
		//GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		int passes = item.getRenderPasses(stack.getItemDamage());
		for (int pass = 0; pass < passes; pass++) {
			manager.bindTexture(((stack.getItemSpriteNumber() == 0) ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture));
			IIcon icon = item.getIcon(stack, pass);
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			if(colorAllowed != true)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
			ItemRenderer.renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
			if(colorAllowed != true)
			{
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
		
		//GL11.glDisable(GL12.GL_RESCALE_NORMAL);

		GL11.glPopMatrix();
	}
}
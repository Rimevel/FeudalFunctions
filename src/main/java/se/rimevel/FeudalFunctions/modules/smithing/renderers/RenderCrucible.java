package se.rimevel.FeudalFunctions.modules.smithing.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.RenderingRegistry;
import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelCrucible;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityCrucible;
import se.rimevel.FeudalFunctions.modules.survival.renderers.RenderCampfire;

public class RenderCrucible extends TileEntitySpecialRenderer implements IItemRenderer
{
	private ModelCrucible model;
	
	public RenderCrucible(ModelCrucible model)
	{
		this.model = model;
	}
	
	public static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/models/crucible.png");
	public static final ResourceLocation textureHot = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/models/crucible_hot.png");
	
	public void render(TileEntityCrucible tile, double x, double y, double z, float f)
	{
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.12F, (float)z + 0.5F);
		GL11.glScalef(-1F, -1F, 1F);
		
		if(tile.getBlockMetadata() > 0)
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(textureHot);
		}
		else
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		}
		model.render(0.0625F);
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTickTime)
	{
		render((TileEntityCrucible)entity, x, y, z, partialTickTime);
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type != type.FIRST_PERSON_MAP;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		
		GL11.glScalef(-1F, -1F, 1F);
		
		switch(type)
		{
			case INVENTORY:
				GL11.glTranslatef(0, 0.25F, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCrucible.texture);
				model.render(0.0625F);
				break;
			case EQUIPPED:
				GL11.glTranslatef(-0.4F, -0.4F, 0.5F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCrucible.texture);
				model.render(0.0625F);
				break;
			case EQUIPPED_FIRST_PERSON:
				GL11.glTranslatef(0F, -0.5F, 0.7F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCrucible.texture);
				model.render(0.0625F);
				break;
			default:
				GL11.glTranslatef(0, 0, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCrucible.texture);
				model.render(0.0625F);
				break;
		}
		
		GL11.glPopMatrix();
	}
}
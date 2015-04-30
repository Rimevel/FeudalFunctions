package se.rimevel.FeudalFunctions.modules.survival.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.util.UtilRendering;
import se.rimevel.FeudalFunctions.core.util.UtilRendering.RenderVector;
import se.rimevel.FeudalFunctions.modules.survival.renderers.models.ModelCampfire;
import se.rimevel.FeudalFunctions.modules.survival.tiles.TileEntityCampfire;

public class RenderCampfire extends TileEntitySpecialRenderer implements IItemRenderer
{
	private ModelCampfire model;
	
	public RenderCampfire(ModelCampfire model)
	{
		this.model = model;
	}

	public static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/models/campfire.png");
	
	public void render(TileEntityCampfire tile, double x, double y, double z, float f)
	{
		GL11.glPushMatrix();
		
		RenderVector rVec = new RenderVector((float)x, (float)y, (float)z);
		GL11.glTranslatef(rVec.x + 0.5F, rVec.y + 0.001F, rVec.z + 0.5F);
		
		GL11.glScalef(-1F, -1F, 1F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		model.render(0.0625F, true);
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_)
	{
		render((TileEntityCampfire)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
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
				GL11.glTranslatef(0, 0.16F, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCampfire.texture);
				model.render(0.08F, false);
				break;
			case EQUIPPED:
				GL11.glTranslatef(-0.8F, -0.2F, 0.7F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCampfire.texture);
				model.render(0.0625F, false);
				break;
			case EQUIPPED_FIRST_PERSON:
				GL11.glTranslatef(0F, -0.7F, 0.7F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCampfire.texture);
				model.render(0.0625F, false);
				break;
			default:
				GL11.glTranslatef(0, 0, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderCampfire.texture);
				model.render(0.0625F, false);
				break;
		}
		
		GL11.glPopMatrix();
	}
}

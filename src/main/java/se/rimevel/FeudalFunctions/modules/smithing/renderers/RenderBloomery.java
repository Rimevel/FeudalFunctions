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

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelBloomery;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityBloomery;

public class RenderBloomery extends TileEntitySpecialRenderer implements IItemRenderer
{
	private ModelBloomery model;
	
	public RenderBloomery(ModelBloomery model)
	{
		this.model = model;
	}
	
	public static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/models/bloomery.png");
	public static final ResourceLocation textureBurning = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/models/bloomery_burning.png");
	public static final ResourceLocation textureEmpty = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/models/bloomery_empty.png");
	
	public void render(TileEntityBloomery tile, double x, double y, double z, float f)
	{
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
		
		float dir;
		int meta = tile.getBlockMetadata() % 4;
		
		if(meta == 0){dir = 0F;}
		else if(meta == 1){dir = -90F;}
		else if(meta == 2){dir = 180F;}
		else if(meta == 3){dir = 90F;}
		else{dir = 0F;}
		
		GL11.glRotatef(dir, 0F, 1F, 0F);
		GL11.glScalef(-1F, -1F, 1F);
		
		if(tile.getHasFuel())
		{
			if(tile.getBlockMetadata() > 3)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(textureBurning);
			}
			else
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(texture);
			}
		}
		else
		{
			Minecraft.getMinecraft().renderEngine.bindTexture(textureEmpty);
		}
		
		model.render(0.0625F);
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float f)
	{
		render((TileEntityBloomery)entity, x, y, z, f);
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
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
		
		GL11.glScalef(-1, -1, 1);
		
		switch(type)
		{
			case INVENTORY:
				GL11.glTranslatef(0, -0.04F, 0);
				GL11.glRotatef(180F, 0F, 1F, 0F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				model.render(0.0625F);
				break;
			case EQUIPPED:
				GL11.glTranslatef(-0.8F, -0.7F, 0.7F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				model.render(0.0625F);
				break;
			case EQUIPPED_FIRST_PERSON:
				GL11.glTranslatef(0F, -1.0F, 0.6F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				model.render(0.0625F);
				break;
			default:
				GL11.glTranslatef(0, 0, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
				model.render(0.0625F);
				break;
		}
		
		GL11.glPopMatrix();
	}
}
package se.rimevel.FeudalFunctions.modules.smithing.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

import org.lwjgl.opengl.GL11;

import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.modules.smithing.blocks.BlockAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.renderers.models.ModelAnvil;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityAnvil;

public class RenderAnvil extends TileEntitySpecialRenderer implements IItemRenderer
{
private ModelAnvil model;
	
	public RenderAnvil(ModelAnvil model)
	{
		this.model = model;
	}
	
	public static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/models/anvil.png");
	
	public void render(TileEntityAnvil tile, double x, double y, double z, float f)
	{
		int meta = tile.getBlockMetadata();
		
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.0F, (float)z + 0.5F);
		GL11.glScalef(-1F, -1F, 1F);
		GL11.glRotatef(90F * meta, 0F, 1F, 0F);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		
		model.render(0.0625F);
		
		GL11.glPopMatrix();
		
		int currentSlot = 0;
		
		for (int gridZ = 2; gridZ >= 0; gridZ--)
		{
			for (int gridX = 2; gridX >= 0; gridX--)
			{	
				if(tile.content[BlockAnvil.getSlotMappingIndex(meta, currentSlot)] != null)
				{
					GL11.glPushMatrix();
					
					//Item to be rendered. Copied from the actual inventory to make sure internal data is correct.
					ItemStack stack = tile.content[BlockAnvil.getSlotMappingIndex(meta, currentSlot)].copy();
					
					//Entity representing the itemStack.
					EntityItem item = new EntityItem(tile.getWorldObj(), 0D, 0D, 0D, stack);
					item.hoverStart = 0F;
					
					RenderItem.renderInFrame = true;
					
					float GRID_PADDING = 0.300F;
					float GRID_SCALE =  0.20F;
					
					GL11.glTranslatef((float)x + GRID_PADDING + (GRID_SCALE * gridX),(float)y + 1.00F,(float)z + GRID_PADDING + (GRID_SCALE * gridZ));
					
					if(!(stack.getItem() instanceof ItemBlock))
					{
						GL11.glTranslatef(0F, 0.01F, 0F);
					}
					
					//GL11.glTranslatef((float)x + 0.5F * , (float)y + 1.0F, (float)z + 0.5F);
					GL11.glRotatef(180F, 0F, 1F, 1F);
					
					if(stack.getItem() instanceof ItemBlock)
					{
						GL11.glRotatef(180F, 0F, 0F, 1F);
						if(meta == 0){GL11.glRotatef(90F, 1F, 0F, 0F);}
						if(meta == 1){GL11.glRotatef(-90F, 0F, 1F, 0F);}
						if(meta == 2){GL11.glRotatef(-90F, 1F, 0F, 0F);}
						if(meta == 3){GL11.glRotatef(90F, 0F, 1F, 0F);}
						
					}
					else
					{
						if(meta == 0){GL11.glTranslatef(0F, -0.08F, 0F);}
						if(meta == 1){GL11.glTranslatef(-0.08F, 0F, 0F);}
						if(meta == 2){GL11.glTranslatef(0F, 0.08F, 0F);}
						if(meta == 3){GL11.glTranslatef(0.08F, 0F, 0F);}
					}
					
					GL11.glRotatef(-90F * meta, 0F, 0F, 1F);
					
					GL11.glScalef(0.40F, 0.40F, 0.40F);
					
					//GL11.glTranslatef((float)x + GRID_PADDING + (GRID_SCALE * gridX),(float)y +  0.9F,(float)z + GRID_PADDING + (GRID_SCALE * gridZ));
					//GL11.glRotatef(90F, 1F, 0F, 0F);
					//GL11.glRotatef(90F * meta, 0F, 0F, 1F);
					//GL11.glScalef(-0.20F, -0.20F, 0.20F);
					
					RenderManager.instance.renderEntityWithPosYaw(item, 0D, 0D, 0D, 0.0F, 0.0F);
					//RenderItem3d.render3d(tile.content[BlockSmithingStation.getSlotMappingIndex(meta, currentSlot)]);
					
					RenderItem.renderInFrame = false;
					
					GL11.glPopMatrix();
				}
				
				currentSlot++;
			}
		}
	}
	
	@Override
	public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float partialTickTime)
	{
		render((TileEntityAnvil)entity, x, y, z, partialTickTime);
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
				GL11.glTranslatef(0, -0.45F, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderAnvil.texture);
				model.render(0.0625F);
				break;
			case EQUIPPED:
				GL11.glTranslatef(-0.4F, -0.4F, 0.5F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderAnvil.texture);
				model.render(0.0625F);
				break;
			case EQUIPPED_FIRST_PERSON:
				GL11.glTranslatef(0F, -0.5F, 0.7F);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderAnvil.texture);
				model.render(0.0625F);
				break;
			default:
				GL11.glTranslatef(0, 0, 0);
				Minecraft.getMinecraft().getTextureManager().bindTexture(RenderAnvil.texture);
				model.render(0.0625F);
				break;
		}
		
		GL11.glPopMatrix();
	}
}

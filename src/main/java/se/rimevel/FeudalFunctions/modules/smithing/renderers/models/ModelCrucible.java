package se.rimevel.FeudalFunctions.modules.smithing.renderers.models;

import java.util.ArrayList;

import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityCrucible;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelCrucible extends ModelBase
{
	ModelRenderer Base;
	
	private ArrayList<ModelRenderer> parts;
	
	public ModelCrucible()
	{
		parts = new ArrayList<ModelRenderer>();
		
		textureWidth = 64;
		textureHeight = 32;
	
		Base = new ModelRenderer(this, 0, 0);
		Base.addBox(-5F, 0F, -5F, 10, 2, 10);
		Base.setRotationPoint(0F, 0F, 0F);
		Base.setTextureSize(64, 32);
		Base.mirror = true;
	  
		for (float r = 0; r <= (Math.PI * 2); r += Math.PI / 2)
		{
			ModelRenderer Side = new ModelRenderer(this, 0, 12);
			setRotation(Base, 0F, 0F, 0F);
			Side.addBox(-5F, -8F, 3F, 8, 8, 2);
			Side.setRotationPoint(0F, 0F, 0F);
			Side.setTextureSize(64, 32);
			Side.mirror = true;
			setRotation(Side, 0F, r, 0F);
			parts.add(Side);
		}
	  
	}
	
	public void render(float f5)
	{
		Base.render(f5);
		for (ModelRenderer part : parts)
		{
			part.render(f5);
		}
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}

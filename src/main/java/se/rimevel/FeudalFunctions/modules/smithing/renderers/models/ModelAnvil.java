package se.rimevel.FeudalFunctions.modules.smithing.renderers.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelAnvil extends ModelBase
{
	ModelRenderer Top;
	ModelRenderer Middle;
	ModelRenderer UpperFoot;
	ModelRenderer LowerFoot;

	public ModelAnvil()
	{
		textureWidth = 64;
		textureHeight = 64;
		
		Top = new ModelRenderer(this, 0, 0);
		Top.addBox(-8F, 0F, -5F, 16, 6, 10);
		Top.setRotationPoint(0F, 0F, 0F);
		Top.setTextureSize(64, 64);
		Top.mirror = true;
		setRotation(Top, 0F, 0F, 0F);
		Middle = new ModelRenderer(this, 0, 16);
		Middle.addBox(-4F, 6F, -3F, 8, 4, 6);
		Middle.setRotationPoint(0F, 0F, 0F);
		Middle.setTextureSize(64, 64);
		Middle.mirror = true;
		setRotation(Middle, 0F, 0F, 0F);
		UpperFoot = new ModelRenderer(this, 0, 26);
		UpperFoot.addBox(-5F, 10F, -4F, 10, 2, 8);
		UpperFoot.setRotationPoint(0F, 0F, 0F);
		UpperFoot.setTextureSize(64, 64);
		UpperFoot.mirror = true;
		setRotation(UpperFoot, 0F, 0F, 0F);
		LowerFoot = new ModelRenderer(this, 0, 36);
		LowerFoot.addBox(-6F, 12F, -5F, 12, 4, 10);
		LowerFoot.setRotationPoint(0F, 0F, 0F);
		LowerFoot.setTextureSize(64, 64);
		LowerFoot.mirror = true;
		setRotation(LowerFoot, 0F, 0F, 0F);
	}
	
	public void render(float f5)
	{
		Top.render(f5);
		Middle.render(f5);
		UpperFoot.render(f5);
		LowerFoot.render(f5);
	}
	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
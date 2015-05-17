package se.rimevel.FeudalFunctions.modules.smithing.renderers.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelBloomery extends ModelBase
{
	ModelRenderer Bottom;

	public ModelBloomery() //#222222
	{
		textureWidth = 64;
		textureHeight = 64;

		Bottom = new ModelRenderer(this, 0, 0);
		Bottom.addBox(-8F, -2F, -8F, 16, 10, 16);
		Bottom.setRotationPoint(0F, 0F, 0F);
		Bottom.setTextureSize(64, 32);
		Bottom.mirror = true;
		setRotation(Bottom, 0F, 0F, 0F);
		
		for(float r = 0; r <= (Math.PI * 2); r += Math.PI)
		{
			ModelRenderer Edge1 = new ModelRenderer(this, 24, 26);
			Edge1.addBox(-7F, -8F, -7F, 14, 6, 2);
			Edge1.setRotationPoint(0F, 0F, 0F);
			Edge1.setTextureSize(64, 32);
			Edge1.mirror = true;
			setRotation(Edge1, 0F, r, 0F);
			ModelRenderer Edge2 = new ModelRenderer(this, 0, 26);
			Edge2.addBox(-7F, -8F, -5F, 2, 6, 10);
			Edge2.setRotationPoint(0F, 0F, 0F);
			Edge2.setTextureSize(64, 32);
			Edge2.mirror = true;
			setRotation(Edge2, 0F, r, 0F);
			Bottom.addChild(Edge1);
			Bottom.addChild(Edge2);
		}
	}

	public void render(float f5)
	{
		Bottom.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}

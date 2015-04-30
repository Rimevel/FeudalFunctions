package se.rimevel.FeudalFunctions.modules.survival.renderers.models;

import net.minecraft.client.model.ModelRenderer;
import se.rimevel.FeudalFunctions.core.rendering.ModelSimpleBase;

public class ModelCampfire extends ModelSimpleBase
{
	ModelRenderer Log3;
	ModelRenderer Log2;
	ModelRenderer Log1;
	ModelRenderer Twig2;
	ModelRenderer Twig1;
	ModelRenderer Ground;
	
	public ModelCampfire()
	{
		textureWidth = 64;
		textureHeight = 64;
		
		Log3 = new ModelRenderer(this, 0, 16);
		Log3.addBox(-7F, -4F, -1F, 14, 2, 2);
		Log3.setRotationPoint(0F, 0F, 0F);
		Log3.setTextureSize(64, 64);
		Log3.mirror = true;
		setRotation(Log3, 0F, 0.9250245F, -0.296706F);
		Log2 = new ModelRenderer(this, 0, 16);
		Log2.addBox(-6F, -3F, -3F, 14, 2, 2);
		Log2.setRotationPoint(0F, 0F, 0F);
		Log2.setTextureSize(64, 64);
		Log2.mirror = true;
		setRotation(Log2, 0F, -1.064651F, -0.2617994F);
		Log1 = new ModelRenderer(this, 0, 16);
		Log1.addBox(-7F, -2F, -1F, 14, 2, 2);
		Log1.setRotationPoint(0F, 0F, 0F);
		Log1.setTextureSize(64, 64);
		Log1.mirror = true;
		setRotation(Log1, 0F, 0.122173F, 0F);
		Twig2 = new ModelRenderer(this, 32, 16);
		Twig2.addBox(-2F, -1F, 3F, 8, 1, 1);
		Twig2.setRotationPoint(0F, 0F, 0F);
		Twig2.setTextureSize(64, 64);
		Twig2.mirror = true;
		setRotation(Twig2, 0F, 2.90303F, 0F);
		Twig1 = new ModelRenderer(this, 32, 16);
		Twig1.addBox(-5F, -1F, 4F, 8, 1, 1);
		Twig1.setRotationPoint(0F, 0F, 0F);
		Twig1.setTextureSize(64, 64);
		Twig1.mirror = true;
		setRotation(Twig1, 0F, 0.4712389F, 0F);
		Ground = new ModelRenderer(this, 0, 0);
		Ground.addBox(-8F, 0F, -8F, 16, 0, 16);
		Ground.setRotationPoint(0F, 0F, 0F);
		Ground.setTextureSize(64, 64);
		Ground.mirror = true;
		setRotation(Ground, 0F, 0F, 0F);
	}
	
	public void render(float f5, boolean showGround)
	{
		Log3.render(f5);
		Log2.render(f5);
		Log1.render(f5);
		Twig2.render(f5);
		Twig1.render(f5);
		if(showGround)
		{
			Ground.render(f5);
		}
	}
}

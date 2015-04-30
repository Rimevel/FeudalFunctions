package se.rimevel.FeudalFunctions.core.rendering;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSimpleBase extends ModelBase
{
	public void render(float f5)
	{
		
	}
	
	public void render(float f5, Object ... extra)
	{
		
	}
	
	public void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}

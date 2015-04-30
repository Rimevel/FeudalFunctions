package se.rimevel.FeudalFunctions.core.util;

import org.lwjgl.opengl.GL11;

public class UtilRendering
{
	public static class RenderVector
	{
		public float x;
		public float y;
		public float z;
		
		public RenderVector(float x, float y, float z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	public static void centerModel(RenderVector rVec)
	{
		GL11.glTranslatef(rVec.x + 0.5F, rVec.y + 0.5F, rVec.z + 0.5F);
	}
	
	public static float getDefaultScale()
	{
		return 0.0625F;
	}
}

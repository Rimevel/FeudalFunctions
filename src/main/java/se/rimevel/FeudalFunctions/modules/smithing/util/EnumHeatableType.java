package se.rimevel.FeudalFunctions.modules.smithing.util;

public enum EnumHeatableType
{
	INGOT("ingot"),
	AXE_HEAD("axe_head"),
	PICKAXE_HEAD("pickaxe_head"),
	HOE_HEAD("hoe_head"),
	SHOVEL_HEAD("shovel_head"),
	SWORD_BLADE("sword_blade"),
	HAMMER_HEAD("hammer_head"),
	PLATE("plate");
	
	public String textureName;
	
	EnumHeatableType(String textureName)
	{
		this.textureName = textureName;
	}
	
	public String getTextureName()
	{
		return this.textureName;
	}
}

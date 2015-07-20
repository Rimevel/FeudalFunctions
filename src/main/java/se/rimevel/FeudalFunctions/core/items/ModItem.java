package se.rimevel.FeudalFunctions.core.items;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import se.rimevel.FeudalFunctions.core.util.UtilArmorMaterials;
import se.rimevel.FeudalFunctions.core.util.UtilToolMaterials;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModItem<T extends Item>
{
	private static final ArrayList<WeakReference<ModItem>> items = new ArrayList<WeakReference<ModItem>>();
	
	//General vars
	private String name;
	private Item instance;
	private Class<T> itemClass;
	
	//Tool vars
	private float damage;
	private UtilToolMaterials material;
	private String repairMaterial;
	
	//Armor vars
	private UtilArmorMaterials armorMaterial;
	private int type;
	private String textureName;
	
	public ModItem(String name, Class<T> itemClass)
	{
		this.name = name;
		this.itemClass = itemClass;
		
		damage = -1F;
		material = null;
		repairMaterial = null;
		
		items.add(new WeakReference<ModItem>(this));
	}
	
	public ModItem(String name, Class<T> itemClass, UtilArmorMaterials armorMaterial, int type, String textureName)
	{
		this.name = name;
		this.itemClass = itemClass;
		
		damage = -1F;
		material = null;
		repairMaterial = null;
		
		this.armorMaterial = armorMaterial;
		this.type = type;
		this.textureName = textureName;
		
		items.add(new WeakReference<ModItem>(this));
	}
	
	public ModItem(String name, Class<T> itemClass, float damage, UtilToolMaterials toolMaterial, String repairMaterial)
	{
		this.name = name;
		this.itemClass = itemClass;
		
		this.damage = damage;
		this.material = toolMaterial;
		this.repairMaterial = repairMaterial;
		
		this.armorMaterial = null;
		this.type = -1;
		this.textureName = null;
		
		items.add(new WeakReference<ModItem>(this));
	}
	
	public T getInstance()
	{
		return itemClass.cast(instance);
	}
	
	public void buildInstance() throws
	InstantiationException,
	IllegalAccessException,
	IllegalArgumentException,
	InvocationTargetException,
	NoSuchMethodException,
	SecurityException,
	ClassNotFoundException
	{
		if(this.instance == null)
		{
			if(this.itemClass.getSuperclass() == ItemBase.class)
			{
				Constructor<?> cl = this.itemClass.getConstructor();
				Object object = cl.newInstance();
				this.instance = (ItemBase) object;
				this.instance.setUnlocalizedName(this.name);
			}
			else if(this.itemClass.getSuperclass() == ItemToolBase.class)
			{
				try
				{
					Constructor<?> cl = this.itemClass.getConstructor(float.class, ToolMaterial.class, String.class);
					Object object = cl.newInstance(this.damage, this.material.getMaterial(), this.repairMaterial);
					this.instance = (ItemToolBase) object;
					this.instance.setUnlocalizedName(this.name);
				}
				catch (InvocationTargetException e)
				{
					e.getTargetException();
				}
			}
			else if(this.itemClass.getSuperclass() == ItemArmorBase.class)
			{
				try
				{
					Constructor<?> cl = this.itemClass.getConstructor(ArmorMaterial.class, int.class, String.class);
					Object object = cl.newInstance(this.armorMaterial.getMaterial(), this.type, this.textureName);
					this.instance = (ItemArmorBase) object;
					this.instance.setUnlocalizedName(this.name);
				}
				catch (InvocationTargetException e)
				{
					e.getTargetException();
				}
			}
			else if(this.itemClass.getSuperclass().getSuperclass() == Item.class ||
					this.itemClass.getSuperclass().getSuperclass().getSuperclass() == Item.class
					)
			{
				try
				{
					Constructor<?> cl = this.itemClass.getConstructor(float.class, ToolMaterial.class, String.class);
					Object object = cl.newInstance(this.damage, this.material.getMaterial(), this.repairMaterial);
					this.instance = (Item) object;
					this.instance.setUnlocalizedName(this.name);
				}
				catch (InvocationTargetException e)
				{
					e.getTargetException();
				}
			}
		}
		
		GameRegistry.registerItem(this.instance, this.name);
		
		if(this.material != null && this.repairMaterial != null && this.damage >= 0F)
		{
			GameRegistry.registerCustomItemStack(this.instance.getUnlocalizedName(), new ItemStack(this.instance, 1));
		}
	}
	
	public static void buildAllItems()
	{
		for (int i = 0; i < items.size(); i++)
		{
			try
			{
				items.get(i).get().buildInstance();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

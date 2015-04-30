package se.rimevel.FeudalFunctions.core.blocks;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import se.rimevel.FeudalFunctions.core.items.ItemBlockBase;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityBase;
import se.rimevel.FeudalFunctions.core.tiles.TileEntityContainerBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlock<T extends Block>
{
	private static final ArrayList<WeakReference<ModBlock>> blocks = new ArrayList<WeakReference<ModBlock>>();
	
	private String name;
	private Block instance;
	private Class<T> blockClass;
	private Class<?> itemBlock;
	private Class<?> tileEntity;
	
	public ModBlock(String name, Class<T> blockClass)
	{
		this.name = name;
		this.blockClass = blockClass;
		
		this.instance = null;
		this.itemBlock = null;
		this.tileEntity = null;
		
		blocks.add(new WeakReference<ModBlock>(this));
	}
	
	public ModBlock(String name, Class<T> blockClass, Class<?> ... classes)
	{
		this.name = name;
		this.blockClass = blockClass;
		
		this.instance = null;
		this.itemBlock = null;
		this.tileEntity = null;
		
		for (Class<?> c : classes)
		{
			if(c.getSuperclass().equals(ItemBlock.class) || c.getSuperclass().equals(ItemBlockBase.class))
			{
				if(this.itemBlock != null){continue;}
				
				this.itemBlock = c;
			}
			else if(c.getSuperclass().equals(TileEntity.class) || c.getSuperclass().equals(TileEntityBase.class) || c.getSuperclass().equals(TileEntityContainerBase.class))
			{
				if(this.tileEntity != null){continue;}
				
				this.tileEntity = c;
			}
		}
		
		blocks.add(new WeakReference<ModBlock>(this));
	}
	
	public T getInstance()
	{
		return blockClass.cast(instance);
	}
	
	public void buildInstance() throws
	InstantiationException,
	IllegalAccessException,
	IllegalArgumentException,
	InvocationTargetException,
	NoSuchMethodException,
	SecurityException,
	ClassNotFoundException,
	NoSuchFieldException
	{
		if(this.instance == null)
		{
			Constructor<T> cl = this.blockClass.getConstructor();
			Object object = cl.newInstance();
			this.instance = (T) object;
			this.instance.setBlockName(this.name);
		}
		
		if(this.itemBlock != null)
		{
			GameRegistry.registerBlock(this.instance, (Class<? extends ItemBlock>) itemBlock, this.name);
		}
		else
		{
			GameRegistry.registerBlock(this.instance, this.name);
		}
		
		if(this.tileEntity != null)
		{
			GameRegistry.registerTileEntity((Class<? extends TileEntity>) this.tileEntity, this.name);
		}
	}
	
	public static void buildAllBlocks()
	{
		for (int i = 0; i < blocks.size(); i++)
		{
			try
			{
				blocks.get(i).get().buildInstance();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

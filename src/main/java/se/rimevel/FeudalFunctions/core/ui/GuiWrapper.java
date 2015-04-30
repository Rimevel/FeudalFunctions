package se.rimevel.FeudalFunctions.core.ui;

import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

/**
 * Wrapper class holding data describing a gui element for both sides.
 */
public class GuiWrapper
{
	public final int id;
	public final Class<? extends Container> container;
	public final Class<? extends Gui> gui;
	public final Class<? extends TileEntity> tileEntity;
	
	public GuiWrapper(int id, Class<? extends Container> container, Class<? extends Gui> gui, Class<? extends TileEntity> tileEntity)
	{
		this.id = id;
		this.container = container;
		this.gui = gui;
		this.tileEntity = tileEntity;
	}
	
	public GuiWrapper(int id, Class<? extends Gui> gui)
	{
		this.id = id;
		this.container = null;
		this.gui = gui;
		this.tileEntity = null;
	}
	
	protected static boolean isOf(Class classToCheck, Object obj)
	{
		return classToCheck.isInstance(obj);
	}
}

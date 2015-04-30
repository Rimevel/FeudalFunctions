package se.rimevel.FeudalFunctions.core.ui;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import se.rimevel.FeudalFunctions.core.ModCore;
import se.rimevel.FeudalFunctions.core.util.UtilLog;
import se.rimevel.FeudalFunctions.modules.Modules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

/**
 * Handles all calls to retrive a gui from either the client or the server.
 */
public class GuiHandler implements IGuiHandler
{
	private static final HashMap<Integer, GuiWrapper> guiList = new HashMap<Integer, GuiWrapper>();
	
	public GuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(ModCore.instance, this);
		for (Modules m : Modules.values())
		{
			if(m.getModule().getGuis() == null){continue;}
			
			for (GuiWrapper gui : m.getModule().getGuis())
			{
				if(!guiList.containsKey(gui.id))
				{
					guiList.put(gui.id, gui);
				}
				else
				{
					UtilLog.error("Duplicate gui id! The id for" + gui.gui.toString() + "already exist!");
				}
			}
		}
	}
	
	/**
	 * Open a gui of the give id.
	 * 
	 * @param player The player. Either client or server side.
	 * @param id Integer for the gui to open.
	 * @param world Current world.
	 * @param x X coordinate of the clicked block.
	 * @param y Y coordinate of the clicked block.
	 * @param z Z coordinate of the clicked block.
	 */
	public static void openGui(EntityPlayer player, int id, World world, int x, int y, int z)
	{
		FMLNetworkHandler.openGui(player, ModCore.instance, id, world, x, y, z);
	}
	
	/**
	 * Fetches the container class for the gui of the given id. You should not need to call this yourself.
	 */
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		GuiWrapper gui = guiList.get(id);
		
		if(gui != null && tile != null && gui.isOf(gui.tileEntity, tile))
		{
			Constructor con;
			try
			{
				con = gui.container.getConstructor(InventoryPlayer.class, tile.getClass());
				return con.newInstance(player.inventory, tile);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Fetches the gui class for the gui of the given id. You should not need to call this yourself.
	 */
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(x, y, z);
		
		GuiWrapper gui = guiList.get(id);
		
		if(gui != null && tile != null && gui.isOf(gui.tileEntity, tile))
		{
			Constructor con;
			try
			{
				con = gui.gui.getConstructor(InventoryPlayer.class, tile.getClass());
				return con.newInstance(player.inventory, gui.tileEntity.cast(tile));
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		if(gui != null)
		{
			Constructor con;
			try
			{
				con = gui.gui.getConstructor();
				return con.newInstance();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
		}
		
		return null;
	}
}

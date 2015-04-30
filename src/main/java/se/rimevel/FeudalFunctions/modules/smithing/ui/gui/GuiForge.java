package se.rimevel.FeudalFunctions.modules.smithing.ui.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import se.rimevel.FeudalFunctions.core.ModSettings;
import se.rimevel.FeudalFunctions.core.ui.gui.GuiContainerBase;
import se.rimevel.FeudalFunctions.core.util.UtilMath;
import se.rimevel.FeudalFunctions.modules.smithing.tiles.TileEntityForge;
import se.rimevel.FeudalFunctions.modules.smithing.ui.container.ContainerForge;

public class GuiForge extends GuiContainerBase
{
	private TileEntityForge tile;
	
	public GuiForge(InventoryPlayer invPlayer, TileEntityForge tile)
	{
		super(new ContainerForge(invPlayer, tile), 176, 186);
		this.tile = tile;
	}
	
	private static final ResourceLocation texture = new ResourceLocation(ModSettings.TEXTURE_LOCATION + "textures/gui/forge_gui.png");
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawBase(texture);
		
		int barWidth = 53;
		int fuel = UtilMath.scaleFloat(tile.fuel, 0F, tile.MAX_FUEL, barWidth);
		
		if(tile.isActive())
		{
			drawTexturedModalRect(guiLeft + 71, guiTop + 58, 176, 0, 34, 14);
		}
		
		if(tile.fuel > 0)
		{
			drawTexturedModalRect(guiLeft + 61, guiTop + 74, 176, 14, fuel + 1, 8);
		}
	}
}
